# EMT Madrid MobilityLabs API Reference

> API para integrar colecciones y datos del portal de desarrolladores de EMT Madrid.  
> Base URL: `https://openapi.emtmadrid.es`  
> Documentación oficial: https://mobilitylabs.emtmadrid.es  
> Código fuente y utilidades: https://gitlab.com/mobilitylabsmadrid

---

## Autenticación

Casi todos los endpoints requieren un `accessToken` obtenido con el endpoint de login.  
El token se renueva automáticamente con cada llamada a la API.

---

## Block 0 — General

### GET `/v1/hello/`

Devuelve el estado de la API y del servidor.

**Respuesta:**
```json
{
  "APIVersion": { "description": "OPENAPI for public access", "version": "00107" },
  "code": "00",
  "developerPortal": "https://mobilitylabs.emtmadrid.es",
  "instant": "2019-10-01T16:52:31.664108",
  "message": "Hello, here openapi.emtmadrid.es, I am running Ok and I feel good",
  "versions": ["v1", "build 06", "v2", "build 10"]
}
```

### GET `/v1/`

Devuelve el estado básico del servidor.

---

## Block 1 — User Identity

### GET `/v?/mobilitylabs/user/login/`

Crea una sesión en el contexto de la API. Hay 3 modos de uso:

- **Basic**: Hasta 25k hits/día. Requiere `email` + `password`.
- **Advanced**: Hasta 250k hits/día. Requiere `email`, `password`, `X-ApiKey`, `X-ClientId`.
- **Protected**: Igual que Advanced pero con sesión de hasta 86400s. Requiere `X-ClientId` + `passKey`.

**Headers de request:**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `email` | String | Email registrado en MobilityLabs (obligatorio sin passKey) |
| `password` | String | Contraseña personal (obligatorio sin passKey) |
| `X-ApiKey` | String | *(deprecated)* Usar `passKey` en su lugar |
| `X-ClientId` | String | Opcional con email/password; obligatorio con passKey |
| `passKey` | String | Opcional. Obligatorio si no hay email/password |

**Respuesta (200):**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `code` | String | Resultado (`00` = OK) |
| `description` | String | Descripción del resultado |
| `datetime` | String | Timestamp de la operación |
| `data[].userName` | String | Nombre de usuario |
| `data[].accessToken` | String | **Token para usar en cada llamada a la API** |
| `data[].tokenSecExpiration` | Integer | Segundos hasta expiración del token |
| `data[].idUser` | String | Código del usuario en el Identity Provider |
| `data[].email` | String | Email logueado |
| `data[].updatedAt` | String | Última actualización de la identidad |
| `data[].apiCounter` | Object | Contador de usos diarios y límite |

```json
{
  "code": "00",
  "description": "Register user: yourusername with token: 3bd5855a-...",
  "datetime": "2019-10-01T16:35:39.521302",
  "data": [{
    "userName": "yourusername",
    "accessToken": "3bd5855a-ed3d-41d5-8b4b-182726f86031",
    "tokenSecExpiration": 984,
    "email": "yourmail@mail.com",
    "idUser": "2f104b08-f8bf-4199-a4bc-c6ecc42ad6ba",
    "apiCounter": { "current": 5, "dailyUse": 150000, "owner": 0 }
  }]
}
```

---

### GET `/v1/mobilitylabs/user/logout/`

Destruye la sesión del usuario.

**Header:** `accessToken` (String)

**Respuesta:** `code: "03"` = OK

```json
{ "code": "03", "description": "Token ... removed from control-cache", "data": [] }
```

---

### GET `/v1/mobilitylabs/user/passwreset/`

Envía un email para restablecer la contraseña.

**Header:** `accessToken` (String)

**Respuesta:** `code: "05"` = OK

---

### GET `/v1/mobilitylabs/user/whoami/`

Recupera el contexto de login si el usuario está logueado.

**Header:** `accessToken` (String)

**Respuesta:** `code: "02"` = Token válido, `code: "80"` = Token no encontrado

---

## Block 2 — Data Model

### GET `/v1/mobilitylabs/discover/categories/`

Devuelve categorías y subcategorías del portal de desarrolladores.

**Header:** `accessToken` (String)

