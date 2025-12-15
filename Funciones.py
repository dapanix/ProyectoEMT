# PRACTICA-ACCESO A INFO EMT
import requests
import json

URLHello = "https://openapi.emtmadrid.es/v1/hello/"
URLLogin = "https://openapi.emtmadrid.es/v3/mobilitylabs/user/login/"

EMT_BASE_URL = "https://openapi.emtmadrid.es"
EMT_LOGIN_ENDPOINT = "/v3/mobilitylabs/user/login/"
EMT_STOP_DETAIL_ENDPOINT = "/v1/transport/busemtmad/stops/{id}/detail/"
EMT_ARRIVE_ENDPOINT = "/v2/transport/busemtmad/stops/{id}/arrives/"

headersCredenciales = {"X-clientId": "e1291b30-0e42-4fb2-b82c-70f5c4225ba2",
                       "passKey": "CEB9AA4ECC8AA23420B759B83CD39A174CBC6B0B986721A4B92AEEFC968A42388F61F7A8F4F881721DEBE6B9B8C90B0DD3773C4EDC730200DF24EDED988CE5F5"}


bodyJson= {"cultureinfo": "ES", "Yext_StopRequired_YN": "Y", "Text_EstimationsRequired_YN": "Y",
            "Text_IncidencesRequired_YN": "Y"}


def getToken():
    try:
        headerToken
        try:
            intento = requests.get(URLLogin, headers=headerToken)
            print("1.token valido")
            return headerToken
        except Exception as e:
            print("2.token caducado, error: ", e, "\n")
            print("consiguientdo un nuevo token")
            token = requests.get(URLLogin, headers=headersCredenciales)
            print("nuevo token: ", token["data"][0]["accessToken"])
            dic = {"accessToken": str(token["data"][0]["accessToken"])}
            return dic
    except Exception as e:
        print("creando el primer token")
        token = requests.get(URLLogin, headers=headersCredenciales)
        token = token.json()
        dic = {"accessToken": str(token["data"][0]["accessToken"])}
        return dic


headerToken = getToken()

IDParada = "1294"  # parada vuelta gym
URLParada = f"https://openapi.emtmadrid.es/v1/transport/busemtmad/stops/{IDParada}/detail/"

# Script que pida una parada por ID

# Devuelva los próximos buses que llegan

# Muestre destino y minutos restantes

# IDparada=input("inserte el id de la parada que desee: ")


"""{'code': '00', 'description': 'Data recovered OK', 'datetime': '2025-10-17T13:03:51.849676', 'data': [{'stops': 
[{'stop': '1294', 'name': 'Avenida de América-Instituto Barajas', 'postalAddress': 'Av. de América (I.E.S. Barajas) frente al Nº 117', 
'geometry': {'type': 'Point', 'coordinates': [-3.60004379145915, 40.4504878544145]}, 'pmv': None, 'dataLine': 
[{'line': '114', 'label': '114', 'direction': 'B', 'maxFreq': '20', 'minFreq': '3', 'headerA': 'AVENIDA AMERICA', 'headerB': 'Bº AEROPUERTO',
 'startTime': '06:00', 'stopTime': '23:00', 'dayType': 'LA'}, {'line': '115', 'label': '115', 'direction': 'B', 'maxFreq': '18', 'minFreq': '4',
   'headerA': 'AVENIDA AMERICA', 'headerB': 'BARAJAS', 'startTime': '06:00', 'stopTime': '23:30', 'dayType': 'LA'}]}]}]}"""


def sacarTiemposParada():
    body = {"cultureinfo": "ES", "Yext_StopRequired_YN": "Y", "Text_EstimationsRequired_YN": "Y",
            "Text_IncidencesRequired_YN": "Y"}
    infoParada = requests.get(f"https://openapi.emtmadrid.es/v1/transport/busemtmad/stops/{IDParada}/detail/",
                              headers=getToken())
    respuesta = requests.post(f"https://openapi.emtmadrid.es/v2/transport/busemtmad/stops/{IDParada}/arrives/",
                              headers=getToken(), json=body)
    tiempoLlegada = respuesta.json()
    busesParada = infoParada.json()
    print(json.dumps(busesParada, indent=4, ensure_ascii=False))
    print(json.dumps(tiempoLlegada, indent=4, ensure_ascii=False))

    if (200 <= respuesta.status_code < 300 and 200 <= infoParada.status_code < 300):
        for i in busesParada["data"][0]["stops"][0]["dataLine"]:
            for j in tiempoLlegada["data"][0]["Arrive"]:
                if (i["line"] == j["line"]):
                    print("Linea " + str(i["line"]) + " tiempo de espera " + str(j["estimateArrive"]) + " segundos")

    else:
        if (respuesta.status_code != 200):
            print("error al acceder a los datos de llegada : statuscode!=200")
        if (infoParada.status_code != 200):
            print("error al acceder a los datos de la parada: statuscode!=200")


sacarTiemposParada()