""" Setup logging, api, sqlalchemy db """
import telebot
import os
import logging
from datetime import timedelta

from flask import Flask, jsonify, request, session
from flask_cors import CORS
from flask_sqlalchemy import SQLAlchemy
from flask_httpauth import HTTPBasicAuth

from utils import TOKENTIMEOUT


def setup_logging(logfile=True):
  fmt = '%(asctime)s(%(module)s:%(lineno)4d) - %(message)s'
  logFormat = logging.Formatter(fmt)

  level = os.getenv('LOGLEVEL', 'INFO')
  loglevel = level if level in ['CRITICAL', 'ERROR', 'WARNING', 'INFO', 'DEBUG'] else 'WARNING'
  logging.basicConfig(level=loglevel, datefmt='%d/%b %H:%M', format=fmt)

  logger = logging.getLogger()

  if logfile:
    baseDir = os.path.abspath(os.path.dirname(__file__))
    logpath = os.path.join(baseDir, "data/log/bot.log")
    fileHandler = logging.FileHandler(logpath)
    fileHandler.setFormatter(logFormat)
    fileHandler.setLevel(loglevel)
    logger.addHandler(fileHandler)

  return logger

bot = None
api = None
db = None
auth = None
LOGGER = setup_logging()

def get_model(self, name):
    "Access the model with a name"
    return self.Model._decl_class_registry.get(name, None)


def get_model_by_tablename(self, tablename):
    "Access the model given a tablename"
    for cname in self.Model._decl_class_registry.values():
        if hasattr(cname, '__tablename__') and cname.__tablename__ == tablename:
            return cname
    return None


def create_db():
    "Create the sqlalchemy base declarative and init with app later."
    SQLAlchemy.get_model_by_tablename = get_model_by_tablename
    SQLAlchemy.get_model = get_model
    appdb = SQLAlchemy()
    return appdb

def index():
    "Main index url for the backend app, an empty json object"
    LOGGER.info("Home:%s", app.config['APPNAME'])
    data = {'status': 'OK', 'app': app.config['APPNAME']}
    return jsonify(data)


def make_session_permanent():
    "Session timeout set to 600 minutes, we can do it based on configuration too."
    session.permanent = True
    app.permanent_session_lifetime = timedelta(minutes=TOKENTIMEOUT)

def not_found(error):
    "Any URI not found shall call this API"
    return jsonify({"error": "Unknown, %s" % error, 'status': 'NOK'})


def create_app(mydb=None):
    "Create the flask app and its other configuration, register routes from controllers."
    config = {
        "default": "config.APIConfig"
    }
    myapp = Flask(__name__)
    config_name = os.getenv('CONFIG', 'default')
    myapp.config.from_object(config[config_name])
    LOGGER.info("DB URI: %s", myapp.config['SQLALCHEMY_DATABASE_URI'])
    cors = CORS(myapp, resources={
                                   r"/forward/api/telegram/user": {"origins": "localhost", "allow_headers": "*"},
                                   r"/forward/api/*": {"origins": "*", "allow_headers": "*"}
           })
    # 'allow_headers': ['Origin', 'X-Requested-With', 'Content-Type', 'Accept', 'Authorization']}})
    # Access-Control-Allow-Origin => *
    # Access-Control-Allow-Headers => Origin, X-Requested-With, Content-Type, Accept, Authorization
    if mydb:
        mydb.init_app(myapp)
    myapp.before_request(make_session_permanent)
    myapp.register_error_handler(404, not_found)
    myapp.add_url_rule('/', 'index', index)
    return myapp

def unauthorized():
    """Message when authorization fails."""
    response = jsonify({'status': 401, 'error': 'unauthorized',
                        'message': 'Please authenticate to access this API.'})
    response.status_code = 401
    response.headers.set('WWW-Authenticate', "xBasic realm='Authentication Required'")
    return response


def create_auth():
    """Basic authentication mechanism used, should be wrapped under https, otherwise no use"""
    myauth = HTTPBasicAuth()
    myauth.error_handler(unauthorized)
    return myauth


def createBot(tkn):
  """ Create the bot """
  sendbot = telebot.TeleBot(tkn)
  return sendbot


def register_modules(myapp):
    "Register all the routes from this function"
    # Register blueprint(s)
    from forwardcontroller import MODFORWARD
    myapp.register_blueprint(MODFORWARD)


def initApp():
    "The starting point of the flask application, both for debug and production."
    global app, db, auth, bot
    LOGGER.info("START")
    db = create_db()
    app = create_app(db)
    auth = create_auth()
    bot = createBot(app.config['BOTTOKEN'])
    register_modules(app)
    LOGGER.info("DB, App,Auth created!")
    return db, app
