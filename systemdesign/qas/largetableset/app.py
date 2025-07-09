# https://medium.com/technology-hits/database-queries-in-python-sqlalchemy-postgresql-e90afe0a06b4

def initialize():
  from app_init import init_app
  init_app()
 
def fetchUsers():
  from user import get_users
  users = get_users()
  for user in users:
    print(user.id, user.name, user.email, user.age)

def countCustomers():
  from customer import count_customers
  count = count_customers()
  print("Count: %d" % count)

def fetchAllCustomers():
  from customer import get_customers
  customers = get_customers()
  for customer in customers:
    print(customer.id, customer.firstname, customer.email, customer.website)

def fetchCustomers(offset, pagesize=10):
  from customer import qry_customers_offset, qry_customers_key
  # offset = 800000
  print("*" * 50)
  customers = qry_customers_offset(offset, pagesize)
  for customer in customers:
    print(customer.id, customer.firstname, customer.email, customer.website)
  print("*" * 50)
  customers = qry_customers_key(offset, pagesize)
  for customer in customers:
    print(customer.id, customer.firstname, customer.email, customer.website)

if __name__ == "__main__":
  offset = 80
  pagesize =10
  import sys
  if len(sys.argv) > 1:
    offset = int(sys.argv[1])
  if len(sys.argv) > 2:
    pagesize = int(sys.argv[2])
  initialize()
  print("*" * 50)
  fetchUsers()
  print("*" * 50)
  countCustomers()
  print("*" * 50)
  print("Running offset and key for: %d and pagesize:%d" % (offset, pagesize))
  fetchCustomers(offset, pagesize)
