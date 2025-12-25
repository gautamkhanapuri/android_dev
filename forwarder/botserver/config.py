import os
BASE_DIR = os.path.abspath(os.path.dirname(__file__))

def get_db_uri():
  """Return the Database URI, based on environment variables"""
  database_uri = os.environ['DBURI'] if 'DBURI' in os.environ else None
  if database_uri:
    db_uri = database_uri
  else:
    db_dir = os.path.join(BASE_DIR, "data/db")
    os.makedirs(db_dir, exist_ok=True)

    db_path = os.path.join(db_dir, "messages.db")
    try:
      open(db_path, 'a').close()
    except PermissionError:
      print(f"ERROR: No write permission for {db_path}")
      sys.exit(1)

    db_uri = "sqlite:///%s" % db_path
    # db_uri = "sqlite:///%s" % os.path.join(BASE_DIR, "data/db/messages.db")
  return db_uri

def get_bot_token():
  """ Return bot token """
  tkn = os.environ['BOTTOKEN'] if 'BOTTOKEN' in os.environ else None
  if tkn is None:
    tkn = ''
  return tkn

def get_log_path():
  """Return the logpath"""
  db_uri = os.join(BASE_DIR, "data/log/bot.log")

class BotConfig(object):
  """The config for Bot"""
  DEBUG = False
  TESTING = False
  SQLALCHEMY_TRACK_MODIFICATIONS = False
  SQLALCHEMY_DATABASE_URI = get_db_uri()
  SQLALCHEMY_LOGS = False
  APPNAME = 'Forwarder Telegram Bot 1.1, 08-May-2025'
  URL = 'http://localhost:9080'
  URLPATH = '/forward/localapi/telegram/user'
  BOTTOKEN = get_bot_token()
  USERNAME = 'ajeybk'
  VERSION = 'ForwardSMS Bot 1.1'
    
def getConfig(botenv=None):
  return BotConfig()
    