**Respuesta — campos de `data[]`:**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `DS_CATEGORY` | String | Nombre de la categoría |
| `DS_SUBCATEGORY` | String | Nombre de la subcategoría |
| `CD_CATEGORY` | Integer | Código de categoría |
| `CD_SUBCATEGORY` | Integer | Código de subcategoría |
| `DS_URI` | String | URL base del recurso |
| `DS_DESCRIPTION_CAT` | String | Descripción de la categoría |
| `DS_DESCRIPTION_SUBCAT` | String | Descripción de la subcategoría |
| `DS_PHOTO_CAT` | String | URL imagen de categoría |
| `DS_PHOTO_SUBCAT` | String | URL imagen de subcategoría |
| `FC_CREATION` | Datetime | Fecha de creación |
| `CD_DATA_TYPE` | Integer | Código de tipo de dato |

---

### GET `/v1/mobilitylabs/discover/collection/<CD_COLLECTION>/`

Recupera detalles de una colección por su ID.

**Header:** `accessToken` (String)  
**Parámetro:** `CD_COLLECTION` (String) — ID de colección

**Respuesta — campos de `data[]`:**
- `valuations` (Array): evaluaciones de desarrolladores
- `links` (Array): links relacionados con la colección
- `comments` (String): comentarios de desarrolladores
- `general` (String): atributos de la colección

---

### GET `/v1/mobilitylabs/discover/collections/<CD_SUBCATEGORY>/`

Recupera todas las colecciones de una subcategoría.

**Header:** `accessToken` (String)  
**Parámetro:** `CD_SUBCATEGORY` (String) — Código de subcategoría

**Respuesta — campos de `data[]`:**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `DS_COLLECTION` | String | Descripción de la colección |
| `DS_DESCRIPTION` | String | Nombre de la colección |
| `CD_COLLECTION` | String | ID de la colección |
| `NM_VERSION` | String | Versión del recurso |
| `CD_DATA_TYPE` | String | Tipo de dato |
| `FC_CREATION` | String | Fecha de creación |
| `FC_UPDATE` | String | Última actualización |
| `IT_GEOGRPHIC_INFO` | String | Si contiene proyección geográfica |
| `CD_SHARING_TYPE` | String | Tipo de compartición |
| `CD_REST_ACTION` | String | Tipo de acción REST |

---

### GET `/v1/mobilitylabs/discover/datatypes/`

Devuelve los tipos de datos del portal.

**Tipos disponibles:**
- `1` — WEBSERVICE: Usa el sistema de API
- `2` — STATIC: Contiene datasets (archivos)
- `3` — REACTIVE: Observable mediante DDP ReactiveBox

```json
{ "code": "00", "data": [
  { "CD_DATA_TYPE": 1, "DS_DATA_TYPE": "WEBSERVICE" },
  { "CD_DATA_TYPE": 2, "DS_DATA_TYPE": "STATIC" },
  { "CD_DATA_TYPE": 3, "DS_DATA_TYPE": "REACTIVE" }
]}
```

---

### GET `/v1/mobilitylabs/discover/fieldformats/`

Devuelve los formatos de campos usados en el portal.

**Header:** `accessToken` (String)

---

### GET `/v1/mobilitylabs/discover/linktypes/`

Devuelve los tipos de enlaces del portal.

**Tipos disponibles:**
- `1` — General
- `2` — Documentation
- `3` — User Guide
- `4` — Http Call (endpoint para webservices)
- `5` — File (dataset descargable)
- `6` — Reactive file (documento por query)

---

### GET `/v1/mobilitylabs/discover/restactions/`

Devuelve los tipos de acciones REST del portal.

```json
{ "data": [
  { "CD_REST_ACTION": 1, "DS_REST_ACTION": "GET" },
  { "CD_REST_ACTION": 2, "DS_REST_ACTION": "POST" },
  { "CD_REST_ACTION": 3, "DS_REST_ACTION": "PUT" },
  { "CD_REST_ACTION": 4, "DS_REST_ACTION": "DELETE" }
]}
```

---

### GET `/v1/mobilitylabs/discover/resttypes/`

Devuelve los tipos REST (REQUEST / RESPONSE).

---

