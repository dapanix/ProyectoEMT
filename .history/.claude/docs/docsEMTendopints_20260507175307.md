EMTMADRID-MobilityLabs API Reference
This API provides WebMethods to integrate Developer Portal Collections & Data. Includes new methods and updates
API Reference (MobilityLabs Madrid - Powered by EMTMADRID)
This API provides Webmethod for Public use from MobilityLabs Madrid Infraestructure .

**Changes:

v1.09 2020-09-28
New API methods for BiciMAD GO
citymad/places/list v2
General fixes
v1.08 2020-01-02
New login process method
v1.07 2019-11-22
See "v2" references New versions of arroundxy places (v2)
New structrures for liststops and arrives.

** v1.06 2019-06-18
Push notifications. Change name for deviceId v1.05 2019-06-05
Some methods with wrong URL
New arroundxy and arroundstop very fast. See the new reference "v2" version.

** v1.06 2021-07-14
Some methods with wrong URL
New arrive stop. See the new reference "v2 or v3" version.
New BiciMAD GO methods.
New BiciPARK stations methods.

** v1.07 2022-05-25 parkings/availability/ support several versions: v3-> Includes "Parking disuasorio" Attribute (detPark) v4-> Includes Total Parking Spaces (parkingSpaces)

lines/info: v2: Includes depo data and lenght of line (meters)

HowTo include
Please, contact for supporting:

mail mobilitylabs@emtmadrid.es form contact and forum https://mobilitylabs.emtmadrid.es source code, examples and utilities: https://gitlab.com/mobilitylabsmadrid

Block 0 General
Block_0_General - hello
This webmethod returns the API and server status.

