from fastapi import APIRouter
from services.emt_service import get_stop_arrivals, search_stops_by_name, get_line_stops, debug_stop_fields

router = APIRouter()


@router.get("/infoParada/{stop_id}")
def get_stop_info(stop_id: int):
    return get_stop_arrivals(stop_id)


@router.get("/buscarParada/{nombre}")
def search_stop(nombre: str):
    return search_stops_by_name(nombre)


@router.get("/linea/{line_id}/paradas")
def get_stops_for_line(line_id: str):
    return get_line_stops(line_id)


@router.get("/debug/parada")
def debug_stop():
    return debug_stop_fields()