### GET `/v1/mobilitylabs/discover/sharingtypes/`

Devuelve los modos de compartición de datos.

```json
{ "data": [
  { "CD_SHARING_TYPE": 1, "DS_SHARING_TYPE": "PUBLIC" },
  { "CD_SHARING_TYPE": 2, "DS_SHARING_TYPE": "PRIVATE" },
  { "CD_SHARING_TYPE": 3, "DS_SHARING_TYPE": "SHARED" }
]}
```

---

## Block 3 — Transport BUSEMTMAD

### GET `/v1/transport/busemtmad/stops/<stopId>/detail/`

Devuelve los detalles de una parada de EMT Madrid.

**Header:** `accessToken` (String)  
**Parámetro:** `stopId` (String) — Número de parada

**Respuesta — campos de `data[].stops[]`:**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `stop` | String | ID de la parada |
| `name` | String | Nombre de la parada |
| `pmv` | String | Número del panel electrónico (si existe) |
| `geometry` | Object | Posición geográfica (GeoJSON Point) |
| `postalAddress` | String | Dirección postal |
| `dataLine[]` | Array | Líneas que usan esta parada |
| `dataLine[].label` | String | Código público de la línea |
| `dataLine[].line` | String | Código interno de la línea |
| `dataLine[].direction` | String | `B` = de A a B, `A` = de B a A |
| `dataLine[].headerA` | String | Cabecera A de la línea |
| `dataLine[].headerB` | String | Cabecera B de la línea |
| `dataLine[].startTime` | String | Hora de inicio del servicio |
| `dataLine[].stopTime` | String | Hora de fin del servicio |
| `dataLine[].minFreq` | String | Frecuencia mínima (minutos) |
| `dataLine[].maxFreq` | String | Frecuencia máxima (minutos) |
| `dataLine[].dayType` | String | `LA`=Laborable, `SA`=Sábado, `FE`=Festivo |

```json
{
  "code": "00",
  "data": [{
    "stops": [{
      "pmv": "61242",
      "name": "Cibeles",
      "geometry": { "type": "Point", "coordinates": [-3.692, 40.420] },
      "stop": "72",
      "dataLine": [{
        "headerB": "CHAMARTIN", "direction": "B", "headerA": "SOL/SEVILLA",
        "label": "5", "stopTime": "22:58", "minFreq": "9",
        "startTime": "06:30", "maxFreq": "20", "dayType": "LA", "line": "005"
      }],
      "postalAddress": "Pº de Recoletos, 2 (Pza. de Cibeles)"
    }]
  }]
}
```

---

### GET `/v2/transport/busemtmad/stops/arroundstop/<stopId>/<radius>/`

Devuelve paradas cercanas a una parada específica en un radio dado.

**Header:** `accessToken` (String)  
**Parámetros:**
- `stopId` (String) — Número de parada de referencia
- `radius` (String) — Radio en metros

**Respuesta — campos de `data[]` (ordenado por distancia):**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `stopId` | String | Número de parada |
| `stopName` | String | Nombre de la parada |
| `metersToPoint` | Integer | Distancia en metros al punto de referencia |
| `geometry` | Object | Coordenadas GeoJSON |
| `lines[]` | Array | Líneas de la parada |
| `lines[].label` | String | Código público de línea |
| `lines[].line` | String | Código interno de línea |
| `lines[].nameA` | String | Cabecera A |
| `lines[].nameB` | String | Cabecera B |
| `lines[].to` | String | Dirección (`A` o `B`) |
| `lines[].metersFromHeader` | Integer | Metros desde la cabecera de la línea |

---

### GET `/v2/transport/busemtmad/stops/arroundxy/<longitude>/<latitude>/<radius>/`

Devuelve paradas cercanas a un punto geográfico.

**Header:** `accessToken` (String)  
**Parámetros:**
- `longitude` (String) — Longitud del punto
- `latitude` (String) — Latitud del punto
- `radius` (String) — Radio en metros

**Respuesta:** Igual que `arroundstop`.

---

### POST `/v2/transport/busemtmad/stops/<stopId>/arrives/<lineArrive>/`

⭐ **Endpoint principal** — Devuelve la estimación en tiempo real de llegada de buses a una parada.

