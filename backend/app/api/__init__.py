from fastapi import APIRouter

router = APIRouter(tags=["PolyPoker - API"])


@router.get("/")
async def health_check():
    return {"Hello": "World"}
