from sqlalchemy import create_engine
from sqlalchemy.orm import declarative_base, sessionmaker

from bot_init import LOGGER

class BotDB:
  def __init__(self, dburi, dblogs=True):
    LOGGER.info("*****: %s" % dburi)
    self.Model = declarative_base()
    self.session = None
    self.engine = create_engine(dburi, echo=dblogs)
    

  def createTables(self):
    self.Model.metadata.create_all(self.engine)

 
  def createSession(self):
    Session = sessionmaker(bind=self.engine)
    self.session = Session()
    return self.session
    
