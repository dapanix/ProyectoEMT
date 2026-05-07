import logging
import requests
from fastapi import FastAPI, HTTPException
from core.config import EMT_BASE_URL, EMT_LOGIN_ENDPOINT, EMT_ARRIVE_ENDPOINT, EMT_STOPS_LIST_ENDPOINT, bodyJson
from services.Auth import headersCredenciales

logger = logging.getLogger(__name__)
app = FastAPI()

_token: str | None = None


def _fetch_new_token() -> str:
    response = requests.get(EMT_BASE_URL + EMT_LOGIN_ENDPOINT, headers=headersCredenciales)
    response.raise_for_status()
    return response.json()["data"][0]["accessToken"]


def _get_auth_headers() -> dict:
    global _token
    if _token is None:
        _token = _fetch_new_token()
    return {"accessToken": _token}


def _retry_on_401(method, url, **kwargs):
    global _token
    headers = _get_auth_headers()
    response = method(url, headers=headers, **kwargs)
    if response.status_code == 401:
        _token = None
        response = method(url, headers=_get_auth_headers(), **kwargs)
    return response


@app.get("/infoParada/{stop_id}")
def get_stop_info(stop_id: int):
    url = (EMT_BASE_URL + EMT_ARRIVE_ENDPOINT).format(id=stop_id)
    response = _retry_on_401(requests.post, url, json=bodyJson)

    if not response.ok:
        raise HTTPException(status_code=response.status_code, detail="Error al obtener datos de la parada")

    data = response.json().get("data", [])
    if not data or not data[0].get("Arrive"):
        raise HTTPException(status_code=404, detail=f"No hay información de llegadas para la parada {stop_id}")

    arrivals = data[0]["Arrive"]
    return {
        "parada": arrivals[0]["stop"],
        "infoPorLinea": [
            {
                "linea": bus["line"],
                "estimateArrive": bus["estimateArrive"],
                "destination": bus["destination"],
            }
            for bus in arrivals
        ],
    }


@app.get("/buscarParada/{nombre}")
def search_stop(nombre: str):
    url = EMT_BASE_URL + EMT_STOPS_LIST_ENDPOINT
    response = _retry_on_401(requests.get, url)

    if not response.ok:
        raise HTTPException(status_code=response.status_code, detail="Error al buscar paradas")

    nombre_lower = nombre.lower()
    paradas = []
    for stop in response.json().get("data", []):
        if nombre_lower in stop.get("name", "").lower():
            paradas.append({
                "idParada": stop.get("stop"),
                "nombre": stop.get("name"),
                "direccion": stop.get("postalAddress", ""),
                "coordenadas": stop.get("geometry", {}).get("coordinates", []),
            })
            if len(paradas) >= 20:
                break

    return {"total": len(paradas), "paradas": paradas}
