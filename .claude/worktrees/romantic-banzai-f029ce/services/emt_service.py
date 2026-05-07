#en este archivo definiré los endpoints 
from fastapi import FastAPI
import requests
import json
from core.config import *
from Auth import headersCredenciales

app=FastAPI()

def getToken():
    if headerToken:
        try:
            intento = requests.get(EMT_LOGIN_ENDPOINT, headers=headerToken)
            print("1.token valido")
            return headerToken
        except Exception as e:
             print("2.token caducado, error: ", e, "\n")
             print("consiguientdo un nuevo token")
             token = requests.get(EMT_LOGIN_ENDPOINT, headers=headersCredenciales)
             dic = {"accessToken": str(token["data"][0]["accessToken"])}
             return dic
    else:
        print("creando el primer token")
        token = requests.get(EMT_LOGIN_ENDPOINT, headers=headersCredenciales)
        token = token.json()
        dic = {"accessToken": str(token["data"][0]["accessToken"])}
        return dic

headerToken = getToken()

#FALTA CONTROLAR ERRORES (codigo de salida)
@app.get("/infoParada{IdParada}")#información que devolveremos cuando el usuario quiera acceder a 
def sacarTiemposParada(IDParada): #la informacion de una parada

    respuesta = requests.post(f"https://openapi.emtmadrid.es/v2/transport/busemtmad/stops/{IDParada}/arrives/",
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