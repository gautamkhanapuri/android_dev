import os
BASE_DIR = os.path.abspath(os.path.dirname(__file__))

def get_db_uri():
  """Return the Database URI, based on environment variables"""
  database_uri = os.environ['DBURI'] if 'DBURI' in os.environ else None
  if database_uri:
    db_uri = database_uri
  else:
    db_uri = "sqlite:///%s" % os.path.join(BASE_DIR, "data/db/apiserver.db")
  return db_uri

def get_bot_token():
  """ Return bot token """
  tkn = os.environ['BOTTOKEN'] if 'BOTTOKEN' in os.environ else None
  if tkn is None:
    tkn = ''
  return tkn

def get_sendgrid_token():
  """ Return sendgrid token """
  tkn = os.environ['SENDGRID_API_KEY'] if 'SENDGRID_API_KEY' in os.environ else None
  if tkn is None:
    tkn = ''
  return tkn

def get_log_path():
  """Return the logpath"""
  db_uri = os.join(BASE_DIR, "data/log/bot.log")

class APIConfig:
    """The base config for all others like Testing, Development and Production"""
    DEBUG = False
    SQLALCHEMY_TRACK_MODIFICATIONS = False
    SQLALCHEMY_DATABASE_URI = get_db_uri()
    CSRF_ENABLED = True
    CSRF_SESSION_KEY = os.environ['SESSION_PW'] if 'SESSION_PW' in os.environ else ''
    SECRET_KEY = os.environ['SESSION_PW'] if 'SESSION_PW' in os.environ else ''
    APPNAME = 'ForwardSMS API 1.0, 01-May-2025'
    APIPREFIX = '/forward/api'
    BOTTOKEN = get_bot_token()
    SENDGRID = get_sendgrid_token()
    FROMEMAIL = 'kbajey@gmail.com'

    def get_session_key(self):
        """Utility method for SESSION"""
        return self.CSRF_SESSION_KEY

    def get_appversion(self):
        """Utility method for APPVERSION"""
        return self.APPVERSION

