from  pydantic import BaseModel
import uvicorn


#recibir parada
class parada(BaseModel):
    idParada : int


#recibir linea
class linea(BaseModel):
    linea : int
