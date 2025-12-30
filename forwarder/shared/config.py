import os
from typing import Literal
from pydantic_settings import BaseSettings
from pydantic import field_validator


class Settings(BaseSettings):
  """
  Application Settings loaded from environment variables.
  All values have defaults for development, must be set for production.
  """

  # ********** APPLICATION **********
  app_name: str = "SMS Forwarder API"
  app_version: str = "2.0"
  environment: Literal["dev", "prod"] = "prod"
  log_level: str = "INFO"
  app_port: int = 8000

  # ********** DATABASE **********
  database_type: Literal["sqlite", "postgres", "dynamodb", "mysql"] = "sqlite"
  sqlite_path: str = "data/db/apiserver.db"  # TODO: Why is there only one database? Shouldn't there be a different one for botserver?

  # For future PostgreSQL support
  postgres_host: str = ""
  postgres_port: int = 5432
  postgres_user: str = ""
  postgres_password: str = ""
  postgres_db: str = ""

  # ==================== SECURITY ====================
  session_secret: str
  session_csrf: str

  @field_validator("session_secret", "session_csrf")
  def validate_secrets(self, v, field):
    """
    Ensure secrets are not default values
    """
    if not v or v.startswith("CHANGE_THIS"):
      raise ValueError(f"{field.name} must be set in environment variables")
    if len(v) < 32:
      raise ValueError(f"{field.name} should be at least 32 characters long")
    return v

  # ==================== TELEGRAM ====================
  bot_token: str

  @field_validator('bot_token')
  def validate_bot_token(self, v):
    """
    Validate Telegram bot token format
    """
    if not v or v.startswith("YOUR_"):
      raise ValueError("BOTTOKEN must be set in environment variables")
    if ":" not in v:
      raise ValueError("Invalid BOTTOKEN format (should be: 123456: ABCD...)")
    return v

  # ==================== EMAIL ====================
  resend_api_key: str
  from_email: str = "noreply@smsforwards.gaks.solutions"

  @field_validator('resend_api_key')
  def validate_resend_key(self, v):
    """Validate Resend API key"""
    if not v or v.startswith("YOUR_"):
      raise ValueError("RESEND_API_KEY must be set in environment variables")
    if not v.startswith("re_"):
      raise ValueError("Invalid RESEND_API_KEY format (should start with 're_')")
    return v

  # ==================== API Configuration ====================
  api_base_url: str = "http://localhost:8000"

  # ==================== Rate Limiting ====================
  rate_limit_enabled: bool = True
  rate_limit_register: str = "6 per hour"
  rate_limit_reset_token: str = "10 per hour"
  rate_limit_delete: str = "3 per hour"
  rate_limit_forward: str = "100 per hour"

  # ==================== Logging ====================
  log_file: str = "data/log/apiserver.log"

  class Config:
    env_file: str = ".env"
    case_sesitive: bool = False

    fields = {
      'app_port': {'env': 'APPPORT'},
      'log_level': {'env': 'LOGLEVEL'},
      'bot_token': {'env': 'BOTTOKEN'},
      'sqlite_path': {'env': 'SQLITE_PATH'},
      'log_file': {'env': 'LOG_FILE'}

    }


_settings: Settings = None

def get_settings() -> Settings:
  """
  Get settings singleton instance
  Loads from environment variables on first call
  """
  global _settings
  if _settings is None:
    try:
      _settings = Settings()
    except Exception as e:
      print(f"ERROR: Failed to load settings: {e}")
      print("Please check your .env file and ensure all required variables are set")
      raise
    return _settings