import uvicorn
from api import router
from core.config import settings
from fastapi import FastAPI

app = FastAPI(title="PolyPoker Backend", version="0.1.0")


@app.on_event("startup")
async def startup():
    pass


@app.on_event("shutdown")
async def shutdown():
    pass


app.include_router(router)

if __name__ == "__main__":
    uvicorn.run(
        "main:app",
        host=settings.DEFAULT_HOST,
        port=settings.DEFAULT_PORT,
        reload=settings.DEBUG,
    )
