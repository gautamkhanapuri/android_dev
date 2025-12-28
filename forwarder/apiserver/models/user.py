from typing import Tuple, Dict

from passlib.apps import custom_app_context as pwd_context

from base64 import b64encode
from sqlalchemy import Column, Integer, String
from sqlalchemy import Sequence
from api_init import db, LOGGER
from utils import STATUS, OK, NOK
from models.forward import ForwardMessage
from models.telegramuser import TelegramUser


# Authorization token will be stored as base 64 encoded string
# and then decode it to acsii as python 3 stores it as a byte string
def basicAuth(username, password):
    token = b64encode(f"{username}:{password}".encode('utf-8')).decode("ascii")
    return token

def hash_password(password):
    """Hash the password, irreversible"""
    return pwd_context.encrypt(password)

def createUser(name, username, pwd):
  "Create users for initial startup of the application."
  LOGGER.info("Creating user: %s", name)
  user = db.session.query(User).filter(User.username == username).first()
  if not user:
    # token = basicAuth(username, pwd)
    user = User(name=name, username=username, pwd=hash_password(pwd))
    db.session.add(user)
    db.session.commit()
    return user
  return None

def checkUsernameAlreadyExists(username: str) -> bool:
  """
  Returns True if the username already exists in the User table. False if such a username doesnot exist in the table.
  """
  LOGGER.info(f"Checking if the username '{username}' already exists in the Database.")
  existing_user = db.session.query(User).filter(User.username == username).first()
  if existing_user:
    return True
  return False

def verifyUsernamesPassword(username: str, password:str) -> bool:
  """
  Checks if password is correct for the given username. Returns True is password is correct else False.
  """
  LOGGER.info(f"Verifying password for '{username}'")
  user = db.session.query(User).filter(User.username == username).first()
  isCorrect = user.verify_password(password)
  return isCorrect

def deleteUser(username: str) -> tuple[dict[str, str], int]:
  try:
    user = db.session.query(User).filter(User.username == username).first()
    user_id = user.id

    # Delete user's telegram associations
    # TelegramUser.query.filter_by(username=username).delete()

    # Delete the user from the User table
    db.session.delete(user)
    db.session.commit()
    LOGGER.info(f"Account deleted successfully from User table: {username}")
    return {STATUS: OK, "message": "Account deleted successfully"}, 200
  except Exception as e:
    db.session.rollback()
    LOGGER.error(f"Account deletion error: {e}")
    return {STATUS: NOK, "message": "Account deletion failed"}, 500


class User(db.Model):
    """User ORM model for user access."""
    __tablename__ = "users"
    id = Column(Integer, Sequence("user_id_seq"), primary_key=True)
    name = Column(String(50))
    username = Column(String(50))
    pwd = Column(String(255))

    def __repr__(self):
        return "<User(name='%s', username='%s', id='%d')>" % (
            self.name,
            self.fullname,
            self.id,
        )

    def verify_password(self, password):
        """Verify the password after hashing the given password."""
        if self.pwd:
            return pwd_context.verify(password, self.pwd)
        return None

