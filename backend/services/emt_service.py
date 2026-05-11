import unicodedata
import requests
from fastapi import HTTPException
from core.config import EMT_BASE_URL, EMT_LOGIN_ENDPOINT, EMT_ARRIVE_ENDPOINT, EMT_STOPS_LIST_ENDPOINT, bodyJson
from services.Auth import headersCredenciales

_token: str | None = None


def _normalizar(texto: str) -> str:
    return unicodedata.normalize("NFD", texto).encode("ascii", "ignore").decode().lower()


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
    response = method(url, headers=_get_auth_headers(), **kwargs)
    if response.status_code == 401:
        _token = None
        response = method(url, headers=_get_auth_headers(), **kwargs)
    return response


def get_stop_arrivals(stop_id: int) -> dict:
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


def get_line_stops(line_id: str) -> dict:
    url = EMT_BASE_URL + EMT_STOPS_LIST_ENDPOINT
    response = _retry_on_401(requests.post, url)

    if not response.ok:
        raise HTTPException(status_code=response.status_code, detail="Error al obtener paradas de la línea")

    paradas = []
    for stop in response.json().get("data", []):
        lines = stop.get("dataLine") or stop.get("lines") or []
        if any(str(l).upper() == line_id.upper() for l in lines):
            paradas.append({
                "idParada": stop.get("node"),
                "nombre": stop.get("name"),
                "coordenadas": stop.get("geometry", {}).get("coordinates", []),
            })

    if not paradas:
        raise HTTPException(status_code=404, detail=f"Línea {line_id} no encontrada o sin paradas")

    return {"linea": line_id, "total": len(paradas), "paradas": paradas}


def debug_stop_fields() -> dict:
    url = EMT_BASE_URL + EMT_STOPS_LIST_ENDPOINT
    response = _retry_on_401(requests.post, url)
    first_stop = response.json().get("data", [{}])[0]
    return {"campos": list(first_stop.keys()), "ejemplo": first_stop}


def search_stops_by_name(nombre: str) -> dict:
    url = EMT_BASE_URL + EMT_STOPS_LIST_ENDPOINT
    response = _retry_on_401(requests.post, url)

    if not response.ok:
        raise HTTPException(status_code=response.status_code, detail="Error al buscar paradas")

    nombre_norm = _normalizar(nombre)
    paradas = []
    for stop in response.json().get("data", []):
        if nombre_norm in _normalizar(stop.get("name", "")):
            paradas.append({
                "idParada": stop.get("node"),
                "nombre": stop.get("name"),
                "coordenadas": stop.get("geometry", {}).get("coordinates", []),
            })
            if len(paradas) >= 20:
                break

    return {"total": len(paradas), "paradas": paradas}
