from sqlalchemy import create_engine, Column, Integer, String, Date
from app_init import Base, session

class Customer(Base):
  __tablename__ = 'customers'
  id = Column(Integer, primary_key=True)
  customerid = Column(String)
  firstname = Column(String)
  lastname = Column(String)
  company = Column(String)
  city = Column(String)
  country = Column(String)
  phone1 = Column(String)
  phone2 = Column(String)
  email = Column(String)
  subscription = Column(Date)
  website = Column(String)

def get_customers():
  customers = session.query(Customer).all()
  return customers

def qry_customers_offset(offset=0, pagesize=10):
  # validate offset for integer and greater than zero
  # validate pagesize for positive integer and in multiples of 10
  customers = session.query(Customer).limit(pagesize).offset(offset).all()
  return customers


def qry_customers_key(key=0, pagesize=10):
  # validate offset for integer and greater than zero
  # validate pagesize for positive integer and in multiples of 10
  customers = session.query(Customer).filter(Customer.id > key).limit(pagesize).all()
  return customers