**Header:** `accessToken` (String)  
**Parámetros:**
- `stopId` (String) — Número de parada
- `lineArrive` (String) — Opcional: línea específica (si no se indica, devuelve todas)

**Body (JSON):**
```json
{
  "cultureInfo": "ES",
  "Text_StopRequired_YN": "Y",
  "Text_EstimationsRequired_YN": "Y",
  "Text_IncidencesRequired_YN": "N",
  "DateTime_Referenced_Incidencies_YYYYMMDD": "20231001"
}
```

| Campo del Body | Descripción |
|----------------|-------------|
| `cultureInfo` | `EN` = inglés, `ES` = español |
| `Text_StopRequired_YN` | `Y` para obtener nombre de parada |
| `Text_EstimationsRequired_YN` | `Y` para obtener estimaciones de llegada |
| `Text_IncidencesRequired_YN` | `Y` para obtener incidencias de líneas |
| `DateTime_Referenced_Incidencies_YYYYMMDD` | Fecha referencia de incidencias (`YYYYMMDD`) |

**Respuesta — campos de `data[].Arrive[]`:**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `line` | String | Número de línea |
| `stop` | String | Número de parada |
| `bus` | String | Número de vehículo |
| `destination` | String | Destino del itinerario |
| `estimateArrive` | Integer | **Segundos hasta llegar** (999999 = más de 45 min en líneas convencionales / más de 90 min en nocturnas) |
| `DistanceBus` | Integer | Metros del bus a la parada |
| `geometry` | Object | Posición actual del bus (GeoJSON Point) |
| `isHead` | String | No aplica en esta versión |
| `positionTypeBus` | String | No aplica en esta versión |

**Respuesta — campos de `data[].StopInfo[]`:**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `stopId` | String | ID de la parada |
| `stopName` | String | Nombre de la parada |
| `geometry` | Object | Posición de la parada |
| `Direction` | String | Dirección/calle de la parada |
| `lines[]` | Array | Líneas que pasan por la parada |

```json
{
  "code": "00",
  "data": [{
    "Arrive": [{
      "line": "45", "stop": "62", "bus": 6746,
      "destination": "REINA VICTORIA",
      "estimateArrive": 71,
      "DistanceBus": 363,
      "geometry": { "type": "Point", "coordinates": [-3.692, 40.413] }
    }],
    "StopInfo": [{
      "stopId": "62",
      "stopName": "Castellana-Ministerio Interior",
      "Direction": "Pº de la Castellana, 20",
      "lines": [{ "label": "5", "line": "005", "nameA": "SOL SEVILLA", "nameB": "CHAMARTIN" }]
    }],
    "Incident": { "ListaIncident": { "data": [] } }
  }]
}
```

---

### GET `/v1/transport/busemtmad/calendar/<startdate>/<enddate>/`

Devuelve el calendario de transporte de EMT.

**Header:** `accessToken` (String)  
**Parámetros:**
- `startdate` (String) — Fecha inicio en formato `YYYYMMDD`
- `enddate` (String) — Fecha fin en formato `YYYYMMDD`

**Respuesta — campos de `data[]`:**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `date` | String | Fecha del calendario |
| `strike` | String | Si hay huelga ese día (`Y`/`N`) |
| `dayType` | String | `LA`=Laborable, `SA`=Sábado, `FE`=Festivo |

```json
{
  "code": "00",
  "data": [
    { "date": "01/01/2018 0:00:00", "strike": "N", "dayType": "FE" },
    { "date": "02/01/2018 0:00:00", "strike": "N", "dayType": "LA" }
  ]
}
```

---

### GET `/v1/transport/busemtmad/lines/incidents/<lineid>/`

Devuelve incidencias o alteraciones en el servicio de líneas en formato JSON.

**Header:** `accessToken` (String)  
**Parámetro:** `lineid` (String) — Línea (usar `"all"` para todas las líneas)

**Respuesta — campos relevantes de `data[].item[]`:**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `title` | String | Título de la incidencia |
| `description` | String | Descripción detallada |
| `category` | Array | Líneas afectadas |
| `rssAfectaDesde` | String | Inicio estimado de la incidencia |
| `rssAfectaHasta` | String | Fin estimado de la incidencia |
| `pubDate` | String | Fecha de publicación |
| `GoogleTransitCause` | String | Causa en formato Google Transit |
| `GoogleTransitEffect` | String | Efecto en formato Google Transit |
| `guid` | String | ID único de la incidencia |
| `enclosure` | Object | Enlace a documento con más detalle |

