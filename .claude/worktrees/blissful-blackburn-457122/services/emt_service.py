#en este archivo definiré los endpoints 
from fastapi import FastAPI
import requests
import json
from core.config import *
from Auth import headersCredenciales

app=FastAPI()

def renovarToken():
    print("solicitando nuevo token a la EMT...")
    token = requests.get(EMT_BASE_URL + EMT_LOGIN_ENDPOINT, headers=headersCredenciales)
    token = token.json()
    return {"accessToken": str(token["data"][0]["accessToken"])}

def getToken():
    global headerToken
    if headerToken:
        return headerToken
    headerToken = renovarToken()
    return headerToken

headerToken = None
headerToken = getToken()

@app.get("/buscarParada")
def buscarParada(nombre: str):
    token = getToken()
    url = EMT_BASE_URL + EMT_STOPS_LIST_ENDPOINT
    respuesta = requests.get(url, headers=token)

    if not (200 <= respuesta.status_code < 300):
        return {"code": respuesta.status_code, "error": "Error al acceder a la API de la EMT"}

    data = respuesta.json()
    nombre_lower = nombre.lower()
    paradas = []
    for stop in data.get("data", []):
        if nombre_lower in stop.get("name", "").lower():
            paradas.append({
                "idParada": stop.get("stop"),
                "nombre": stop.get("name"),
                "direccion": stop.get("postalAddress", ""),
                "coordenadas": stop.get("geometry", {}).get("coordinates", [])
            })
            if len(paradas) >= 20:
                break

    return {"code": 200, "total": len(paradas), "paradas": paradas}

@app.get("/infoParada/{idParada}")
def sacarTiemposParada(idParada: int):

    respuesta = requests.post(f"https://openapi.emtmadrid.es/v2/transport/busemtmad/stops/{idParada}/arrives/",
                              headers=getToken(), json=bodyJson)
    tiempoLlegada = respuesta.json()

    info={"code":respuesta.status_code}
    if (200 <= respuesta.status_code < 300):
        info.update({"parada":tiempoLlegada["data"][0]["Arrive"][0]["stop"]})
        lineas=[]
        for j in tiempoLlegada["data"][0]["Arrive"]:
            
            linea={"linea": j["line"],
            "estimateArrive": j["estimateArrive"],"destination":j["destination"]}#aqui se añade toda la informacion que quiero pasar a la aplicación
            lineas.append(linea)
        info.update({"infoPorLinea":lineas})
        
    else:
        if (respuesta.status_code != 200):
            print("error al acceder a los datos de llegada : statuscode!=200")
    return(info)