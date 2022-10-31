import os

from pydantic import BaseConfig, Field


class Settings(BaseConfig):
    DEFAULT_HOST: str = "localhost"
    DEFAULT_PORT: int = 8001
    SERVICE_NAME: str = "Event manager"
    DB_HOST: str = os.getenv("DB_HOST", "127.0.0.1")
    DB_USER: str = "user"
    DB_PASSWORD: str = "password"
    DB_NAME: str = "postgres"
    DEBUG: bool = True

    class Config:
        case_sensitive = True


settings = Settings()
