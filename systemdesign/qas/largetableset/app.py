# https://medium.com/technology-hits/database-queries-in-python-sqlalchemy-postgresql-e90afe0a06b4

from app_init import init_app

init_app()
 
from user import get_users

users = get_users()
for user in users:
  print(user.id, user.name, user.email, user.age)

from customer import get_customers, qry_customers_offset, qry_customers_key

customers = get_customers()
print("Count: %d" % len(customers))
# for customer in customers:
#   print(customer.id, customer.firstname, customer.email, customer.website)
print("*" * 50)
customers = qry_customers_offset(80)
for customer in customers:
  print(customer.id, customer.firstname, customer.email, customer.website)
print("*" * 50)
customers = qry_customers_key(80)
for customer in customers:
  print(customer.id, customer.firstname, customer.email, customer.website)
