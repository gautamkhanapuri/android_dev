CREATE TABLE customers (
       id SERIAL PRIMARY KEY,
       customerId VARCHAR(32),
       firstName VARCHAR(64),
       lastName VARCHAR(64),
       company VARCHAR(64),
       city VARCHAR(64),
       country VARCHAR(64),
       phone1 VARCHAR(32),
       phone2 VARCHAR(32),
       email VARCHAR(64),
       subscription DATE,
       website VARCHAR(255)
   );

COPY customers(id, customerId, firstName, lastName, company, city, country, phone1, phone2, email, subscription, website)
   FROM '<path to this file>/customers-100.csv'
   DELIMITER ','
   CSV HEADER;

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  age INT NOT NULL
);


class User(Base):
    __tablename__ = 'users'
    id = Column(Integer, primary_key=True)
    name = Column(String)
    email = Column(String)
    age = Column(Integer)

