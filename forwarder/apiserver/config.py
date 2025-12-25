import os
BASE_DIR = os.path.abspath(os.path.dirname(__file__))

def get_db_uri():
  """Return the Database URI, based on environment variables"""
  database_uri = os.environ['DBURI'] if 'DBURI' in os.environ else None
  if database_uri:
    db_uri = database_uri
  else:
    # Create db directory if doesn't exist
    db_dir = os.path.join(BASE_DIR, "data/db")
    os.makedirs(db_dir, exist_ok=True)

    # Check write permission
    db_path = os.path.join(db_dir, "apiserver.db")
    try:
      # Test write by touching file
      open(db_path, 'a').close()
    except PermissionError:
      print(f"ERROR: No write permission for {db_path}")
      sys.exit(1)

    db_uri = "sqlite:///%s" % db_path
    # db_uri = "sqlite:///%s" % os.path.join(BASE_DIR, "data/db/apiserver.db")
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

def get_gmail_token():
  """ Return gmail token """
  tkn = os.environ['GMAIL_APP'] if 'GMAIL_APP' in os.environ else None
  if tkn is None:
    tkn = ''
  return tkn

def get_resend_token():
  """ Return resend API key """
  tkn = os.environ.get('RESEND_API_KEY', '')
  return tkn

def get_log_path():
  """Return the logpath"""
  return os.join(BASE_DIR, "data/log/apiserver.log")

class APIConfig:
    """The base config for all others like Testing, Development and Production"""
    DEBUG = False
    SQLALCHEMY_TRACK_MODIFICATIONS = False
    SQLALCHEMY_DATABASE_URI = get_db_uri()
    CSRF_ENABLED = True
    CSRF_SESSION_KEY = os.environ['SESSION_CSRF'] if 'SESSION_CSRF' in os.environ else ''
    SECRET_KEY = os.environ['SESSION_SECRET'] if 'SESSION_SECRET' in os.environ else ''
    APPNAME = 'Forwarder API 1.1, 08-May-2025'
    APIPREFIX = '/forward/api'
    BOTTOKEN = get_bot_token()
    SENDGRID = get_sendgrid_token()
    GMAILAPP = get_gmail_token()
    RESEND_API_KEY = get_resend_token()
    FROMEMAIL = 'kbajey@gmail.com'

    def get_session_key(self):
        """Utility method for SESSION"""
        return self.CSRF_SESSION_KEY

    def get_appversion(self):
        """Utility method for APPVERSION"""
        return self.APPVERSION

