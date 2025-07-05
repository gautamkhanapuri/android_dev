from sqlalchemy import create_engine, Column, Integer, String

from app_init import Base, session

class User(Base):
  __tablename__ = 'users'
  id = Column(Integer, primary_key=True)
  name = Column(String)
  email = Column(String)
  age = Column(Integer)


def get_users():
  users = session.query(User).all()
  return users
