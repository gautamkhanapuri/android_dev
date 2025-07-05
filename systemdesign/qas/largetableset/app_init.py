from sqlalchemy import create_engine, Column, Integer, String
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base

session = None
Base = None

def init_app():
  global session, Base
  # engine = create_engine('postgresql://ajeybk:pwd%26321%23@localhost:5432/testdb')
  engine = create_engine('postgresql://user1:pwd%26321%23@localhost:5432/testdb')
  Session = sessionmaker(bind=engine)
  session = Session()
  Base = declarative_base()
