# NotifyMeBus — Implementaciones

Registro de funcionalidades observadas en la app oficial EMT Madrid y estado de implementación en este proyecto.

**Niveles de implementación:**
- ⬜ Sin empezar
- 🟡 L1 — Endpoint backend funcional (JSON)
- 🟠 L2 — Integrado en Android (llamada Retrofit)
- ✅ L3 — UI completa en Android

---

## Paradas

| Funcionalidad | Endpoint backend | Estado |
|---|---|---|
| Buscar parada por nombre | `GET /buscarParada/{nombre}` | 🟡 L1 |
| Ver tiempos de llegada de una parada | `GET /infoParada/{stop_id}` | 🟡 L1 |
| Ver líneas que pasan por una parada | incluido en `/infoParada` | 🟡 L1 |
| Ver nombre, dirección y coordenadas de parada | incluido en `/buscarParada` | 🟡 L1 |
| Paradas favoritas (guardadas localmente) | Room DB — sin backend | ⬜ |
| Historial de búsquedas recientes | Room DB — sin backend | ⬜ |

---

## Líneas

| Funcionalidad | Endpoint backend | Estado |
|---|---|---|
| Ver paradas de una línea | `GET /linea/{line_id}/paradas` | ⬜ |
| Listar todas las líneas con origen-destino | `GET /lineas` | ⬜ |
| Recorrido de una línea (polilínea en mapa) | pendiente investigar endpoint EMT | ⬜ |

---

## Detalle de parada (pantalla de la app)

| Elemento UI | Fuente de datos | Estado |
|---|---|---|
| Número y nombre de parada | `/infoParada` o `/buscarParada` | 🟡 L1 |
| Dirección postal | endpoint detail EMT (`/stops/{id}/detail/`) | ⬜ |
| Badges de líneas que pasan (color día/noche) | `/infoParada` | 🟡 L1 |
| Próximas llegadas por línea (minutos) | `/infoParada` — `estimateArrive` en segundos | 🟡 L1 |
| Destino de cada línea | `/infoParada` — campo `destination` | 🟡 L1 |
| Mapa con ubicación de la parada | coordenadas de `/buscarParada` | 🟡 L1 (datos) |
| Botón favorito | Room DB local | ⬜ |
| Alias personalizado para la parada | Room DB local | ⬜ |

---

## Mapa

| Funcionalidad | Estado |
|---|---|
| Mapa con todas las paradas como pines | ⬜ (Fase 3) |
| Paradas cercanas a la ubicación del usuario | ⬜ (Fase 3) |
| Recorrido de línea sobre el mapa (línea azul) | ⬜ (Fase 3) |

---

## Favoritos

| Funcionalidad | Estado |
|---|---|
| Guardar parada favorita con alias | ⬜ |
| Reordenar favoritos | ⬜ |
| Ver líneas de cada parada favorita | ⬜ |

---

## Notificaciones (diferenciador NotifyMeBus)

| Funcionalidad | Estado |
|---|---|
| Notificación simple: avisar cuando bus a N minutos | ⬜ |
| Notificación programada: a HH:MM, avisar cuando queden N min | ⬜ |
| Próximos N buses (mostrar los 2 siguientes) | ⬜ |
| WorkManager polling cada 30s | ⬜ |
