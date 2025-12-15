

EMT_BASE_URL = "https://openapi.emtmadrid.es"
EMT_LOGIN_ENDPOINT = "/v3/mobilitylabs/user/login/"
EMT_STOP_DETAIL_ENDPOINT = "/v1/transport/busemtmad/stops/{id}/detail/"
EMT_ARRIVE_ENDPOINT = "/v2/transport/busemtmad/stops/{id}/arrives/"



bodyJson= {"cultureinfo": "ES", "Yext_StopRequired_YN": "Y", "Text_EstimationsRequired_YN": "Y",
            "Text_IncidencesRequired_YN": "Y"}