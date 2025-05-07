""" Setup logging, bot, sqlalchemy db """
import telebot
import os
import logging
import sys
from config import getConfig


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
    # print("logpath: %s" % logpath)
    fileHandler = logging.FileHandler(logpath)
    fileHandler.setFormatter(logFormat)
    fileHandler.setLevel(loglevel)
    logger.addHandler(fileHandler)

  return logger

bot = None
botCfg = None
db = None
auth = None
LOGGER = setup_logging()


def createDb(dburi, dblogs):
  """Create the sqlalchemy base declarative and init session later."""
  LOGGER.info("DBURI: %s" % dburi)
  from botdb import BotDB
  appdb = BotDB(dburi, dblogs)
  return appdb

def initDb():
  """Initializing DB the sqlalchemy base declarative and init session later."""
  import models
  db.createTables()
  db.createSession()
  models.user.createUser("ABK", "ajeybk", "#kb321!#")

def createBot(tkn):
  """ Create the bot """
  bot = telebot.TeleBot(tkn)
  return bot


def initBot():
  """The starting point of the telegram bot"""
  global bot, db, botCfg
  botCfg = getConfig()
  LOGGER.info("START: %s" % botCfg.APPNAME)
  db = createDb(botCfg.SQLALCHEMY_DATABASE_URI, botCfg.SQLALCHEMY_LOGS)
  bot = createBot(botCfg.BOTTOKEN)
  initDb()
  import mybot
  LOGGER.info("DB, bot created!")
  return db, bot
