from fastapi import FastAPI
from routers import emt

app = FastAPI(title="EMT Madrid API")

app.include_router(emt.router)