---

### GET `/v1/transport/busemtmad/lines/<lineId>/info/<dateref>/`

Devuelve el detalle de una línea EMT (horarios, frecuencias por tipo de día).

**Header:** `accessToken` (String)  
**Parámetros:**
- `lineId` (String) — Línea o etiqueta pública
- `dateref` (String) — Fecha de referencia en formato `YYYYMMDD`

**Respuesta — campos de `data[]`:**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `dateRef` | String | Fecha de referencia |
| `label` | String | Código público de la línea |
| `nameA` | String | Nombre cabecera A |
| `nameB` | String | Nombre cabecera B |
| `line` | String | Código interno de línea |
| `timeTable[]` | Array | Horarios por tipo de día |
| `timeTable[].idDayType` | String | Tipo de día (`LABORABLE`, `SABADO`, `FESTIVO`) |
| `timeTable[].Direction1/2.StartTime` | String | Hora inicio del servicio |
| `timeTable[].Direction1/2.StopTime` | String | Hora fin del servicio |
| `timeTable[].Direction1/2.MinimunFrequency` | String | Frecuencia mínima (min) |
| `timeTable[].Direction1/2.MaximumFrequency` | String | Frecuencia máxima (min) |
| `timeTable[].Direction1/2.FrequencyText` | String | Descripción de la frecuencia |

---

### GET `/v2/transport/busemtmad/lines/info/<dateref>/`

Devuelve la lista de líneas activas en una fecha de referencia.

**Header:** `accessToken` (String)  
**Parámetro:** `dateref` (String) — Fecha en formato `YYYYMMDD`

**Respuesta — campos de `data[]`:**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `line` | String | Código interno de línea |
| `label` | String | Código público de línea |
| `nameA` | String | Nombre cabecera A |
| `nameB` | String | Nombre cabecera B |
| `group` | String | Grupo al que pertenece |
| `startDate` | String | Inicio de configuración vigente |
| `endDate` | String | Fin de configuración vigente |

```json
{ "code": "00", "data": [
  { "line": "001", "label": "1", "nameA": "CRISTO REY", "nameB": "PROSPERIDAD", "group": "110" }
]}
```

---

### POST `/v1/transport/busemtmad/stops/list/`

Devuelve la lista de paradas activas. Si se envía un array de IDs, filtra por esas paradas.

**Header:** `accessToken` (String)  
**Body (opcional):** `{ "liststops": [62, 1234] }`

**Respuesta — campos de `data[]`:**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `node` | String | Número de parada |
| `name` | String | Nombre de la parada |
| `geometry` | Object | Posición geográfica (GeoJSON) |
| `wifi` | String | `1` si la parada tiene WiFi público |
| `lines` | Array | Líneas/dirección que usan la parada (formato `"lineId/direction/..."`) |

```json
{ "code": "00", "data": [{
  "node": "72",
  "name": "Cibeles",
  "geometry": { "type": "Point", "coordinates": [-3.692, 40.420] },
  "wifi": "0",
  "lines": ["5/1/1", "14/1/1", "27/1/1"]
}]}
```

---

### GET `/v1/transport/busemtmad/lines/<lineId>/stops/<direction>/`

Devuelve las paradas de una línea en una dirección y las líneas que coinciden en cada parada.

**Header:** `accessToken` (String)  
**Parámetros:**
- `lineId` (String) — Código de línea EMT
- `direction` (String) — `1` = de A a B, `2` = de B a A

**Respuesta — campos de `data[]`:**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `line` | String | Número de línea |
| `timeTable[]` | Array | Horarios de la línea |
| `stops[]` | Array | Paradas de la línea |
| `stops[].stop` | String | Número de parada |
| `stops[].name` | String | Nombre de la parada |
| `stops[].pmv` | String | Panel electrónico (si existe) |
| `stops[].geometry` | Object | Posición GeoJSON |
| `stops[].postalAddress` | String | Dirección postal |
| `stops[].dataLine` | Array | Otras líneas que pasan por esa parada |

