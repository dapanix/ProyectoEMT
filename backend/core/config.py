

EMT_BASE_URL = "https://openapi.emtmadrid.es"
EMT_LOGIN_ENDPOINT = "/v1/mobilitylabs/user/login/"
EMT_STOP_DETAIL_ENDPOINT = "/v1/transport/busemtmad/stops/{id}/detail/"
EMT_ARRIVE_ENDPOINT = "/v2/transport/busemtmad/stops/{id}/arrives/"
EMT_STOPS_LIST_ENDPOINT = "/v1/transport/busemtmad/stops/list/"
EMT_LINE_STOPS_ENDPOINT = "/v1/transport/busemtmad/lines/{line}/stops/"



bodyJson = {
    "cultureInfo": "ES",
    "Text_StopRequired_YN": "Y",
    "Text_EstimationsRequired_YN": "Y",
    "Text_IncidencesRequired_YN": "N",
}