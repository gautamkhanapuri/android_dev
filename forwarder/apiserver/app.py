""" Run API server """
import argparse
import os
from api_init import initApp

def run_main():
    """Run it as a server to accept requests"""
    _, app = initApp()
    appport = int(os.environ['APPPORT']) if 'APPPORT' in os.environ else 8000
    appdebug = True if 'DEBUG' in os.environ else False
    app.run(host='0.0.0.0', port=appport, debug=appdebug)


def create_main():
    """To create tables and dummy users."""
    dbname, app = initApp()
    from api_init import LOGGER
    from models.user import createUser
    ctx = app.app_context()
    ctx.push()
    LOGGER.warning("Creating DB tables")
    dbname.create_all()
    LOGGER.warning("Creating Dummy Users")
    # createUser("name", "username", "password")
    createUser("ADMIN", "admin", "iIpLrCEleLoEb4vLwspKDeeyjTBe1EFr8EGcOli3rg8TajLGtXIbRXJALL7dNSrR")
    ctx.pop()


if __name__ == "__main__":
  CMDPARSER = argparse.ArgumentParser()
  CMDPARSER.add_argument('-c', action='store_true', default=False, help='Create tables and users')
  ARGS = CMDPARSER.parse_args()
  if ARGS.c:
    create_main()
  else:
    run_main()