---

### GET `/v1/transport/busemtmad/lines/groups/`

Devuelve los grupos de operación de las líneas EMT.

**Header:** `accessToken` (String)

**Respuesta — campos de `data[]`:**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `group` | String | Código de grupo (ej: `100`) |
| `subGroup` | String | Código de subgrupo (ej: `110`) |
| `description` | String | Descripción del grupo |

**Grupos conocidos:**
- `110` — Líneas convencionales
- `120` — Líneas centros de trabajo
- `155` — Líneas minibuses

---

### GET `/v1/transport/busemtmad/lines/<labelId>/route/`

Devuelve el itinerario completo de una línea en formato GeoJSON.

**Header:** `accessToken` (String)  
**Parámetro:** `labelId` (String) — Código público de línea (o número de línea)

**Respuesta — campos de `data`:**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `label` | String | Código público de la línea |
| `line` | String | Código interno |
| `nameSectionA` | String | Nombre dirección A |
| `nameSectionB` | String | Nombre dirección B |
| `itinerary.toA` | Object | GeoJSON FeatureCollection con el recorrido hacia A |
| `itinerary.toB` | Object | GeoJSON FeatureCollection con el recorrido hacia B |
| `stops.toA` | Object | GeoJSON FeatureCollection con paradas en dirección A |
| `stops.toB` | Object | GeoJSON FeatureCollection con paradas en dirección B |

---

### GET `/v1/transport/busemtmad/stops/arroundstreet/<namePlace>/<number>/radius/`

Devuelve paradas y líneas cercanas a una calle o lugar.

**Header:** `accessToken` (String)  
**Parámetros:**
- `namePlace` (String) — Nombre parcial de lugar o calle
- `number` (Int) — Número de calle (`0` si no aplica)
- `radius` (Int) — Radio en metros

**Respuesta:** Array con atributos del lugar, paradas cercanas y líneas de cada parada.

---

### GET `/v1/transport/busemtmad/lines/<lineId>/timetable/`

Devuelve las horas de inicio y fin del servicio de una línea.

**Header:** `accessToken` (String)  
**Parámetro:** `lineId` (String) — Código de línea

**Respuesta — campos de `data[]`:**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `dateIni` | String | Inicio de la planificación vigente |
| `dateEnd` | String | Fin de la planificación vigente |
| `dayType` | String | Tipo de día |
| `line` | String | Código interno de línea |
| `firstTimeServiceA` | String | Primera hora del servicio de A a B |
| `firstTimeServiceB` | String | Primera hora del servicio de B a A |
| `endTimeServiceA` | String | Última hora del servicio de A a B |
| `endTimeServiceB` | String | Última hora del servicio de B a A |

---

### GET `/v1/transport/busemtmad/lines/<lineId>/trips/<dateRef>/`

Devuelve la lista de expediciones (trips) de una línea en una fecha.

**Header:** `accessToken` (String)  
**Parámetros:**
- `lineId` (String) — Código de línea
- `dateRef` (String) — Fecha en formato `YYYYMMDD`

**Respuesta — campos de `data[]`:**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `logicBus` | String | ID único interno en servicio simultáneo |
| `direction` | String | De A a B o de B a A |
| `tripNum` | String | Número de expedición |
| `startTimeTrip` | String | Hora teórica de inicio |
| `endTimeTrip` | String | Hora teórica de fin |
| `date` | String | Fecha de referencia |
| `dayType` | String | Tipo de día |
| `line` | String | Código de línea |

---

### POST `/v1/transport/busemtmad/travelplan/`

Calcula la ruta entre dos puntos geográficos.

**Header:** `accessToken` (String)

**Body (JSON):**
```json
{
  "routeType": "P",
  "coordinateXFrom": -3.701077,
  "coordinateYFrom": 40.4469,
  "coordinateXTo": -3.674902,
  "coordinateYTo": 40.400149,
  "originName": "Calle Maudes 6",
  "destinationName": "Calle Cerro de la Plata 4",
  "polygon": null,
  "day": 2, "month": 4, "year": 2019, "hour": 18, "minute": 18,
  "culture": "es",
  "itinerary": true,
  "allowBus": true,
  "allowBike": false
}
```