get
https://openapi.emtmadrid.es/v1/hello/
{
{
    "APIVersion": {
        "description": "OPENAPI for public access",
        "version": "00107"
    },
    "code": "00",
    "developerPortal": "https://mobilitylabs.emtmadrid.es",
    "instant": "2019-10-01T16:52:31.664108",
    "message": "Hello, here openapi.emtmadrid.es, I am running Ok and I feel good",
    "morehelp": "https://gitlab.com/mobilitylabsmadrid",
    "poweredBy": "Empresa Municipal de Transportes de Madrid, S.A.",
    "versions": [
        "v1",
        "build 06",
        "v2",
        "build 10"
    ]
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/hello/
url
Block_0_General - test
This webmethod returns the API and server status.

get
https://openapi.emtmadrid.es/v1/
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/
url
Block 1 User identity
Block_1_User_identity - login
This webmethod allows create one session into API context. If you register your own apps into MobilityLabs portal you can use credentials in the context of this X-ClientId and X-Apikey, so, please, register those params for your proposal.
You can use on three ways:
Basic: Allows to use the API on basic level (up to 25k hits/day). Mandatory request params are email and password
Advanced: Allows to use the API on advanced level (up to 250k/day). Mandatory register your application in MobilityLabs and including in the request params are email, password, X-ApiKey and X-ClientId.
Protected: Same functionality as Advanced but allows to protect your portal credentials and increase time session up to 86400 seconds. Mandatory X-ClientId and passKey.
Support v1 and v2 API level with some minor changes.

get
https://openapi.emtmadrid.es/v?/mobilitylabs/user/login/
Header
Campo	Tipo	Descripción
email	String	
Email verified that user has registered using https://mobilitylabs.emtmadrid.es (mandatory if not put the X-ClientId and passKey params)

password	String	
Personal password (mandatory if not put the X-ClientId and passKey params)

X-ApiKey	String	
(deprecated, please, use passKey instead of) when email and password are inserted, if not input, MobilityLabs openapi is asumed

X-ClientId	String	
Optional when email and password are inserted, MobilityLabs openapi is asumed. Mandatory when passKey is inserted

passKey	String	
Optional. Mandatory if not exists email and password.

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
structure of values (if operation did well or empty array).

first_Position	Object	
Only one item of data structure (see below)

userName	String	
user id registered

tokenSecExpiration	Integer	
seconds until token expired (auto-extend each the API is invoked)

idUser	String	
code of user into Service Identity Provider.

email	String	
mail logged

accessToken	String	
token id for use in each API call

updatedAt	String	
last updated of identity

apiCounter	Object	
Counter and limit of daily Api uses, also indicates if you are the owner (relation ship between idUser and xClientId) or not.

{
{
    "code": "00",
    "description": "Register user: yourusername with token: 3bd5855a-ed3d-41d5-8b4b-182726f86031 ",
    "datetime": "2019-10-01T16:35:39.521302",
    "data": [
        {
            "updatedAt": "2019-05-08T07:23:40.7500000",
            "userName": "yourusername",
            "accessToken": "3bd5855a-ed3d-41d5-8b4b-182726f86031",
            "tokenSecExpiration": 984,
            "email": "yourmail@mail.com",
            "idUser": "2f104b08-f8bf-4199-a4bc-c6ecc42ad6ba",
            "apiCounter": {
                "current": 5,
                "dailyUse": 150000,
                "owner": 0,
                "licenceUse": "Please mention EMT Madrid MobilityLabs as data source. Thank you and enjoy!",
                "aboutUses": "Important announcement! On November 30, 2019, your permitted use of this API will be 20.000 hits. To increase up to 150,000 hits, please, register your App or Website in Mobilitylabs and use your own X-ClientId and  X-ApiKey instead of generic login. It is free cost (more info in https://mobilitylabs.emtmadrid.es/doc/new-app and https://apidocs.emtmadrid.es/#api-Block_1_User_identity-login)"
            }
        }
    ]
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v?/mobilitylabs/user/login/
url
Headers
Header
email
email
String
password
password
String
X-ApiKey
X-ApiKey
String
X-ClientId
X-ClientId
String
passKey
passKey
String
Block_1_User_identity - logout
This webmethod destroy the user session.

get
https://openapi.emtmadrid.es/v1/mobilitylabs/user/logout/
Header
Campo	Tipo	Descripción
accessToken	String	
token of session

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (03=OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
Empty.

{
{
    "code": "03",
    "description": "Token a1727347-3eea-4bd1-91b3-0845df61bd32 removed  from control-cache",
    "datetime": "2019-10-02T06:57:50.878129",
    "data": []
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/mobilitylabs/user/logout/
url
Headers
Header
accessToken
accessToken
String
Block_1_User_identity - passwreset
This webmethod send an email for asking about new password.

get
https://openapi.emtmadrid.es/v1/mobilitylabs/user/passwreset/
Header
Campo	Tipo	Descripción
accessToken	String	
token of session

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (05=OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
Empty.

{
{
    "code": "05",
    "description": "Ask for new password sent",
    "datetime": "2019-10-02T07:16:52.703988",
    "data": []
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/mobilitylabs/user/passwreset/
url
Headers
Header
accessToken
accessToken
String
Block_1_User_identity - whoami
This webmethod recover login context if the user is logged

get
https://openapi.emtmadrid.es/v1/mobilitylabs/user/whoami/
Header
Campo	Tipo	Descripción
accessToken	String	
that was got from login method

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (02=Token alive, 80=Token not found)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
please, review login method

{
{
    "code": "02",
    "description": "Token 984f85c9-86db-4066-8fb1-d791abd3a16e valid  into control-cache",
    "datetime": "2019-10-02T07:10:37.541911",
    "data": [
        {
            "_id": "984f85c9-86db-4066-8fb1-d791abd3a16e",
            "updatedAt": "2019-06-25T07:50:13.9030000",
            "createdAt": "2019-06-25T07:50:13.9030000",
            "userName": "youruser",
            "lastUpdate": {
                "$date": 1569993020894
            },
            "idUser": "2f104b08-f8bf-4199-a4bc-c6ecc42ad6ba",
            "tokenSecExpiration": 86400,
            "email": "yourmail@mail.com",
            "tokenDteExpiration": {
                "$date": 1570086620894
            },
            "flagAdvise": true,
            "apiCounter": {
                "current": 4,
                "dailyUse": 150000,
                "owner": 0,
                "licenceUse": "Please mention EMT Madrid MobilityLabs as data source. Thank you and enjoy!",
                "aboutUses": "Important announcement! On November 30, 2019, your permitted use of this API will be 20.000 hits. To increase up to 150,000 hits, please, register your App or Website in Mobilitylabs and use your own X-ClientId and  X-ApiKey instead of generic login. It is free cost (more info in https://mobilitylabs.emtmadrid.es/doc/new-app and https://apidocs.emtmadrid.es/#api-Block_1_User_identity-login)"
            }
        }
    ]
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/mobilitylabs/user/whoami/
url
Headers
Header
accessToken
accessToken
String
Block 2 Data Model
Block_2_Data_Model - categories
Discovering categories and subcategories from Developer Portal

get
https://openapi.emtmadrid.es/v1/mobilitylabs/discover/categories/
Header
Campo	Tipo	Descripción
accessToken	String	
that was got from login method

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=Result OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
structure of values (if operation did well or empty array).

DS_CATEGORY	String	
Name of category into Developers Portal

DS_PHOTO_CAT	String	
Url of category image

DS_SUBCATEGORY	String	
Name of subcategory into Developers Portal

CD_CATEGORY	Integer	
Category code

DS_PHOTO_SUBCAT	String	
Url of subcategory image

DS_URI	String	
Url Resource base (structure depending on CD_DATA_TYPE)

DS_DESCRIPTION_CAT	String	
Description of category

DS_DESCRIPTION_SUBCAT	String	
Description of subcategory

FC_CREATION	Datetime	
Datetime creation of subcategory into Developers Portal

CD_SUBCATEGORY	Integer	
Subcategory code

CD_DATA_TYPE	Integer	
Datatype Code (see Datatypes)

{
{
    "code": "00",
    "data": [
        {
            "DS_CATEGORY": "TRANSPORT",
            "DS_PHOTO_CAT": null,
            "DS_SUBCATEGORY": "BUSEMTMAD",
            "CD_CATEGORY": 1,
            "DS_PHOTO_SUBCAT": "https://mobilitylabs.emtmadrid.es/fs/1/providers/emtPortal/images/subcatsubemtmad",
            "DS_URI": "https://openapi.emtmadrid.es/v1/transport/busemtmad/",
            "DS_DESCRIPTION_CAT": "TRANSPORT EMTMADRID DATA",
            "DS_DESCRIPTION_SUBCAT": "FOR GETTING DATA FROM MADRID EMT BUSES",
            "FC_CREATION": {
                "$date": 1535365773290
            },
            "CD_SUBCATEGORY": 4,
            "CD_DATA_TYPE": 1
        }
    ],
    "description": "Data recovered  OK, (lapsed: 378 millsecs)",
    "datetime": "2018-09-21T06:47:16.587652"
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/mobilitylabs/discover/categories/
url
Headers
Header
accessToken
accessToken
String
Block_2_Data_Model - collection
Recovering all data details from one collection id put into the params

get
https://openapi.emtmadrid.es/v1/mobilitylabs/discover/collection/<CD_COLLECTION>/
Header
Campo	Tipo	Descripción
accessToken	String	
that was got from login method

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=Result OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
structure of values (if operation did well or empty array).

valuations	Array	
Data structure belongs to "data" contains evaluations from developers

links	Array	
Data structure belongs to "data" contains every link related to recovered collection

comments	String	
Data structure belongs to "data" contains comments from developers

general	String	
attributes of collection (see general data description in "collections" method

{
{
    "code": "00",
    "data": [
        {
            "valuations": [],
            "links": [
                {
                    "CD_LINK_TYPE": 4,
                    "DS_LINK": "info/<dateRef>/",
                    "DS_LINK_DESCRIPTION": "List of EMTMADRID lines on a refered date",
                    "CD_LINK": 1
                },
                {
                    "CD_LINK_TYPE": 4,
                    "DS_LINK": "<lineId>/info/<dateRef>/",
                    "DS_LINK_DESCRIPTION": "General Info of a EMTMADRID line on a refered date",
                    "CD_LINK": 2
                },
                {
                    "CD_LINK_TYPE": 4,
                    "DS_LINK": "groups/",
                    "DS_LINK_DESCRIPTION": "Groups of lines ",
                    "CD_LINK": 3
                },
                {
                    "CD_LINK_TYPE": 4,
                    "DS_LINK": "<lineId>/stops/<direction>/",
                    "DS_LINK_DESCRIPTION": "List of Stops bus from EMTMADRID for a specific line",
                    "CD_LINK": 4
                }
            ],
            "comments": [],
            "general": {
                "FS_STATIC": null,
                "DS_DESCRIPTION": "Servicios de EMTMADRID orientados a la información de Líneas y sus datos derivados",
                "DS_ROW_SEPARATOR": null,
                "NM_VERSION": 1,
                "DS_DATE_SEPARATOR": null,
                "CD_SHARING_TYPE": 1,
                "CD_REST_ACTION": 1,
                "COLLECTION": "busemtmad.lines",
                "IT_GEOGRPHIC_INFO": false,
                "CD_COLLECTION": "F0B6DA3B-E4DA-419B-B75C-C3146BE22E67",
                "DS_THOUSANDS_SEPARATOR": null,
                "FC_CREATION": {
                    "$date": 1534173646050
                },
                "DS_FORMAT": null,
                "DS_COLLECTION": "Líneas de EMT",
                "CD_SUBCATEGORY": 2,
                "CD_DATA_TYPE": 1,
                "FC_UPDATE": {
                    "$date": 1534173646050
                },
                "CD_COLLECTION_PARENT": null
            }
        }
    ],
    "description": "Data recovered  OK, (lapsed: 28 millsecs)",
    "datetime": "2018-09-24T06:59:22.361654"
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/mobilitylabs/discover/collection/<CD_COLLECTION>/
url
Headers
Header
accessToken
accessToken
String
Block_2_Data_Model - collections
Recovering all data description from one subcategory from Developers Portal

get
https://openapi.emtmadrid.es/v1/mobilitylabs/discover/collections/<CD_SUBCATEGORY>/
Header
Campo	Tipo	Descripción
accessToken	String	
that was got from login method

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=Result OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
structure of values (if operation did well or empty array).

FS_STATIC	String	
ignore

DS_DESCRIPTION	String	
Collection Name

DS_RESOURCE	String	
Resource Name

NM_VERSION	String	
Version data in resource

DS_SUBCATEGORY	String	
Name of subcategory

DS_DATE_SEPARATOR	String	
date separator

CD_SHARING_TYPE	String	
Sharing type

CD_REST_ACTION	String	
Rest Action type

DS_FORMAT	String	
ignore

IT_GEOGRPHIC_INFO	String	
Contains geographic projection in the structure data

CD_COLLECTION	String	
Id collection

DS_THOUSANDS_SEPARATOR	String	
Thousand separator

FC_CREATION	String	
Creation datetime of collection

DS_ROW_SEPARATOR	String	
Row separator

DS_COLLECTION	String	
Collection description

CD_SUBCATEGORY	String	
Subcategory code

CD_DATA_TYPE	String	
Datatype

FC_UPDATE	String	
Last update

{
{
    "code": "00",
    "data": [
        {
            "FS_STATIC": null,
            "DS_DESCRIPTION": "Datos del calendario de transporte de EMTMADRID",
            "DS_RESOURCE": "calendar",
            "NM_VERSION": 1,
            "DS_SUBCATEGORY": "busemtmad",
            "DS_DATE_SEPARATOR": null,
            "CD_SHARING_TYPE": 1,
            "CD_REST_ACTION": 1,
            "DS_FORMAT": null,
            "IT_GEOGRPHIC_INFO": true,
            "CD_COLLECTION": "2BCAAF2B-CE00-45B5-BB13-4C275E13E4A2",
            "DS_THOUSANDS_SEPARATOR": null,
            "FC_CREATION": {
                "$date": 1534165094620
            },
            "DS_ROW_SEPARATOR": null,
            "DS_COLLECTION": "Calendario de Transporte de EMTMADRID",
            "CD_SUBCATEGORY": 2,
            "CD_DATA_TYPE": 1,
            "FC_UPDATE": {
                "$date": 1534173692117
            },
            "CD_COLLECTION_PARENT": null
        }
    ],
    "description": "Data recovered  OK, (lapsed: 41 millsecs)",
    "datetime": "2018-09-21T16:56:52.154954"
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/mobilitylabs/discover/collections/<CD_SUBCATEGORY>/
url
Headers
Header
accessToken
accessToken
String
Block_2_Data_Model - datatypes
Discovering data types used from Developers Portal. Every type of data is related to specific model of use

List of Datatypes:

1.-WEBSERVICE: Use the API system

2.-STATIC: Contains datasets (files) linked

3.-REACTIVE: May be observed using DDP ReactiveBox system

get
https://openapi.emtmadrid.es/v1/mobilitylabs/discover/datatypes/
Header
Campo	Tipo	Descripción
accessToken	String	
that was got from login method

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=Result OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
structure of values (if operation did well or empty array).

CD_DATA_TYPE	Integer	
Code for Type of data

DS_DATA_TYPE	String	
Description of datatype

{
{
    "code": "00",
    "data": [
        {
            "CD_DATA_TYPE": 1,
            "DS_DATA_TYPE": "WEBSERVICE"
        },
        {
            "CD_DATA_TYPE": 2,
            "DS_DATA_TYPE": "STATIC"
        },
        {
            "CD_DATA_TYPE": 3,
            "DS_DATA_TYPE": "REACTIVE"
        }
    ],
    "description": "Data recovered  OK, (lapsed: 40 millsecs)",
    "datetime": "2018-09-21T16:38:27.086406"
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/mobilitylabs/discover/datatypes/
url
Headers
Header
accessToken
accessToken
String
Block_2_Data_Model - fieldformats
Discovering field formats used from Developers Portal.

get
https://openapi.emtmadrid.es/v1/mobilitylabs/discover/fieldformats/
Header
Campo	Tipo	Descripción
accessToken	String	
that was got from login method

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=Result OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
structure of values (if operation did well or empty array).

CD_TYPE_FIELD	Integer	
Code for Type of field

CD_FORMAT	String	
code of format

CD_DATA_TYPE	String	
Datatype related to

DS_FORMAT	String	
string of data format

{
{
    "code": "00",
    "data": [
        {
            "CD_TYPE_FIELD": 3,
            "CD_FORMAT": 1,
            "CD_DATA_TYPE": 1,
            "DS_FORMAT": "dd/mm/yyyy"
        },
        {
            "CD_TYPE_FIELD": 3,
            "CD_FORMAT": 2,
            "CD_DATA_TYPE": 1,
            "DS_FORMAT": "yyyy-mm-dd"
        },
        {
            "CD_TYPE_FIELD": 3,
            "CD_FORMAT": 3,
            "CD_DATA_TYPE": 1,
            "DS_FORMAT": "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        },
         "description": "Data recovered  OK, (lapsed: 30 millsecs)",
    "datetime": "2018-09-21T16:43:55.421088"
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/mobilitylabs/discover/fieldformats/
url
Headers
Header
accessToken
accessToken
String
Block_2_Data_Model - linktypes
Discovering link types used from Developers Portal. Every type of link is related to specific actions into the system

List of Link types:

1.-General: Only for show or offer more information to developer

2.-Documentation: Url link offer documentation about the Collection

3.-User Guide: Url link contains a guide for users

4.-Http Call: Url contains an end point for a specific method of current collection (for web services types)

5.-File: Url contains one documment downloadable (for static datasets)

6.-Reactive file: Url contains one document reading via query (for reactive data)

get
https://openapi.emtmadrid.es/v1/mobilitylabs/discover/linktypes/
Header
Campo	Tipo	Descripción
accessToken	String	
that was got from login method

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=Result OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
structure of values (if operation did well or empty array).

CD_LINK_TYPE	Integer	
Code for Type of link

DS_LINK_TYPE	String	
Description of link

{
{
    "code": "00",
    "data": [
        {
            "CD_LINK_TYPE": 1,
            "DS_LINK_TYPE": "General"
        }
    ],
    "description": "Data recovered  OK, (lapsed: 24 millsecs)",
    "datetime": "2018-09-21T07:10:11.199930"
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/mobilitylabs/discover/linktypes/
url
Headers
Header
accessToken
accessToken
String
Block_2_Data_Model - restactions
Discovering rest actions used from Developers Portal in webservices

get
https://openapi.emtmadrid.es/v1/mobilitylabs/discover/restactions/
Header
Campo	Tipo	Descripción
accessToken	String	
that was got from login method

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=Result OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
structure of values (if operation did well or empty array).

CD_REST_ACTION	Integer	
Code for restaction

DS_REST_ACTION	String	
Description of restaction

{
{
    "code": "00",
    "data": [
        {
            "CD_REST_ACTION": 1,
            "DS_REST_ACTION": "GET"
        },
        {
            "CD_REST_ACTION": 2,
            "DS_REST_ACTION": "POST"
        },
        {
            "CD_REST_ACTION": 3,
            "DS_REST_ACTION": "PUT"
        },
        {
            "CD_REST_ACTION": 4,
            "DS_REST_ACTION": "DELETE"
        }
    ],
    "description": "Data recovered  OK, (lapsed: 17 millsecs)",
    "datetime": "2018-09-21T16:48:56.318617"
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/mobilitylabs/discover/restactions/
url
Headers
Header
accessToken
accessToken
String
Block_2_Data_Model - resttypes
Discovering rest types used from Developers Portal in webservices

get
https://openapi.emtmadrid.es/v1/mobilitylabs/discover/resttypes/
Header
Campo	Tipo	Descripción
accessToken	String	
that was got from login method

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=Result OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
structure of values (if operation did well or empty array).

CD_REST_TYPE	Integer	
Code for resttype

DS_REST_TYPE	String	
Description of resttype

{
{
    "code": "00",
    "data": [
        {
            "CD_REST_TYPE": 1,
            "DS_REST_TYPE": "REQUEST"
        },
        {
            "CD_REST_TYPE": 2,
            "DS_REST_TYPE": "RESPONSE"
        }
    ],
    "description": "Data recovered  OK, (lapsed: 25 millsecs)",
    "datetime": "2018-09-21T16:51:32.257659"
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/mobilitylabs/discover/resttypes/
url
Headers
Header
accessToken
accessToken
String
Block_2_Data_Model - sharingtypes
Discovering share mode used from Developers Portal in data storage

get
https://openapi.emtmadrid.es/v1/mobilitylabs/discover/sharingtypes/
Header
Campo	Tipo	Descripción
accessToken	String	
that was got from login method

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=Result OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
structure of values (if operation did well or empty array).

IT_ACTIVE	Boolean	
Active sharingtype

CD_SHARING_TYPE	Integer	
Code for sharingtype

DS_SHARING_TYPE	String	
Description of sharingtype

{
{
    "code": "00",
    "data": [
        {
            "IT_ACTIVE": true,
            "CD_SHARING_TYPE": 1,
            "DS_SHARING_TYPE": "PUBLIC"
        },
        {
            "IT_ACTIVE": true,
            "CD_SHARING_TYPE": 2,
            "DS_SHARING_TYPE": "PRIVATE"
        },
        {
            "IT_ACTIVE": true,
            "CD_SHARING_TYPE": 3,
            "DS_SHARING_TYPE": "SHARED"
        }
    ],
    "description": "Data recovered  OK, (lapsed: 25 millsecs)",
    "datetime": "2018-09-21T16:54:45.309656"
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/mobilitylabs/discover/sharingtypes/
url
Headers
Header
accessToken
accessToken
String
Block 3 TRANSPORT BUSEMTMAD
Block_3_TRANSPORT_BUSEMTMAD - Stop Detail
This webmethod shows details of the stop request from EMTMADRID.

get
https://openapi.emtmadrid.es/v1/transport/busemtmad/stops/<stopId>/detail/
Header
Campo	Tipo	Descripción
accessToken	String	
Current token generated from login

Parámetro
Campo	Tipo	Descripción
idStop	String	
Stop number.

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
Main structure of values (if operation did well or empty array) contains below:

stops	Array	
structure of stop values

pmv	String	
If the stop contaions an electronic panel, contains the number (or empty).

name	String	
Stop name.

geometry	String	
geographical position

stop	String	
id Stop.

dataline	Object	
Array of information about the lines using this stop.

headerB	String	
(or headerA) Name of line

direction	String	
B mean from A to B, A mean from B to A.

label	String	
Public name

startTime	String	
time of start the service line

stopTime	String	
time of end the service line

minFreq	String	
minimun frequency of line

maxFreq	String	
Maximun frequency of line

dayType	String	
related to current query (LA.- Working day, SA.- Saturday, FE.- Festive)

line	String	
code line

{
{
    "code": "00",
    "data": [
        {
            "stops": [
                {
                    "pmv": "61242",
                    "name": "Cibeles",
                    "geometry": {
                        "type": "Point",
                        "coordinates": [
                            -3.69214452424823,
                            40.4203613685499
                        ]
                    },
                    "stop": "72",
                    "dataLine": [
                        {
                            "headerB": "CHAMARTIN",
                            "direction": "B",
                            "headerA": "SOL/SEVILLA",
                            "label": "5",
                            "stopTime": "22:58",
                            "minFreq": "9",
                            "startTime": "06:30",
                            "maxFreq": "20",
                            "dayType": "LA",
                            "line": "005"
                        },
                        {
                            "headerB": "PIO XII",
                            "direction": "B",
                            "headerA": "CONDE DE CASAL",
                            "label": "14",
                            "stopTime": "23:30",
                            "minFreq": "5",
                            "startTime": "06:20",
                            "maxFreq": "13",
                            "dayType": "LA",
                            "line": "014"
                        }
                    ],
                    "postalAddress": "Pº de Recoletos, 2 (Pza. de Cibeles)"
                }
    ],
    "description": "Data recovered  OK, (lapsed: 10049 millsecs)",
    "datetime": "2018-10-23T07:00:32.830552"
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/transport/busemtmad/stops/<stopId>/detail/
url
Headers
Header
accessToken
accessToken
String
Parameters
Parameter
idStop
idStop
String
Block_3_TRANSPORT_BUSEMTMAD - Stops Arround Stop
This webmethod shows details of the stop request from EMTMADRID arround one stop in a specific radius.

get
https://openapi.emtmadrid.es/v2/transport/busemtmad/stops/arroundstop/<stopId>/<radius>/
Header
Campo	Tipo	Descripción
accessToken	String	
Current token generated from login

Parámetro
Campo	Tipo	Descripción
stopId	String	
Stop number.

radius	String	
meters arround the stop.

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
Main structure of array values (if operation did well or empty array) contains (in distance order) below:
{Object} geometry GEOJSON coordinates of stop
{String} stopId Stop number
{Integer} metersToPoint Distance on meters from point to stop
{String} stopName Name of stop
{Array} lines array with lines belong to stop, contains bellow:
{String} nameA Name or Header A of line
{String} nameB Name or Header B of line
{Integer} metersFromHeader Distance of referred stop from the header of line
{String} label public code of line
{String} to Position into itinerary (header A to header B is to "B" and viceversa)
{String} line internal code of line

{
{
    {
            "geometry": {
                "type": "Point",
                "coordinates": [
                    -3.71793052606063,
                    40.4384746558032
                ]
            },
            "stopId": 4514,
            "metersToPoint": 0,
            "lines": [
                {
                    "nameB": "PROSPERIDAD",
                    "nameA": "PLAZA DE CRISTO REY",
                    "metersFromHeader": 0,
                    "label": "1",
                    "to": "B",
                    "line": "001"
                },
                {
                    "nameB": "PROSPERIDAD",
                    "nameA": "PLAZA DE CRISTO REY",
                    "metersFromHeader": 8776,
                    "label": "1",
                    "to": "A",
                    "line": "001"
                },
                {
                    "nameB": "HOSPITAL LA PAZ",
                    "nameA": "MONCLOA",
                    "metersFromHeader": 10203,
                    "label": "132",
                    "to": "A",
                    "line": "132"
                },
                {
                    "nameB": "MARQUES DE VIANA",
                    "nameA": "PLAZA DEL CALLAO",
                    "metersFromHeader": 4449,
                    "label": "44",
                    "to": "A",
                    "line": "044"
                },
                {
                    "nameB": "PITIS",
                    "nameA": "MONCLOA",
                    "metersFromHeader": 10881,
                    "label": "82",
                    "to": "A",
                    "line": "082"
                },
                {
                    "nameB": " ",
                    "nameA": "CIRCULAR 2",
                    "metersFromHeader": 2674,
                    "label": "C2",
                    "to": "B",
                    "line": "069"
                }
            ],
            "stopName": "Cristo Rey"
        }
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v2/transport/busemtmad/stops/arroundstop/<stopId>/<radius>/
url
Headers
Header
accessToken
accessToken
String
Parameters
Parameter
stopId
stopId
String
radius
radius
String
Block_3_TRANSPORT_BUSEMTMAD - Stops Arround geographical point
This webmethod shows details of the stop request from EMTMADRID arround one geographical point in a specific radius.

get
https://openapi.emtmadrid.es/v2/transport/busemtmad/stops/arroundxy/<longitude>/<latitude>/<radius>/
Header
Campo	Tipo	Descripción
accessToken	String	
Current token generated from login

Parámetro
Campo	Tipo	Descripción
stopId	String	
Stop number.

radius	String	
meters arround the stop.

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
Main structure of array values (if operation did well or empty array) contains (in distance order) below:
{Object} geometry GEOJSON coordinates of stop
{String} stopId Stop number
{Integer} metersToPoint Distance on meters from point to stop
{String} stopName Name of stop
{Array} lines array with lines belong to stop, contains bellow:
{String} nameA Name or Header A of line
{String} nameB Name or Header B of line
{Integer} metersFromHeader Distance of referred stop from the header of line
{String} label public code of line
{String} to Position into itinerary (header A to header B is to "B" and viceversa)
{String} line internal code of line

{
{
    {
            "geometry": {
                "type": "Point",
                "coordinates": [
                    -3.71793052606063,
                    40.4384746558032
                ]
            },
            "stopId": 4514,
            "metersToPoint": 0,
            "lines": [
                {
                    "nameB": "PROSPERIDAD",
                    "nameA": "PLAZA DE CRISTO REY",
                    "metersFromHeader": 0,
                    "label": "1",
                    "to": "B",
                    "line": "001"
                },
                {
                    "nameB": "PROSPERIDAD",
                    "nameA": "PLAZA DE CRISTO REY",
                    "metersFromHeader": 8776,
                    "label": "1",
                    "to": "A",
                    "line": "001"
                },
                {
                    "nameB": "HOSPITAL LA PAZ",
                    "nameA": "MONCLOA",
                    "metersFromHeader": 10203,
                    "label": "132",
                    "to": "A",
                    "line": "132"
                },
                {
                    "nameB": "MARQUES DE VIANA",
                    "nameA": "PLAZA DEL CALLAO",
                    "metersFromHeader": 4449,
                    "label": "44",
                    "to": "A",
                    "line": "044"
                },
                {
                    "nameB": "PITIS",
                    "nameA": "MONCLOA",
                    "metersFromHeader": 10881,
                    "label": "82",
                    "to": "A",
                    "line": "082"
                },
                {
                    "nameB": " ",
                    "nameA": "CIRCULAR 2",
                    "metersFromHeader": 2674,
                    "label": "C2",
                    "to": "B",
                    "line": "069"
                }
            ],
            "stopName": "Cristo Rey"
        }
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v2/transport/busemtmad/stops/arroundxy/<longitude>/<latitude>/<radius>/
url
Headers
Header
accessToken
accessToken
String
Parameters
Parameter
stopId
stopId
String
radius
radius
String
Block_3_TRANSPORT_BUSEMTMAD - Time arrival bus
This webmethod returns the real time estimation of two buses at specific stop.

post
https://openapi.emtmadrid.es/v2/transport/busemtmad/stops/<stopId>/arrives/<lineArrive>/
Header
Campo	Tipo	Descripción
accessToken	String	
Current token generated from login

Parámetro
Campo	Tipo	Descripción
stopId	String	
Number of bus stop.

lineArrive	String	
Optional, if not exists returns everyone lines of busstop (or specific line referred)

Body	Object	
Must be a JSON structure,
{

"cultureInfo":"??" Could be EN for english or ES for spanish

"Text_StopRequired_YN":"?", Y(es) for getting name stop or N(ot)

"Text_EstimationsRequired_YN":"?", Y(es) for data estimations to arrival Bus or N(ot)

"Text_IncidencesRequired_YN":"?", Y(es) for getting incidents related to lines in this stop s or N(ot)

"DateTime_Referenced_Incidencies_YYYYMMDD":"????????", year-month-day to reference of incidents

}

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
Main structure of values (if operation did well or empty array) contains below:

Arrive	Array	
Array belongs to "data". Structure of arrive stops contains below:

DistanceBus	Integer	
Belongs to "Arrive" struct. Meters of bus to stop.

geometry	Object	
Belongs to "Arrive" struct. GEOJSON coordinates of busPosition.

bus	String	
Belongs to "Arrive" struct. Number of vehicle.

destination	String	
Belongs to "Arrive" struct. Destination of itinerary.

stop	String	
Belongs to "Arrive" struct. Stop number.

positionTypeBus	String	
Belongs to "Arrive" struct. No apply for this version.

isHead	String	
Belongs to "Arrive" struct. No apply for this version.

line	String	
Belongs to "Arrive" struct. Bus Line value.

estimateArrive	String	
Belongs to "Arrive" struct. Seconds to arrive bus (999999 = more than 45 minutes in conventional lines, more than 90 minutes in nightly lines).

StopLines	Array	
Array belongs to "data". Structure of extra-contents (see below):

ExtraInfo	Array	
Belongs to "StopLines" struct (see below):

Data	Array	
Belongs to "StopLines" struct. Names and ids of lines in the stop.

Direction	String	
Data Belongs to "StopLines" struct. Main place of busstop.

Description	String	
Data Belongs to "StopLines" struct. Name of Busstop.

Label	String	
Data Belongs to "StopLines" struct. LabelId of Busstop.

{
{
    "code": "00",
    "description": "Data recovered  OK, (lapsed: 91 millsecs)",
    "datetime": "2019-11-21T07:07:44.352933",
    "data": [
        {
            "Arrive": [
                {
                    "line": "45",
                    "stop": "62",
                    "isHead": "False",
                    "destination": "REINA VICTORIA",
                    "deviation": 0,
                    "bus": 6746,
                    "geometry": {
                        "type": "Point",
                        "coordinates": [
                            -3.6928317878395864,
                            40.413189298653414
                        ]
                    },
                    "estimateArrive": 71,
                    "DistanceBus": 363,
                    "positionTypeBus": "0"
                },
                {
                    "line": "5",
                    "stop": "62",
                    "isHead": "False",
                    "destination": "CHAMARTIN",
                    "deviation": 0,
                    "bus": 4849,
                    "geometry": {
                        "type": "Point",
                        "coordinates": [
                            -3.698391522957107,
                            40.41793218491306
                        ]
                    },
                    "estimateArrive": 74,
                    "DistanceBus": 319,
                    "positionTypeBus": "0"
                },
                {
                    "line": "150",
                    "stop": "62",
                    "isHead": "False",
                    "destination": "VIRGEN CORTIJO",
                    "deviation": 0,
                    "bus": 8199,
                    "geometry": {
                        "type": "Point",
                        "coordinates": [
                            -3.6984145266208115,
                            40.41769667950469
                        ]
                    },
                    "estimateArrive": 248,
                    "DistanceBus": 1137,
                    "positionTypeBus": "0"
                },
                {
                    "line": "27",
                    "stop": "62",
                    "isHead": "False",
                    "destination": "PLAZA CASTILLA",
                    "deviation": 0,
                    "bus": 540,
                    "geometry": {
                        "type": "Point",
                        "coordinates": [
                            -3.691451613067933,
                            40.408835411999064
                        ]
                    },
                    "estimateArrive": 280,
                    "DistanceBus": 1005,
                    "positionTypeBus": "0"
                },
                {
                    "line": "27",
                    "stop": "62",
                    "isHead": "False",
                    "destination": "PLAZA CASTILLA",
                    "deviation": 0,
                    "bus": 517,
                    "geometry": {
                        "type": "Point",
                        "coordinates": [
                            -3.69348715193224,
                            40.40498402900478
                        ]
                    },
                    "estimateArrive": 315,
                    "DistanceBus": 1373,
                    "positionTypeBus": "0"
                },
                {
                    "line": "14",
                    "stop": "62",
                    "isHead": "False",
                    "destination": "PIO XII",
                    "deviation": 0,
                    "bus": 8444,
                    "geometry": {
                        "type": "Point",
                        "coordinates": [
                            -3.6815869982236493,
                            40.40708637967762
                        ]
                    },
                    "estimateArrive": 563,
                    "DistanceBus": 2328,
                    "positionTypeBus": "0"
                },
                {
                    "line": "14",
                    "stop": "62",
                    "isHead": "False",
                    "destination": "PIO XII",
                    "deviation": 0,
                    "bus": 8432,
                    "geometry": {
                        "type": "Point",
                        "coordinates": [
                            -3.683058684333715,
                            40.406755543086994
                        ]
                    },
                    "estimateArrive": 619,
                    "DistanceBus": 2105,
                    "positionTypeBus": "0"
                },
                {
                    "line": "45",
                    "stop": "62",
                    "isHead": "False",
                    "destination": "REINA VICTORIA",
                    "deviation": 0,
                    "bus": 8419,
                    "geometry": {
                        "type": "Point",
                        "coordinates": [
                            -3.6943981701661093,
                            40.39694610870079
                        ]
                    },
                    "estimateArrive": 648,
                    "DistanceBus": 2619,
                    "positionTypeBus": "0"
                },
                {
                    "line": "150",
                    "stop": "62",
                    "isHead": "False",
                    "destination": "VIRGEN CORTIJO",
                    "deviation": 0,
                    "bus": 8201,
                    "geometry": {
                        "type": "Point",
                        "coordinates": [
                            -3.692591774944438,
                            40.420224878645435
                        ]
                    },
                    "estimateArrive": 909,
                    "DistanceBus": 1658,
                    "positionTypeBus": "0"
                },
                {
                    "line": "5",
                    "stop": "62",
                    "isHead": "False",
                    "destination": "CHAMARTIN",
                    "deviation": 0,
                    "bus": 4850,
                    "geometry": {
                        "type": "Point",
                        "coordinates": [
                            -3.6915677427971634,
                            40.435132546254486
                        ]
                    },
                    "estimateArrive": 1127,
                    "DistanceBus": 3876,
                    "positionTypeBus": "0"
                }
            ],
            "StopInfo": [
                {
                    "lines": [
                        {
                            "label": "14",
                            "line": "014",
                            "nameA": "CONDE DE CASAL",
                            "nameB": "PIO XII",
                            "metersFromHeader": 4338,
                            "to": "B"
                        },
                        {
                            "label": "150",
                            "line": "150",
                            "nameA": "SOL SEVILLA",
                            "nameB": "VIRGEN CORTIJO",
                            "metersFromHeader": 1718,
                            "to": "B"
                        },
                        {
                            "label": "27",
                            "line": "027",
                            "nameA": "EMBAJADORES",
                            "nameB": "PLAZA CASTILLA",
                            "metersFromHeader": 4015,
                            "to": "B"
                        },
                        {
                            "label": "45",
                            "line": "045",
                            "nameA": "LEGAZPI",
                            "nameB": "REINA VICTORIA",
                            "metersFromHeader": 4388,
                            "to": "B"
                        },
                        {
                            "label": "5",
                            "line": "005",
                            "nameA": "SOL SEVILLA",
                            "nameB": "CHAMARTIN",
                            "metersFromHeader": 1718,
                            "to": "B"
                        },
                        {
                            "label": "N1",
                            "line": "501",
                            "nameA": "CIBELES",
                            "nameB": "SANCHINARRO",
                            "metersFromHeader": 954,
                            "to": "B"
                        },
                        {
                            "label": "N22",
                            "line": "522",
                            "nameA": "CIBELES",
                            "nameB": "BARRIO DEL PILAR",
                            "metersFromHeader": 954,
                            "to": "B"
                        },
                        {
                            "label": "N24",
                            "line": "524",
                            "nameA": "CIBELES",
                            "nameB": "LAS TABLAS",
                            "metersFromHeader": 954,
                            "to": "B"
                        }
                    ],
                    "stopId": "62",
                    "stopName": "Castellana-Ministerio Interior",
                    "geometry": {
                        "type": "Point",
                        "coordinates": [
                            -3.68907087764756,
                            40.4292626043241
                        ]
                    },
                    "Direction": "Pº de la Castellana, 20                                "
                }
            ],
            "ExtraInfo": [],
            "Incident": {
                "ListaIncident": {
                    "data": [
                        {
                            "title": "Evento Deportivo: Zona Bernabéu. Afectadas 4 líneas de EMT.",
                            "guid": "CE371CE0-ECBC-4897-866F-4F1B681B28DE",
                            "description": "El 23 de noviembre de 18:30 a 23:45 horas, aproximadamente, las líneas 14, 43, 120 y 150, tendrán retenciones y modificaciones en sus itinerarios, por la celebración de evento deportivo en el estadio Santiago Bernabéu, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal.  Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Wed, 20 Nov 2019 10:19:38 GMT",
                            "rssFrom": "23/11/2019 18:30:00",
                            "rssTo": "23/11/2019 23:45:00",
                            "cause": "12 - Evento deportivo",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191123-Bernabeu.pdf",
                                "@type": "application/pdf",
                                "@length": "179259"
                            }
                        },
                        {
                            "title": "Manifestación: Zona Alcalá - Sol. Afectadas 15 líneas de EMT.",
                            "guid": "C71DF3E7-81A0-4EB2-A38E-BAFD4E0AA743",
                            "description": "El 23 de noviembre de 18:00 a 21:00 horas aproximadamente, las líneas 1, 2, 3, 5, 9, 15, 20, 46, 51, 52, 53, 74, 146, 150 y M2, tendrán retenciones y modificaciones en sus itinerarios en Alcalá y plaza Puerta del Sol por manifestación, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal.  Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Wed, 20 Nov 2019 08:25:24 GMT",
                            "rssFrom": "23/11/2019 18:00:00",
                            "rssTo": "23/11/2019 21:00:00",
                            "cause": "04 - Manifestación",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191123-Manifestacion-Alcal-Sol.pdf",
                                "@type": "application/pdf",
                                "@length": "52764"
                            }
                        },
                        {
                            "title": "Manifestación: Distrito Centro. Afectadas 20 líneas de EMT.",
                            "guid": "8BD81D86-2565-411C-AFD8-A5F39D272337",
                            "description": "El 22 de noviembre de 19:00 a 21:30 horas aproximadamente, las líneas 1, 2, 3, 5, 9, 15, 20, 44, 46, 51, 52, 53, 74, 75, 133, 146, 147, 148, 150 y M2, tendrán retenciones y modificaciones en sus itinerarios en Alcalá, Gran Vía y San Bernardo por manifestación, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal.  Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Tue, 19 Nov 2019 15:03:54 GMT",
                            "rssFrom": "22/11/2019 19:00:00",
                            "rssTo": "22/11/2019 21:30:00",
                            "cause": "04 - Manifestación",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191122-Manifestacion-Alcala-San-Bernardo.pdf",
                                "@type": "application/pdf",
                                "@length": "53226"
                            }
                        },
                        {
                            "title": "Evento deportivo: Distritos Chamartín y Retiro. Afectadas 48 líneas de EMT.",
                            "guid": "7A8FBC91-6ADB-48D2-9E13-5B6844EEC718",
                            "description": "El día, 17 de noviembre, de 5:00 a 11:00 horas, aproximadamente, las líneas 1, 2, 5, 7, 9, 10, 11, 12, 14, 15, 16, 20, 21, 27, 29, 34, 37, 40, 43, 45, 51, 52, 53, 61, 70, 74, 87, 107, 120, 126, 129, 146, 147, 150, C1, C2, E1, Exprés Aeropuerto, S.E. [Atocha Renfe - Nuevos Ministerios], N9, N10, N11, N12, N13, N14, N15, N17, N25, tendrán retenciones y modificaciones en sus itinerarios en los distritos de Chamartín y Retiro por evento deportivo, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal. Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Fri, 15 Nov 2019 15:47:54 GMT",
                            "rssFrom": "17/11/2019 5:00:00",
                            "rssTo": "17/11/2019 11:00:00",
                            "cause": "12 - Evento deportivo",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191117-Carrera-Marca.pdf",
                                "@type": "application/pdf",
                                "@length": "509595"
                            }
                        },
                        {
                            "title": "Manifestación: Alcalá y plaza Puerta del Sol. Afectadas 14 líneas de EMT.",
                            "guid": "7E9B3AE2-C9EF-4E38-B577-74B0C7DCE54A",
                            "description": "El 17 de noviembre de 17:00 a 21:00 horas aproximadamente, las líneas 1, 2, 3, 5, 9, 15, 20, 46, 51, 52, 53, 74, 146 y 150, tendrán retenciones y modificaciones en sus itinerarios en Alcalá y plaza Puerta del Sol por manifestación, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal.  Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/900-CGI-icono-RSS_Corporativo.png'/></p>",
                            "pubDate": "Thu, 14 Nov 2019 13:13:35 GMT",
                            "rssFrom": "17/11/2019 17:00:00",
                            "rssTo": "17/11/2019 21:00:00",
                            "cause": "04 - Manifestación",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191117-Manifestacion-Alcala-Sol.pdf",
                                "@type": "application/pdf",
                                "@length": "52276"
                            }
                        },
                        {
                            "title": "Manifestación: Zona Centro. Afectadas 16 líneas de EMT.",
                            "guid": "11D8814A-A612-44AF-A4F6-1269FD11E837",
                            "description": "El 17 de noviembre de 12:00 a 14:00 horas aproximadamente, las líneas 3, 5, 10, 14, 15, 20, 27, 34, 37, 45, 51, 53, 150, E1, Exprés Aeropuerto y SE [Atocha Renfe – Nuevos Ministerios], tendrán retenciones y modificaciones en sus itinerarios en plaza Puerta del Sol, Alcalá, Cedaceros, carrera San Jerónimo, plaza Cánovas del Castillo y paseo Prado por manifestación, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal.  Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/900-CGI-icono-RSS_Corporativo.png'/></p>",
                            "pubDate": "Thu, 14 Nov 2019 11:23:53 GMT",
                            "rssFrom": "17/11/2019 12:00:00",
                            "rssTo": "17/11/2019 14:00:00",
                            "cause": "04 - Manifestación",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191117-Manifestacion-Sol-Paseo-Prado.pdf",
                                "@type": "application/pdf",
                                "@length": "52488"
                            }
                        },
                        {
                            "title": "Concentración: Zona Colón. Afectadas 14 líneas de EMT.",
                            "guid": "EEB6DD5F-DE25-4024-81EE-DD842F28316A",
                            "description": "El 16 de noviembre de 14:00 a 17:00 horas aproximadamente, las líneas 1, 5, 9, 14, 19, 21, 27, 37, 45, 51, 53, 74, 150 y SE [Atocha Renfe, Nuevos Ministerios], tendrán retenciones y modificaciones en sus itinerarios en plaza Colón, Goya y Serrano por concentración, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal.  Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Wed, 13 Nov 2019 12:46:04 GMT",
                            "rssFrom": "16/11/2019 14:00:00",
                            "rssTo": "16/11/2019 17:00:00",
                            "cause": "04 - Manifestación",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191116-Concentracion-Colon.pdf",
                                "@type": "application/pdf",
                                "@length": "53353"
                            }
                        },
                        {
                            "title": "Manifestación: Distrito Centro. Afectadas 20 líneas de EMT.",
                            "guid": "629A4949-DDB5-4C88-B10F-1ED86F542027",
                            "description": "El 16 de noviembre de 11:00 a 14:00 horas aproximadamente, las líneas 1, 2, 3, 5, 9, 14, 15, 20, 27, 37, 45, 51, 52, 53, 74, 146, 150, E1, Exprés Aeropuerto y SE [Atocha Renfe - Nuevos Ministerios], tendrán retenciones y modificaciones en sus itinerarios en plaza Puerta del Sol, Alcalá y plaza Cibeles por manifestación, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal.  Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Wed, 13 Nov 2019 09:24:46 GMT",
                            "rssFrom": "16/11/2019 11:00:00",
                            "rssTo": "16/11/2019 14:00:00",
                            "cause": "04 - Manifestación",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191116-Manifestacion-Sol-Cibeles.pdf",
                                "@type": "application/pdf",
                                "@length": "53239"
                            }
                        },
                        {
                            "title": "Manifestación: Varios distritos. Afectadas 94 líneas de EMT.",
                            "guid": "421A40EE-F3F8-474A-B3B8-36096E915FD1",
                            "description": "El 15 de noviembre de 21:15 a 23:59 horas aproximadamente, las líneas 1, 2, 3, 5, 6, 7, 9, 10, 12, 14, 15, 16, 19    20, 21, 24, 25, 26, 27, 28, 29, 32, 34, 37, 39, 40, 44, 45   46, 51, 52, 53, 54, 57, 59, 61, 62, 63, 64, 66, 74, 75, 82   83, 85, 86, 124, 126, 127, 128, 132, 133, 138, 141, 146, 147  148, 149, 150, 152, C1, C2, E1, F, G, Exprés Aeropuerto, SE [Atocha Renfe, Nuevos Ministerios], N1, N2, N3, N4, N5, N6, N7, N8, N9, N10  N11, N12, N13, N14, N15, N16, N17, N18, N19, N20, N21, N22, N23, N24, N25, N26, N27, tendrán retenciones y modificaciones en sus itinerarios en varios distritos, por manifestación, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal. Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/900-CGI-icono-RSS_Corporativo.png'/></p>",
                            "pubDate": "Tue, 12 Nov 2019 11:20:03 GMT",
                            "rssFrom": "15/11/2019 21:15:00",
                            "rssTo": "15/11/2019 23:59:00",
                            "cause": "04 - Manifestación",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191115-Manifestacion-Cibeles-Cibeles.pdf",
                                "@type": "application/pdf",
                                "@length": "55377"
                            }
                        },
                        {
                            "title": "Concentración: Calle Alcalá. Afectadas 6 líneas de EMT",
                            "guid": "835A03C0-2CB5-4C21-8420-F1DDFF382E44",
                            "description": "El 15 de noviembre de 10:00 a 15:00 horas aproximadamente, las líneas 5, 15, 20, 51, 53 y 150, tendrán retenciones y modificaciones en sus itinerarios en calle Alcalá por concentración, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal.  Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Tue, 12 Nov 2019 09:18:46 GMT",
                            "rssFrom": "15/11/2019 10:00:00",
                            "rssTo": "15/11/2019 15:00:00",
                            "cause": "04 - Manifestación",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191115-Concentracion-Alcala.pdf",
                                "@type": "application/pdf",
                                "@length": "51899"
                            }
                        },
                        {
                            "title": "Concentración: Zona plaza Colón. Afectadas 14 líneas de EMT.",
                            "guid": "3013235A-E9C5-44A1-B1CB-6B3AFF041A76",
                            "description": "El 8 de noviembre de 18:00 a 23:00 horas aproximadamente, las líneas 1, 5, 9, 14, 19, 21, 27, 37, 45, 51, 53, 74, 150 y SE [Atocha Renfe - Nuevos Ministerios], tendrán retenciones y modificaciones en sus itinerarios en plaza Colón, Goya y Serrano por concentración, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal.  Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Fri, 08 Nov 2019 07:54:46 GMT",
                            "rssFrom": "08/11/2019 18:00:00",
                            "rssTo": "08/11/2019 23:00:00",
                            "cause": "04 - Manifestación",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191108-Concentracion-Colon.pdf",
                                "@type": "application/pdf",
                                "@length": "53465"
                            }
                        },
                        {
                            "title": "Manifestación: Distritos Moncloa y Centro. Afectadas 27 líneas de EMT.",
                            "guid": "F50CD40E-6399-4526-A815-775413A8E5AB",
                            "description": "El 7 de noviembre de 11:00 a 13:00 horas aproximadamente, las líneas 1, 2, 3, 5, 6, 15, 20, 21, 25, 26, 32, 39, 44, 46, 51, 53, 62, 74, 75, 133, 138, 146, 147, 148, 150, C1 y C2, tendrán retenciones y modificaciones en sus itinerarios en Ferraz, Marqués de Urquijo, Princesa, plaza España, Gran Vía, plaza Callao, plaza Puerta del Sol y carrera San Jerónimo por manifestación, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal.  Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Wed, 06 Nov 2019 14:40:34 GMT",
                            "rssFrom": "07/11/2019 11:00:00",
                            "rssTo": "07/11/2019 13:00:00",
                            "cause": "04 - Manifestación",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191107-Manifestacion-Ferraz-San-Jeronimo.pdf",
                                "@type": "application/pdf",
                                "@length": "53963"
                            }
                        },
                        {
                            "title": "Incidencia en la calzada: Zona Centro. Afectadas 18 líneas de EMT.",
                            "guid": "E30C25C8-7882-40EA-B74E-93F4564B5A0D",
                            "description": "Con motivo de una incidencia en la calzada que se está produciendo en plaza Puerta del Sol, plaza Callao, Gran Vía y Alcalá, las líneas 1, 2, 3, 5, 15, 20, 44, 46, 51, 52, 53, 75, 133, 146, 147, 148, 150 y M2, están teniendo modificaciones de itinerario y alteraciones en sus frecuencias de paso.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Tue, 05 Nov 2019 18:49:43 GMT",
                            "rssFrom": "05/11/2019 19:45:00",
                            "rssTo": "05/11/2019 20:30:00",
                            "cause": "04 - Manifestación",
                            "effect": "04 - Desvío sobrevenido",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191105-Incidencia-Calzada-Zona-Centro.pdf",
                                "@type": "application/pdf",
                                "@length": "71985"
                            }
                        },
                        {
                            "title": "Evento Deportivo: Zona Bernabéu. Afectadas 4 líneas de EMT.",
                            "guid": "58E2102C-A867-4C62-B6A8-8FFDE64299BC",
                            "description": "El 6 de noviembre de 18:30 a 23:45 horas, aproximadamente, las líneas 14, 43, 120 y 150, tendrán retenciones y modificaciones en sus itinerarios, por la celebración de evento deportivo en el estadio Santiago Bernabéu, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal.  Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Sun, 03 Nov 2019 08:04:48 GMT",
                            "rssFrom": "06/11/2019 18:30:00",
                            "rssTo": "06/11/2019 23:45:00",
                            "cause": "12 - Evento deportivo",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191106-Bernabeu.pdf",
                                "@type": "application/pdf",
                                "@length": "183685"
                            }
                        },
                        {
                            "title": "Evento deportivo: Distritos Chamberí y Salamanca. Afectadas 19 líneas de EMT.",
                            "guid": "0D9DBA3F-6F7D-463A-BBF4-982364227B7C",
                            "description": "Día 3 de noviembre de 7:00 a 11:00 horas, aproximadamente, las líneas 2, 3, 5, 7, 12, 14, 16, 21, 27, 37, 40, 45, 53, 61, 126, 147, 150, C1 y S.E. [Atocha Renfe - Nuevos Ministerios]  tendrán retenciones y modificaciones en sus itinerarios en Santander, paseo San Francisco de Sales, avenida Pablo Iglesias, Bravo Murillo, José Abascal, paseo Castellana, paseo Recoletos, Raimundo Fernández Villaverde, Agustín de Betancourt y Ríos Rosas, por evento deportivo, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal. Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Thu, 31 Oct 2019 18:01:52 GMT",
                            "rssFrom": "03/11/2019 7:00:00",
                            "rssTo": "03/11/2019 11:00:00",
                            "cause": "12 - Evento deportivo",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191103-Carrera-Go-Fit.pdf",
                                "@type": "application/pdf",
                                "@length": "1308056"
                            }
                        },
                        {
                            "title": "Evento Deportivo: Zona Bernabéu. Afectadas 4 líneas de EMT.",
                            "guid": "E79F8C8B-04A5-4E32-A707-990E72885830",
                            "description": "El 2 de noviembre de 18:30 a 23:45 horas, aproximadamente, las líneas 14, 43, 120 y 150, tendrán retenciones y modificaciones en sus itinerarios, por la celebración de evento deportivo en el estadio Santiago Bernabéu, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal.  Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Wed, 30 Oct 2019 18:56:57 GMT",
                            "rssFrom": "02/11/2019 18:30:00",
                            "rssTo": "02/11/2019 23:45:00",
                            "cause": "12 - Evento deportivo",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191102-Bernabeu.pdf",
                                "@type": "application/pdf",
                                "@length": "180440"
                            }
                        },
                        {
                            "title": "Manifestación: Distrito Centro. Afectadas 27 líneas de EMT.",
                            "guid": "949AB715-7161-432C-B0CF-E1362399A682",
                            "description": "El 2 de noviembre de 17:00 a 20:00 horas aproximadamente, las líneas 1, 2, 3, 5, 9, 14, 15, 20, 27, 37, 44, 45, 46, 51, 52, 53, 74, 75, 133, 146, 147, 148, 150, E1, M2, SE [Atocha Renfe - Nuevos Ministerios] y Exprés Aeropuerto, tendrán retenciones y modificaciones en sus itinerarios en Gran Vía, Alcalá y plaza Cibeles por manifestación, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal.  Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Wed, 30 Oct 2019 17:57:00 GMT",
                            "rssFrom": "02/11/2019 17:00:00",
                            "rssTo": "02/11/2019 20:00:00",
                            "cause": "04 - Manifestación",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191102-Manifestacion-Gran-Via-Cibeles.pdf",
                                "@type": "application/pdf",
                                "@length": "130862"
                            }
                        },
                        {
                            "title": "Evento Deportivo: Zona Bernabéu. Afectadas 4 líneas de EMT.",
                            "guid": "14689639-615F-4455-B6E2-9F7940C81588",
                            "description": "El 30 de octubre de 18:30 a 24:00 horas, aproximadamente, las líneas 14, 43, 120 y 150, tendrán retenciones y modificaciones en sus itinerarios, por la celebración de evento deportivo en el estadio Santiago Bernabéu, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal.  Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Sun, 27 Oct 2019 08:04:43 GMT",
                            "rssFrom": "30/10/2019 18:30:00",
                            "rssTo": "30/10/2019 23:59:00",
                            "cause": "12 - Evento deportivo",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191030-Bernabeu.pdf",
                                "@type": "application/pdf",
                                "@length": "180427"
                            }
                        },
                        {
                            "title": "Traslado de parada: Paseo Recoletos. Afectadas 11 líneas de EMT.",
                            "guid": "2D4CAF73-1CC9-46F6-80C1-2E320F19DA62",
                            "description": "Desde las 10:00 horas del 24 de octubre a fin de obras, las líneas 5, 14, 27, 37, 45, 53, 150, N1, N4, N22 y N24, trasladan provisionalmente la parada 72, ubicada en paseo Recoletos 2, al número 8 de la misma calle. Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Sun, 27 Oct 2019 01:36:03 GMT",
                            "rssFrom": "24/10/2019 10:00:00",
                            "rssTo": "24/11/2019 23:30:00",
                            "cause": "08 - Obras",
                            "effect": "09 - Parada trasladada",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191024-Traslado-Parada-72-Paseo-Recoletos.pdf",
                                "@type": "application/pdf",
                                "@length": "37636"
                            }
                        },
                        {
                            "title": "Evento deportivo: Varios distritos. Afectadas 44 líneas de EMT.",
                            "guid": "4025F0EA-E90E-4A4A-9277-45BD79CCB83D",
                            "description": "El 27 de octubre de 9:00 a 13:00 horas, aproximadamente, las líneas 1, 2, 3, 5, 7, 9, 10, 11, 12, 14, 15, 16, 20, 21, 27, 34, 37, 40, 43, 44, 45, 46, 51, 52, 53, 61, 62, 74, 83, 120, 126, 133, 138, 146, 147, 150, 160, 161, 162, C1, C2, E1, Exprés Aeropuerto y S.E. [Atocha Renfe, Nuevos Ministerios], tendrán retenciones y modificaciones en sus itinerarios en varios distritos, por evento deportivo, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal. Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Sun, 27 Oct 2019 01:17:12 GMT",
                            "rssFrom": "27/10/2019 9:00:00",
                            "rssTo": "27/10/2019 13:00:00",
                            "cause": "12 - Evento deportivo",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191027-Media-Maraton-Mujer.pdf",
                                "@type": "application/pdf",
                                "@length": "1586948"
                            }
                        },
                        {
                            "title": "Concentración: Distrito Retiro. Afectadas 19 líneas de EMT.",
                            "guid": "346A2C9A-AF8C-4C44-B701-0B9A1D1DCF16",
                            "description": "El 24 de octubre, de 10:30 a 13:00 horas aproximadamente, las líneas 1, 2, 5, 9, 14, 15, 20, 27, 37, 45, 51, 52, 53, 74, 146, 150, E1, SE [Atocha Renfe - Nuevos Ministerios] y Exprés Aeropuerto, tendrán retenciones y modificaciones en sus itinerarios en plaza de Cibeles por concentración, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal.  Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Sun, 27 Oct 2019 01:13:58 GMT",
                            "rssFrom": "24/10/2019 10:30:00",
                            "rssTo": "24/10/2019 13:00:00",
                            "cause": "04 - Manifestación",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191024-Concentracion-Plaza-Cibeles.pdf",
                                "@type": "application/pdf",
                                "@length": "53177"
                            }
                        },
                        {
                            "title": "Traslado de Parada: Paseo Castellana. Afectada línea 27 de EMT.",
                            "guid": "CD97A11A-3D05-4B44-AE1B-E9740EA14251",
                            "description": "A partir del 19 de noviembre la línea 27, traslada definitivamente la parada 49 ubicada en paseo Castellana frente al número 110, a la parada 5613 ubicada en paseo Castellana 67. Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Sat, 16 Nov 2019 12:38:29 GMT",
                            "rssFrom": "19/11/2019 5:00:00",
                            "rssTo": "19/11/2020 23:30:00",
                            "cause": "99 - Otras causas",
                            "effect": "09 - Parada trasladada",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/2019119-Traslado-Parada-49-Paseo-Castellana.pdf",
                                "@type": "application/pdf",
                                "@length": "44825"
                            }
                        },
                        {
                            "title": "Manifestación: Prado - Atocha. Afectadas 14 líneas de EMT.",
                            "guid": "99CBCD48-44B3-468C-A476-B555D5BA0120",
                            "description": "El 17 de noviembre de 12:00 a 15:00 horas aproximadamente, las líneas 6, 10, 14, 26, 27, 32, 34, 37, 45, 50, 65, E1, Exprés Aeropuerto y SE [Atocha Renfe - Nuevos Ministerios], tendrán retenciones y modificaciones en sus itinerarios en Prado - Atocha por manifestación, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal.  Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Thu, 14 Nov 2019 12:53:37 GMT",
                            "rssFrom": "17/11/2019 12:00:00",
                            "rssTo": "17/11/2019 15:00:00",
                            "cause": "04 - Manifestación",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191117-Manifestacion-Prado-Jacinto-Benavente.pdf",
                                "@type": "application/pdf",
                                "@length": "53817"
                            }
                        },
                        {
                            "title": "Manifestación: Zona Legazpi. Afectadas 15 líneas de EMT.",
                            "guid": "2A263444-4651-4A95-B65E-08801ED53CA4",
                            "description": "El 11 de noviembre de 19:30 a 21:30 horas aproximadamente, las líneas 6, 8, 19, 27, 45, 47, 55, 59, 62, 76, 85, 86, 102, 247 y E1, tendrán retenciones y modificaciones en sus itinerarios en paseo de las Delicias y plaza de la Beata María Ana de Jesús por manifestación, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal.  Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Fri, 08 Nov 2019 15:05:36 GMT",
                            "rssFrom": "11/11/2019 19:30:00",
                            "rssTo": "11/11/2019 21:30:00",
                            "cause": "04 - Manifestación",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191111-Manifestacion-Legazpi-Plaza-Beata.pdf",
                                "@type": "application/pdf",
                                "@length": "53212"
                            }
                        },
                        {
                            "title": "Manifestación: Distrito Tetuán. Afectadas 18 líneas de EMT.",
                            "guid": "8A287D62-B7C9-4651-9BF1-7DF3B4DA6F09",
                            "description": "El 24 de octubre de 15:15 a 16:15 horas aproximadamente, las líneas 5, 27, 42, 49, 66, 67, 70, 107, 124, 129, 134, 135, 147, 173, 175, 176, 177 y 178, tendrán retenciones y modificaciones en sus itinerarios en Mártires de la Ventilla, paseo Castellana, plaza Castilla y Bravo Murillo (hasta pasaje de la Remonta) por manifestación, según su desarrollo y de acuerdo con las restricciones de tráfico que realice Policía Municipal.  Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Sun, 27 Oct 2019 01:36:32 GMT",
                            "rssFrom": "24/10/2019 15:15:00",
                            "rssTo": "24/10/2019 16:15:00",
                            "cause": "04 - Manifestación",
                            "effect": "05 - Desvío programado",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191024-Manifestacion-Martires-Ventilla-Bravo-Murillo.pdf",
                                "@type": "application/pdf",
                                "@length": "53242"
                            }
                        },
                        {
                            "title": "Modificación de paradas: Glorieta Presidente García Moreno. Afectada línea 45 de EMT.",
                            "guid": "D6D28236-CB61-47E3-B2B7-68DDD5CBA59B",
                            "description": "A partir del 12 de noviembre, la línea 45, modifica de manera definitiva la ubicación de la parada cabecera ubicada en la glorieta Presidente García Moreno.   Ver más detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/00-Logo-RSS_Corporativo.png'/></p>",
                            "pubDate": "Sat, 09 Nov 2019 10:09:45 GMT",
                            "rssFrom": "12/11/2019 6:00:00",
                            "rssTo": "19/11/2019 23:30:00",
                            "cause": "99 - Otras causas",
                            "effect": "09 - Parada trasladada",
                            "moreInfo": {
                                "@url": "http://feeds.emtmadrid.es:8082/docs/20191112-Cambio-Cabecera-Linea-45.pdf",
                                "@type": "application/pdf",
                                "@length": "50848"
                            }
                        }
                    ]
                }
            }
        }
    ]
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v2/transport/busemtmad/stops/<stopId>/arrives/<lineArrive>/
url
Headers
Header
accessToken
accessToken
String
Parameters
Parameter
stopId
stopId
String
lineArrive
lineArrive
String
Body
Body
Object
Block_3_TRANSPORT_BUSEMTMAD - calendar
This webmethod returns the EMT transport bus calendar

get
https://openapi.emtmadrid.es/v1/transport/busemtmad/calendar/<startdate>/<enddate>/
Header
Campo	Tipo	Descripción
accessToken	String	
Current token generated from login

Parámetro
Campo	Tipo	Descripción
startdate	String	
Date start on YYYYMMDD formnat

entdate	String	
Date end on YYYYMMDD format

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
Main structure of values (if operation did well or empty array) contains below:

date	String	
Calendar date

Strike	String	
in this day or not (depending on this attribute, data planification could have change).

dayType	String	
(LA.- Working day, SA.- Saturday, FE.- Holly day

{
{
    "code": "00",
    "data": [
        {
            "date": "01/01/2018 0:00:00",
            "strike": "N",
            "dayType": "FE"
        },
        {
            "date": "02/01/2018 0:00:00",
            "strike": "N",
            "dayType": "LA"
        },
        {
            "date": "03/01/2018 0:00:00",
            "strike": "N",
            "dayType": "LA"
        }
    ],
    "description": "Data recovered  OK, (lapsed: 156 millsecs)",
    "datetime": "2018-09-24T07:14:43.076404"
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/transport/busemtmad/calendar/<startdate>/<enddate>/
url
Headers
Header
accessToken
accessToken
String
Parameters
Parameter
startdate
startdate
String
entdate
entdate
String
Block_3_TRANSPORT_BUSEMTMAD - incidents
This webmethod returns details about incidents or alterations in line services on feed format (json).

get
https://openapi.emtmadrid.es/v1/transport/busemtmad/lines/incidents/<lineid>/
Header
Campo	Tipo	Descripción
accessToken	String	
Current token generated from login

Parámetro
Campo	Tipo	Descripción
lineid	String	
Line that wants to know about incidents (value "all" for recovering all lines)

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
Main structure of values (if operation did well or empty array) contains below:

date	String	
Date generator of feed

copyright	String	
Copyright

title	String	
Title of feed data

image	String	
General image of feed and data asociated

generator	String	
ignored

language	String	
ignored

link	String	
feed location

item	Array	
of individual incidents data

Category	Array	
lines related to incident belongs to "item"

GoogleTransitEffect	String	
In GoogleTransit, incident of this context (effect)

rssAfectaDesde	String	
Start time estimated of incident

pubDate	String	
date of publication

author	String	
author-department of publish

MEDIA	String	
ignore

rssAfectaHasta	String	
end of incident (estimate)

rsspublisher	String	
publisher of incident

rssfinaldate	String	
end of publish

guid	String	
unique id for reference each incident

enclosure	String	
data link in external document

GoogleTransitCause	String	
In GoogleTransit, incident of this context (Cause)

{
{
    "code": "00",
    "data": [
        {
            "lastBuildDate": "Sun, 23 Sep 2018 17:46:09 GMT",
            "description": "Informaciones de interes para el cliente de EMT de Madrid",
            "copyright": "Empresa Municipal de Transportes de Madrid",
            "title": "Canal de Noticias de la Empresa Municipal de Transportes de Madrid",
            "image": {
                "url": "http://feeds.emtmadrid.es:8082/images/logoemthorizontal.gif",
                "link": "http://www.emtmadrid.es/",
                "description": null,
                "title": "Canales de informaciÃ³n de EMT"
            },
            "generator": null,
            "language": null,
            "link": "http://feeds.feedburner.com/emtmadrid",
            "item": [
                {
                    "category": [
                        "2",
                        "143",
                        "145"
                    ],
                    "GoogleTransitEffect": "09 - Parada trasladada",
                    "rssAfectaDesde": "17/09/2018 8:00:00",
                    "description": "Desde las 8:00 horas, del 17 de septiembre, a fin de obras, las lÃ\u00adneas 2, 30, 56, 71, 143 y 145, trasladan de manera provisional la parada 151, ubicada en Doctor Esquerdo 49, 30 metros antes en la misma calle. Ver mÃ¡s detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/900-CGI-icono-RSS_Corporativo.png'/></p>",
                    "pubDate": "Fri, 14 Sep 2018 14:19:48 GMT",
                    "author": "cgi@emtmadrid.es",
                    "MEDIA": {
                        "MEDIA": {
                            "@AltTitle": "",
                            "@AltDescription": "",
                            "@Type": "",
                            "@Format": ""
                        }
                    },
                    "title": "Obras: Doctor Esquerdo, Afectadas 6 la lineas de EMT.",
                    "rssAfectaHasta": "14/09/2019 16:28:00",
                    "link": "http://feeds.emtmadrid.es:8082/docs/20180917-Traslado-Parada-151-Doctor-Esquerdo.pdf",
                    "rsspublisher": "cgi@emtmadrid.es",
                    "rssfinaldate": "14/10/2018 16:15:00",
                    "guid": "8C118D8E-5038-4DAC-801B-5148E73907E4",
                    "enclosure": {
                        "@length": "59734",
                        "@type": "application/pdf",
                        "@url": "http://feeds.emtmadrid.es:8082/docs/20180917-Traslado-Parada-151-Doctor-Esquerdo.pdf"
                    },
                    "GoogleTransitCause": "08 - Obras"
                },
                {
                    "category": [
                        "1",
                        "203",
                        "E1",
                        "N8",
                        "N11"
                    ],
                    "GoogleTransitEffect": "05 - DesvÃ\u00ado programado",
                    "rssAfectaDesde": "16/09/2018 5:00:00",
                    "description": "El domingo 16 de septiembre, de 5:00 a 22:30 horas aproximadamente, las lÃ\u00adneas 1, 2, 5, 6, 7, 9, 10, 14, 15, 16, 19, 20, 21, 24, 26, 27  32   34, 37, 45, 51, 52, 53, 54, 57, 59, 61, 63, 74, 85, 86  141, 143, 145, 146, 147, 150, C1, C2, E1, N8, N9, N10, N11 y ExprÃ©s Aeropuerto, tendrÃ¡n retenciones y modificaciones en sus itinerarios, en AutovÃ\u00ada A3, avenida MediterrÃ¡neo, paseo Reina Cristina, paseo Infanta Isabel, plaza Emperador Carlos V, paseo Prado, plaza CÃ¡novas del Castillo, plaza Cibeles, paseo Recoletos, plaza ColÃ³n y paseo Castellana, por la celebraciÃ³n de la Ãºltima etapa de la Vuelta Ciclista a EspaÃ±a, segÃºn su desarrollo y de acuerdo con las restricciones de trÃ¡fico que realice PolicÃ\u00ada Municipal. Ver mÃ¡s detalle en documento adjunto.<p><img src='http://feeds.emtmadrid.es:8082/images/900-CGI-icono-RSS_Corporativo.png'/></p>",
                    "pubDate": "Fri, 14 Sep 2018 11:38:47 GMT",
                    "author": "cgi@emtmadrid.es",
                    "MEDIA": {
                        "MEDIA": {
                            "@AltTitle": "",
                            "@AltDescription": "",
                            "@Type": "",
                            "@Format": ""
                        }
                    }
            ],
            "ttl": "120",
            "webMaster": "opendata@emtmadrid.es"
        }
    ],
    "description": "Data recovered  OK, (lapsed: 410 millsecs)",
    "datetime": "2018-09-24T07:23:51.011002"
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/transport/busemtmad/lines/incidents/<lineid>/
url
Headers
Header
accessToken
accessToken
String
Parameters
Parameter
lineid
lineid
String
Block_3_TRANSPORT_BUSEMTMAD - infoline(detail)
This webmethod returns the detail of one EMT line

get
https://openapi.emtmadrid.es/v1/transport/busemtmad/lines/<lineId>/info/<dateref>/
Header
Campo	Tipo	Descripción
accessToken	String	
Current token generated from login

Parámetro
Campo	Tipo	Descripción
lineId	String	
Line (or label) for getting data

dateref	String	
date reference on YYYYMMDD format

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
Main structure of values (if operation did well or empty array) contains below:

dateRef	String	
date reference of line data

line	Object	
Group of data

nameA	String	
Name of Header A

nameB	String	
Name of Header B

label	String	
Public line code

timeTable	Object	
Timetable of line in all typeDays

{
{
    "code": "00",
    "data": [
        {
            "dateRef": "01/07/2018",
            "nameA": "CIRCULAR 1",
            "label": "C1",
            "timeTable": [
                {
                    "Direction2": {
                        "MaximumFrequency": "30",
                        "MinimunFrequency": "8",
                        "FrequencyText": "De 07:00 a 23:30 -> Cada 8 - 30min./",
                        "StopTime": "23:30",
                        "StartTime": "07:00"
                    },
                    "Direction1": {
                        "MaximumFrequency": "30",
                        "MinimunFrequency": "8",
                        "FrequencyText": "De 07:00 a 23:00 -> Cada 8 - 30min./",
                        "StopTime": "23:00",
                        "StartTime": "07:00"
                    },
                    "idDayType": "FESTIVO"
                },
                {
                    "Direction2": {
                        "MaximumFrequency": "11",
                        "MinimunFrequency": "4",
                        "FrequencyText": "De 06:00 a 23:30 -> Cada 4 - 11min./",
                        "StopTime": "23:30",
                        "StartTime": "06:00"
                    },
                    "Direction1": {
                        "MaximumFrequency": "11",
                        "MinimunFrequency": "4",
                        "FrequencyText": "De 05:35 a 23:00 -> Cada 4 - 11min./",
                        "StopTime": "23:00",
                        "StartTime": "05:35"
                    },
                    "idDayType": "LABORABLE"
                },
                {
                    "Direction2": {
                        "MaximumFrequency": "14",
                        "MinimunFrequency": "6",
                        "FrequencyText": "De 06:00 a 23:30 -> Cada 6 - 14min./",
                        "StopTime": "23:30",
                        "StartTime": "06:00"
                    },
                    "Direction1": {
                        "MaximumFrequency": "14",
                        "MinimunFrequency": "6",
                        "FrequencyText": "De 05:35 a 23:00 -> Cada 6 - 14min./",
                        "StopTime": "23:00",
                        "StartTime": "05:35"
                    },
                    "idDayType": "SABADO"
                }
            ],
            "nameB": "CIRCULAR 1",
            "line": "068"
        }
    ],
    "description": "Data recovered  OK, (lapsed: 31493 millsecs)",
    "datetime": "2018-09-25T07:20:01.765757"
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/transport/busemtmad/lines/<lineId>/info/<dateref>/
url
Headers
Header
accessToken
accessToken
String
Parameters
Parameter
lineId
lineId
String
dateref
dateref
String
Block_3_TRANSPORT_BUSEMTMAD - infoline(general)
This webmethod returns the list of lines actives in the reference date

get
https://openapi.emtmadrid.es/v2/transport/busemtmad/lines/info/<dateref>/
Header
Campo	Tipo	Descripción
accessToken	String	
Current token generated from login

Parámetro
Campo	Tipo	Descripción
dateref	String	
date reference on YYYYMMDD format

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
Main structure of values (if operation did well or empty array) contains below:

group	String	
Group that belongs each line.

line	String	
Line of EMT.

nameA	String	
Name of Header A

nameB	String	
Name of Header B

label	String	
Public code of line

startDate	String	
Date init of current line configuration

endDate	String	
Date end of current line configuration

{
{
    "code": "00",
    "data": [
        {
            "startDate": "20/08/2018",
            "group": "110",
            "nameA": "CRISTO REY",
            "endDate": "31/12/2999",
            "label": "1",
            "nameB": "PROSPERIDAD",
            "line": "001"
        },
        {
            "startDate": "20/08/2018",
            "group": "110",
            "nameA": "MANUEL BECERRA",
            "endDate": "31/12/2999",
            "label": "2",
            "nameB": "REINA VICTORIA",
            "line": "002"
        }
    ],
    "description": "Data recovered  OK, (lapsed: 971 millsecs)",
    "datetime": "2018-09-18T06:39:34.496482"
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v2/transport/busemtmad/lines/info/<dateref>/
url
Headers
Header
accessToken
accessToken
String
Parameters
Parameter
dateref
dateref
String
Block_3_TRANSPORT_BUSEMTMAD - infostops(general)
This webmethod returns the list of stops actives in the commercial operation

post
https://openapi.emtmadrid.es/v1/transport/busemtmad/stops/list/
Header
Campo	Tipo	Descripción
accessToken	String	
Current token generated from login

Parámetro
Campo	Tipo	Descripción
liststops	Array	
(optional in the body, array of list stops, i.e. [62,1234] )

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
Main structure of values (if operation did well or empty array) contains below:

group	String	
Group that belongs each line.

node	String	
number of stop

geometry	String	
GEO-JSON geometry of stop point

wifi	String	
1.- this stop contains a wifi AP

lines	Array	
array of lines belong to stop

name	String	
Name of bus stop

{
{
    "code": "00",
    "description": "1 Records recovered (lapsed: 85 millsecs)",
    "datetime": "2019-11-21T07:12:38.149689",
    "data": [
        {
            "node": "1",
            "geometry": {
                "type": "Point",
                "coordinates": [
                    -3.78288324038992,
                    40.4701435453176
                ]
            },
            "name": "Avenida Valdemarín-Blanca de Castilla",
            "wifi": "0",
            "lines": [
                "161/1"
            ]
        }
    ]
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/transport/busemtmad/stops/list/
url
Headers
Header
accessToken
accessToken
String
Parameters
Parameter
liststops
liststops
Array
Block_3_TRANSPORT_BUSEMTMAD - line stops
This webmethod returns the list of stops of a line-direction and every line that coincides with some stop

get
https://openapi.emtmadrid.es/v1/transport/busemtmad/lines/<lineId>/stops/<direction>/
Header
Campo	Tipo	Descripción
accessToken	String	
Current token generated from login

Parámetro
Campo	Tipo	Descripción
lineId	String	
Line EMT

direction	String	
1.- From A to B 2.- From B to A

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
Main structure of values (if operation did well or empty array) contains below:

timetable	Array	
Array of timetables of every line (see infoline/detail)

line	String	
Line number

stops	Array	
Array contains detail of bus stops

pmv	String	
If exists electronic panel, the idNumber

name	String	
Stop name

geometry	Object	
GEO-JSON projection

stop	String	
Number of stop

postalAddress	String	
Direction

dataLine	Array	
Lines using this stop

{
{
    "code": "00",
    "data": [
       {
            "timeTable": [
                {
                    "IdLine": "068",
                    "nameA": "CIRCULAR 1",
                    "typeOfDays": [
                        {
                            "Direction2": {
                                "MaximumFrequency": "30",
                                "MinimunFrequency": "8",
                                "StopTime": "23:00",
                                "Frequency": null,
                                "StartTime": "07:00"
                            },
                            "Direction1": {
                                "MaximumFrequency": "30",
                                "MinimunFrequency": "8",
                                "StopTime": "23:30",
                                "Frequency": null,
                                "StartTime": "07:00"
                            },
                            "DayType": "FE"
                        }
               }
            ]
        },
                   "line": "068",
            "stops": [
                {
                    "pmv": "61322",
                    "name": "CUATRO CAMINOS",
                    "geometry": {
                        "type": "Point",
                        "coordinates": [
                            -3.7030778919681,
                            40.4468405647608
                        ]
                    },
                    "stop": "1890",
                    "dataLine": "068",
                    "postalAddress": "Raimundo Fernández Villaverde, 2"
                },
                {
                    "pmv": "61401",
                    "name": "FERNANDEZ VILLAVERDE-PONZANO",
                    "geometry": {
                        "type": "Point",
                        "coordinates": [
                            -3.69892299910574,
                            40.4465323874369
                        ]
                    },
                    "stop": "2693",
                    "dataLine": [
                        "068",
                        "149"
                    ],
                    "postalAddress": "Raimundo Fernández Villaverde, 32"
                }
    ],
    "description": "Data recovered  OK, (lapsed: 971 millsecs)",
    "datetime": "2018-09-18T06:39:34.496482"
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/transport/busemtmad/lines/<lineId>/stops/<direction>/
url
Headers
Header
accessToken
accessToken
String
Parameters
Parameter
lineId
lineId
String
direction
direction
String
Block_3_TRANSPORT_BUSEMTMAD - list stops
This webmethod returns the list of stops from EMTMADRID.

post
https://openapi.emtmadrid.es/v1/transport/busemtmad/stops/list/
Header
Campo	Tipo	Descripción
accessToken	String	
Current token generated from login

Parámetro
Campo	Tipo	Descripción
stops	Array	
Optional, array contains a list of stops for getting.

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
Main structure of values (if operation did well or empty array) contains below:

node	String	
Stop number.

geometry	String	
geographical position.

wifi	String	
Indicates if stop offer public wifi.

lines	String	
Array of line/direction used by node.

name	String	
Stop name.

{
{
    "code": "00",
    "data": [
        {
            "node": "1",
            "geometry": {
                "type": "Point",
                "coordinates": [
                    -3.782776719960321,
                    40.47005438829035
                ]
            },
            "wifi": "0",
            "lines": [
                "161/1/1"
            ],
            "name": "Avenida Valdemarín-Blanca de Castilla"
        },
        {
            "node": "72",
            "geometry": {
                "type": "Point",
                "coordinates": [
                    -3.692039901493752,
                    40.420272966069064
                ]
            },
            "wifi": "0",
            "lines": [
                "5/1/1",
                "14/1/1",
                "27/1/1",
                "37/2/1",
                "45/1/1",
                "53/1/1",
                "150/1/1",
                "501/1/1",
                "501/2/1",
                "504/1/1",
                "504/2/1",
                "522/1/1",
                "522/2/1",
                "524/1/1",
                "524/2/1",
                "525/2/1",
                "526/2/1"
            ],
            "name": "Cibeles"
        }
    ],
    "description": "Data recovered  OK, (lapsed: 10049 millsecs)",
    "datetime": "2018-10-23T07:00:32.830552"
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/transport/busemtmad/stops/list/
url
Headers
Header
accessToken
accessToken
String
Parameters
Parameter
stops
stops
Array
Block_3_TRANSPORT_BUSEMTMAD - operation groups
This webmethod returns the list of groups of operations related to EMT lines

get
https://openapi.emtmadrid.es/v1/transport/busemtmad/lines/groups/
Header
Campo	Tipo	Descripción
accessToken	String	
Current token generated from login

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
Main structure of values (if operation did well or empty array) contains below:

group	String	
Group that belongs each line.

subGroup	String	
Subgroup of line

{
{
    "code": "00",
    "data": [
        {
    "code": "00",
    "data": [
        {
            "group": "100",
            "description": "LINEAS CONVENCIONALES",
            "subGroup": "110"
        },
        {
            "group": "100",
            "description": "LINEAS CENTROS DE TRABAJO",
            "subGroup": "120"
        },
        {
            "group": "100",
            "description": "LINEAS MINIBUSES",
            "subGroup": "155"
        }
    ],
    "description": "Data recovered  OK, (lapsed: 971 millsecs)",
    "datetime": "2018-09-18T06:39:34.496482"
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/transport/busemtmad/lines/groups/
url
Headers
Header
accessToken
accessToken
String
Block_3_TRANSPORT_BUSEMTMAD - route of line
This webmethod returns the full itinerary for a line

get
https://openapi.emtmadrid.es/v1/transport/busemtmad/lines/<labelId>/route/
Header
Campo	Tipo	Descripción
accessToken	String	
Current token generated from login

Parámetro
Campo	Tipo	Descripción
labelId	String	
Public id of line (or line number)

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Object	
Main structure of values (if operation did well or empty object) contains below:

number	String	
of line

public	String	
label of line

nameSectionA	String	
direction name A

nameSectionB	String	
direction name B

stops	Object	
Structure of arrays contains stops locations and asociates values

itinerary	Object	
Structure of arrays contains multiline strings of itinerary

{

{
    "code": "00",
    "description": "Data recovered  OK, (lapsed: 238 millsecs)",
    "data": {
        "label": "1",
        "line": "001",
        "nameSectionA": "PLAZA DE CRISTO REY",
        "nameSectionB": "PROSPERIDAD",
        "itinerary": {
            "toA": {
                "type": "FeatureCollection",
                "features": [
                    {
                        "type": "Feature",
                        "properties": {
                            "id": 5,
                            "name": "Calle de López de Hoyos",
                            "distance": -18,
                            "stroke-width": 5,
                            "stroke": "#43b433"
                        },
                        "geometry": {
                            "type": "MultiLineString",
                            "coordinates": [
                                [
                                    [
                                        -3.6746843390381,
                                        40.4438633809632
                                    ],
                                    [
                                        -3.67533436446322,
                                        40.4433633826127
                                    ]
                                ]
                            ]
                        }
                    },
        ..etc..
            },
            "toB": {
                "type": "FeatureCollection",
                "features": [
                    {
                        "type": "Feature",
                        "properties": {
                            "id": 115,
                            "name": "Calle de Isaac Peral",
                            "distance": -38,
                            "stroke-width": 5,
                            "stroke": "#43b4bb"
                        },
                        "geometry": {
                            "type": "MultiLineString",
                            "coordinates": [
                                [
                                    [
                                        -3.71773577756945,
                                        40.4388033963104
                                    ],
                                    [
                                        -3.71783578404514,
                                        40.4381933984801
                                    ]
                                ]
                            ]
                        }
                    },
                    {
                        "type": "Feature",
                        "properties": {
                            "id": 78,
                            "name": "Calle de Isaac Peral",
                            "distance": 30,
                            "stroke-width": 5,
                            "stroke": "#43b4bb"
                        },
                        "geometry": {
                            "type": "MultiLineString",
                            "coordinates": [
                                [
                                    [
                                        -3.71783578404514,
                                        40.4381933984801
                                    ],
                                    [
                                        -3.71787578668127,
                                        40.4379734009539
                                    ]
                                ]
                            ]
                        }
                    },
                    {

...etc
        },
        "stops": {
            "toA": {
                "type": "FeatureCollection",
                "features": [
                    {
                        "type": "Feature",
                        "properties": {
                            "stopNum": 273,
                            "stopName": "Prosperidad",
                            "distance": 0
                        },
                        "geometry": {
                            "type": "Point",
                            "coordinates": [
                                -3.67489619473516,
                                40.4438010524072
                            ]
                        }
                    },
                    {
                        "type": "Feature",
                        "properties": {
                            "stopNum": 716,
                            "stopName": "Cartagena-López de Hoyos",
                            "distance": 169
                        },
                        "geometry": {
                            "type": "Point",
                            "coordinates": [
                                -3.67581845367846,
                                40.4425749529
                            ]
                        }
                    },
                    {
                        "type": "Feature",
                        "properties": {
                            "stopNum": 718,
                            "stopName": "Cartagena",
                            "distance": 536
                        },
                        "geometry": {
                            "type": "Point",
                            "coordinates": [
                                -3.6737781993913,
                                40.4396634181182
                            ]
                        }
                    },
 ...etc...
            },
            "toB": {
                "type": "FeatureCollection",
                "features": [
                    {
                        "type": "Feature",
                        "properties": {
                            "stopNum": 4514,
                            "stopName": "Cristo Rey",
                            "distance": 0
                        },
                        "geometry": {
                            "type": "Point",
                            "coordinates": [
                                -3.71793052606063,
                                40.4384746558032
                            ]
                        }
                    },
                    {
                        "type": "Feature",
                        "properties": {
                            "stopNum": 4022,
                            "stopName": "Junta Municipal Moncloa",
                            "distance": 295
                        },
                        "geometry": {
                            "type": "Point",
                            "coordinates": [
                                -3.71842134613126,
                                40.4358409821157
                            ]
                        }
                    },
...etc...
    }
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/transport/busemtmad/lines/<labelId>/route/
url
Headers
Header
accessToken
accessToken
String
Parameters
Parameter
labelId
labelId
String
Block_3_TRANSPORT_BUSEMTMAD - stops arround places
This webmethod returns the list of stops and lines nearby street or places

get
https://openapi.emtmadrid.es/v1/transport/busemtmad/stops/arroundstreet/<namePlace>/<number_or_zero_street_number>/radius/
Header
Campo	Tipo	Descripción
accessToken	String	
Current token generated from login

Parámetro
Campo	Tipo	Descripción
namePlace	String	
partial name of place or street

number	Int	
number of street (0 if not need or for getting the first number of street)

radius	Int	
meters arround de place

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
Main structure of values (if operation did well or empty array) contains attributes of place, stops and lines belongs to each stop

{
{
    "code": "00",
    "data": [
          {
            "Description": "ALCALA",
            "StreetType": "CALLE",
            "geometry": {
                "type": "Point",
                "coordinates": [
                    -3.69689639836373,
                    40.418458268702
                ]
            },
            "NumberType": "NUM",
            "Stop": [
                {
                    "PMV": null,
                    "Name": "Sevilla",
                    "geometry": {
                        "type": "Point",
                        "coordinates": [
                            -3.69747025696256,
                            40.4187708509133
                        ]
                    },
                    "IdStop": "3411",
                    "PostalAdress": "Alcalá, 37",
                    "Line": {
                        "HeaderB": "HORTALEZA",
                        "Direction": "B",
                        "IdLine": "009",
                        "TipoDia": "LA",
                        "FrequencyS1": null,
                        "FrequencyS2": null,
                        "Label": "9",
                        "MaximumFrequency": "20",
                        "StopTime": "00:09",
                        "HeaderA": "SEVILLA",
                        "StartTime": "05:55",
                        "DayType": "LABORABLE",
                        "MinimunFrequency": "5"
                    }
                },
                {
                    "PMV": null,
                    "Name": "Círculo de Bellas Artes",
                    "geometry": {
                        "type": "Point",
                        "coordinates": [
                            -3.69714696173351,
                            40.4189619874668
                        ]
                    },
                    "IdStop": "164",
                    "PostalAdress": "Gran Vía  con C/ Alcalá",
                    "Line": [
                        {
                            "HeaderB": "PROSPERIDAD",
                            "Direction": "B",
                            "IdLine": "001",
                            "TipoDia": "LA",
                            "FrequencyS1": null,
                            "FrequencyS2": null,
                            "Label": "1",
                            "MaximumFrequency": "22",
                            "StopTime": "23:15",
                            "HeaderA": "CRISTO REY",
                            "StartTime": "06:30",
                            "DayType": "LABORABLE",
                            "MinimunFrequency": "11"
                        },
                        {
                            "HeaderB": "REINA VICTORIA",
                            "Direction": "A",
                            "IdLine": "002",
                            "TipoDia": "LA",
                            "FrequencyS1": null,
                            "FrequencyS2": null,
                            "Label": "2",
                            "MaximumFrequency": "15",
                            "StopTime": "00:21",
                            "HeaderA": "MANUEL BECERRA",
                            "StartTime": "06:00",
                            "DayType": "LABORABLE",
                            "MinimunFrequency": "7"
                            ....
                ....
        ....
    ],
    "description": "Data recovered  OK, (lapsed: 971 millsecs)",
    "datetime": "2018-09-18T06:39:34.496482"
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/transport/busemtmad/stops/arroundstreet/<namePlace>/<number_or_zero_street_number>/radius/
url
Headers
Header
accessToken
accessToken
String
Parameters
Parameter
namePlace
namePlace
String
number
number
Int
radius
radius
Int
Block_3_TRANSPORT_BUSEMTMAD - timetable start/stop
This webmethod returns start and stop time from one EMT line

get
https://openapi.emtmadrid.es/v1/transport/busemtmad/lines/<lineId>/timetable/
Header
Campo	Tipo	Descripción
accessToken	String	
Current token generated from login

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
Main structure of values (if operation did well or empty array) contains below:

dateIni	String	
start of current planification

dateEnd	String	
end of current planification

firstTimeServiceA	String	
First time in reference date from A to B

firstTimeServiceB	String	
First time in reference date from B to A

endTimeServiceA	String	
Last time in reference date from A to B

endTimeServiceB	String	
Last time in reference date from B to A

dayType	String	
Related to line timetable and calendar

line	String	
idLine EMT

{
{
    "code": "00",
    "data": [
        {
            "dateIni": "03/09/2018",
            "endTimeServiceB": "22/09/2018 5:30:00",
            "firstTimeServiceB": "21/09/2018 23:50:00",
            "dateEnd": "10/10/2018",
            "endTimeServiceA": "22/09/2018 5:50:00",
            "dayType": "V",
            "firstTimeServiceA": "21/09/2018 23:45:00",
            "line": "501"
        },
        {
            "dateIni": "03/09/2018",
            "endTimeServiceB": "24/09/2018 5:50:00",
            "firstTimeServiceB": "23/09/2018 23:30:00",
            "dateEnd": "10/10/2018",
            "endTimeServiceA": "24/09/2018 5:10:00",
            "dayType": "FE",
            "firstTimeServiceA": "23/09/2018 23:55:00",
            "line": "501"
        }
    ],
    "description": "Data recovered  OK, (lapsed: 971 millsecs)",
    "datetime": "2018-09-18T06:39:34.496482"
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/transport/busemtmad/lines/<lineId>/timetable/
url
Headers
Header
accessToken
accessToken
String
Block_3_TRANSPORT_BUSEMTMAD - timetable trips
This webmethod returns the list of trips of operations related to EMT lines

get
https://openapi.emtmadrid.es/v1/transport/busemtmad/lines/<lineId>/trips/<dateRef>/
Header
Campo	Tipo	Descripción
accessToken	String	
Current token generated from login

Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
Main structure of values (if operation did well or empty array) contains below:

logicBus	String	
Unique internal id in simualtaneous service

direction	String	
From A to B or from B to A

tripNum	String	
Number of trip

startTimeTrip	String	
Theoretical init trip

endTimeTrip	String	
Theoretical end trip

date	String	
Date reference

dayType	String	
Related to line timetable and calendar

{
{
    "code": "00",
    "data": [
          "logicBus": "001",
            "direction": "1",
            "tripNum": "0001",
            "endTimeTrip": "00:30:00",
            "startTimeTrip": "23:45:00",
            "date": "21/09/2018",
            "dayType": "V",
            "line": "501"
        },
        {
            "logicBus": "001",
            "direction": "2",
            "tripNum": "0002",
            "endTimeTrip": "01:40:00",
            "startTimeTrip": "00:30:00",
            "date": "21/09/2018",
            "dayType": "V",
            "line": "501"
        }
    ],
    "description": "Data recovered  OK, (lapsed: 971 millsecs)",
    "datetime": "2018-09-18T06:39:34.496482"
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/transport/busemtmad/lines/<lineId>/trips/<dateRef>/
url
Headers
Header
accessToken
accessToken
String
Block_3_TRANSPORT_BUSEMTMAD - travelplan
This webmethod calculates route between two points

post
https://openapi.emtmadrid.es/v1/transport/busemtmad/travelplan/
Header
Campo	Tipo	Descripción
accessToken	String	
Current token generated from login

Parámetro
Campo	Tipo	Descripción
Structure	Object	
in JSON format contains the route criteria:

routeType	String	
P (Public transport) C (by Car from/to Parking) W (Walking) M (Mixed)

coordinateXFrom	String	
longitude origin

coordinateYFrom	String	
latitude origin

coordinateXTo	String	
longitude destination

coordinateYTo	String	
latitude destination

originName	String	
Symbolic name of origin

destinationName	String	
Symbolic name of destination

polygon	String	
Optional Array on JSON format contains coordinates of polygon. (If input, represents one exclusion area and route surround it).

day	Numeric	
optional day of routeplan

month	Numeric	
optional month of routeplan

year	Numeric	
optional year of routeplan

hour	Numeric	
optional hour of routeplan

minute	Numeric	
optional minute of routeplan

culture	String	
EN (english) ES (spanish)

itinerary	Boolean	
(if true returns the full multiline string for mapping representations)

allowBus	Boolean	
(if true and routeType is "M" or "P" recover routes by bus)

allowBike	Boolean	
(if true and routeType is "M" or "P" recover routes by public bike)

preferPublic	Boolean	
(reserved for future use)

isResidentOrInvited	Boolean	
(reserved for future use)

isEnvFriendly	Boolean	
(reserved for future use)

usingTaxi	Boolean	
(reserved for future use)

usingRentedCar	Boolean	
(reserved for future use)

{
{
    "routeType": "P",
    "itinerary": true,
    "coordinateXFrom": -3.701077,
    "coordinateYFrom": 40.4469,
    "coordinateXTo": -3.674902,
    "coordinateYTo": 40.400149,
    "originName": "Calle Maudes 6",
    "destinationName": "Calle Cerro de la Plata 4",
    "polygon": null,
    "day": 2,
    "month": 4,
    "year": 2019,
    "hour": 18,
    "minute": 18,
    "culture": "es",
    "allowBus": true,
    "allowBike": false
}
Success 200
Campo	Tipo	Descripción
code	String	
Result of operation (00=OK)

description	String	
Description of success

datetime	String	
Instant of current operation in server side

data	Array	
Main structure of values (if operation did well or empty array) contains below:

distance	Numeric	
total distance from origin to destination

departureTime	String	
Time of departure

arrivalTime	String	
estimation of arrival time

duration	String	
estimated duration

sections	Array	
Every part of itinerary, contains:

type	String	
Travel mode (Walk, Bus, etc)

order	Numeric	
order of section in general route

source	Object	
data instructions of origin point of section

destination	Object	
data instructions of destination point of section

route	Array	
instructions (in GEOJSON format) of route (IE, relation of stops, data of bicimad bases)

itinerary	Array	
design (in GEOJSON format) of route representation in a map (requires itinerary = true in params)

{
{
    "code": "00",
    "data": [

        "distance":8.4062,
        "departureTime":"25/04/2019 7:03:03",
        "description":"Ruta calculada para ir de Calle Maudes 6 a Calle Cerro de la Plata 4. Tiempo aproximado: 59 minutos.",
        "arrivalTime":"25/04/2019 8:02:03",
        "duration":59,
        "sections":[
        {
        "distance":0.41514,
        "route":{},
        "destination":{
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.69624611625539,
        40.4464701726132
        ]
        },
        "type":"Feature",
        "properties":{
        "name":"Mariano de Cavia"
        }
        },
        "source":{},
        "itinerary":{
        "type":"LineString",
        "coordinates":[
        [
        -3.70108243991047,
        40.4468153417153
        ],
        [
        -3.70094000021951,
        40.4468099996286
        ],
        [
        -3.70085499709828,
        40.4468034610392
        ],
        [
        -3.70068000002996,
        40.4467900003619
        ],
        [
        -3.7004900005336,
        40.4467600003904
        ],
        [
        -3.70032000009262,
        40.4466599995661
        ],
        [
        -3.7001400004886,
        40.446659999917
        ],
        [
        -3.69994999998615,
        40.4466600000889
        ],
        [
        -3.6993499997779,
        40.4466399996743
        ],
        [
        -3.69891000000644,
        40.4466199997678
        ],
        [
        -3.69890503456515,
        40.4466197928042
        ],
        [
        -3.69867000020574,
        40.4466099996073
        ],
        [
        -3.69822999964324,
        40.4465899998188
        ],
        [
        -3.69822913829575,
        40.4465899491572
        ],
        [
        -3.69788999990992,
        40.4465700001213
        ],
        [
        -3.6973999994868,
        40.4465400003074
        ],
        [
        -3.69713000047065,
        40.4465200001247
        ],
        [
        -3.6968799998172,
        40.4464999996628
        ]
        ]
        },
        "duration":6.4,
        "type":"Walk",
        "order":1
        },
        {
        "distance":6.70678,
        "idLine":"68",
        "route":{
        "type":"FeatureCollection",
        "features":[
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.696263628,
        40.44640877
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"2695",
        "description":"Fernández Villaverde-Alonso Cano"
        }
        },
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.692368623,
        40.446211406
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"2696",
        "description":"Nuevos Ministerios"
        }
        },
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.690122168,
        40.44606716
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"421",
        "description":"Nuevos Ministerios-Joaquín Costa"
        }
        },
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.686238131,
        40.445639794
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"420",
        "description":"República Argentina"
        }
        },
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.684855729,
        40.444391223
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"4678",
        "description":"Metro República Argentina"
        }
        },
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.683259697,
        40.442855608
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"2698",
        "description":"Joaquín Costa-Velázquez"
        }
        },
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.681302878,
        40.440979751
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"4676",
        "description":"Glorieta López de Hoyos"
        }
        },
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.679389361,
        40.43914865
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"2700",
        "description":"Francisco Silvela-Príncipe de Vergara"
        }
        },
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.677236663,
        40.437008106
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"789",
        "description":"Intercambiador de Avenida de América"
        }
        },
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.673968638,
        40.433964105
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"786",
        "description":"Diego de León"
        }
        },
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.671653374,
        40.431261336
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"811",
        "description":"Francisco Silvela-Juan Bravo"
        }
        },
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.66950165,
        40.428647663
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"3829",
        "description":"Manuel Becerra"
        }
        },
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.669031288,
        40.427578307
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"1427",
        "description":"Manuel Becerra"
        }
        },
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.668913996,
        40.425254653
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"148",
        "description":"Doctor Esquerdo-Goya"
        }
        },
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.673519819,
        40.424894624
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"1252",
        "description":"Palacio de los Deportes"
        }
        },
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.67675215,
        40.42393884
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"2152",
        "description":"Felipe II"
        }
        },
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.676228785,
        40.421113059
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"2154",
        "description":"O Donnell-Narváez"
        }
        },
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.676439674,
        40.418625331
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"2156",
        "description":"Narváez-Ibiza"
        }
        },
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.678414139,
        40.416866026
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"814",
        "description":"Menéndez Pelayo-Sainz de Baranda"
        }
        },
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.677552845,
        40.414429616
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"816",
        "description":"Hospital Niño Jesús"
        }
        },
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.676659895,
        40.410957342
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"2213",
        "description":"Plaza Niño Jesús"
        }
        },
        {
        "geometry":{
        "type":"Point",
        "coordinates":[
        -3.678656982,
        40.408170866
        ]
        },
        "type":"Feature",
        "properties":{
        "idStop":"2211",
        "description":"Mariano de Cavia"
        }
        }
        ]
        },
        "destination":{},
        "source":{
        "geometry":{},
        "type":"Feature",
        "properties":{
        "idStop":"2695",
        "name":"Fernández Villaverde-Alonso Cano",
        "description":"Coja la línea de bus 68"
        }
        },
        "itinerary":{},
        "duration":35.67435,
        "type":"Bus",
        "order":2 
        ....
        ....
  
    ],
    "description": "Data recovered  OK, (lapsed: 971 millsecs)",
    "datetime": "2018-09-18T06:39:34.496482"
}
Enviar una petición de ejemplo

https://openapi.emtmadrid.es/v1/transport/busemtmad/travelplan/
url
Headers
Header
accessToken
accessToken
String
Parameters
Parameter
Structure
Structure
Object
routeType
routeType
String
coordinateXFrom
coordinateXFrom
String
coordinateYFrom
coordinateYFrom
String
coordinateXTo
coordinateXTo
String
coordinateYTo
coordinateYTo
String
originName
originName
String
destinationName
destinationName
String
polygon
polygon
String
day
day
Numeric
month
month
Numeric
year
year
Numeric
hour
hour
Numeric
minute
minute
Numeric
culture
culture
String
itinerary
itinerary
Boolean
allowBus
allowBus
Boolean
allowBike
allowBike
Boolean
preferPublic
preferPublic
Boolean
isResidentOrInvited
isResidentOrInvited
Boolean
isEnvFriendly
isEnvFriendly
Boolean
usingTaxi
usingTaxi
Boolean
usingRentedCar
usingRentedCar
Boolean
