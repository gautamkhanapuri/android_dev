from passlib.apps import custom_app_context as pwd_context

from base64 import b64encode
from sqlalchemy import Column, Integer, String
from sqlalchemy import Sequence
from api_init import db, LOGGER


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
        """Verify the password after hasing the given password."""
        if self.pwd:
            return pwd_context.verify(password, self.pwd)
        return None