**Parámetros del Body:**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `routeType` | String | `P`=Transporte público, `C`=Coche/Parking, `W`=Caminar, `M`=Mixto |
| `coordinateXFrom` | String | Longitud origen |
| `coordinateYFrom` | String | Latitud origen |
| `coordinateXTo` | String | Longitud destino |
| `coordinateYTo` | String | Latitud destino |
| `originName` | String | Nombre simbólico del origen |
| `destinationName` | String | Nombre simbólico del destino |
| `polygon` | String | Opcional: área de exclusión en GeoJSON |
| `day/month/year/hour/minute` | Numeric | Fecha y hora opcionales de la ruta |
| `culture` | String | `en` = inglés, `es` = español |
| `itinerary` | Boolean | Si `true`, devuelve multilinestrings para mapa |
| `allowBus` | Boolean | Permitir rutas en bus (cuando `routeType` es M o P) |
| `allowBike` | Boolean | Permitir rutas en bici pública (cuando `routeType` es M o P) |
| `preferPublic` | Boolean | Reservado para uso futuro |
| `isResidentOrInvited` | Boolean | Reservado para uso futuro |
| `isEnvFriendly` | Boolean | Reservado para uso futuro |
| `usingTaxi` | Boolean | Reservado para uso futuro |
| `usingRentedCar` | Boolean | Reservado para uso futuro |

**Respuesta — campos de `data[]`:**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `distance` | Numeric | Distancia total en km |
| `departureTime` | String | Hora de salida |
| `arrivalTime` | String | Estimación de llegada |
| `duration` | String | Duración estimada en minutos |
| `description` | String | Descripción textual de la ruta |
| `sections[]` | Array | Tramos del itinerario |
| `sections[].type` | String | Modo de viaje (`Walk`, `Bus`, etc.) |
| `sections[].order` | Numeric | Orden del tramo |
| `sections[].source` | Object | Instrucciones del punto de origen del tramo |
| `sections[].destination` | Object | Instrucciones del punto de destino del tramo |
| `sections[].route` | Array | Instrucciones en GeoJSON (paradas, bases de BiciMAD) |
| `sections[].itinerary` | Array | Representación GeoJSON del tramo para mapa |
| `sections[].duration` | Numeric | Duración del tramo |
| `sections[].idLine` | String | ID de línea (solo para tramos en bus) |

---

## Resumen de endpoints clave para NotifyMeBus

| Endpoint | Método | Uso |
|----------|--------|-----|
| `/v?/mobilitylabs/user/login/` | GET | Obtener `accessToken` |
| `/v2/transport/busemtmad/stops/<stopId>/arrives/<lineArrive>/` | POST | **Tiempos de llegada en tiempo real** |
| `/v1/transport/busemtmad/stops/<stopId>/detail/` | GET | Detalle de parada + líneas |
| `/v1/transport/busemtmad/stops/list/` | POST | Lista de paradas (con filtro opcional) |
| `/v2/transport/busemtmad/stops/arroundxy/<lon>/<lat>/<radius>/` | GET | Paradas cercanas a coordenadas GPS |
| `/v2/transport/busemtmad/stops/arroundstop/<stopId>/<radius>/` | GET | Paradas cercanas a otra parada |
| `/v2/transport/busemtmad/lines/info/<dateref>/` | GET | Lista de líneas activas |
| `/v1/transport/busemtmad/lines/<lineId>/stops/<direction>/` | GET | Paradas de una línea |
| `/v1/transport/busemtmad/calendar/<startdate>/<enddate>/` | GET | Tipo de día (LA/SA/FE) |

---

## Notas importantes

- El `accessToken` se obtiene con `/login/` y expira, pero se **renueva automáticamente** en cada llamada.
- `estimateArrive` en el endpoint de llegadas devuelve **segundos**. El valor `999999` significa más de 45 minutos (líneas convencionales) o más de 90 minutos (nocturnas).
- Los tipos de día son: `LA` = Laborable, `SA` = Sábado, `FE` = Festivo.
- Las coordenadas en la API siguen el estándar GeoJSON: `[longitud, latitud]`.
