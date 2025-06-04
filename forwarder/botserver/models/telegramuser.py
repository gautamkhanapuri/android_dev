from sqlalchemy import Column, Integer, String, Boolean
from sqlalchemy import Sequence
from bot_init import db, LOGGER


def getTelegramUser(username):
    "Get telegram users for sending messages."
    user = db.session.query(TelegramUser).filter(TelegramUser.username == username).first()
    if user and user.active:
        return user
    return None


def createTelegramUser(name, username, chatId, active=True):
  "Create telegram users for sending messages."
  user = db.session.query(TelegramUser).filter(TelegramUser.username == username).first()
  if not user:
    user = TelegramUser(name=name, username=username, chatId=chatId, active=active)
    db.session.add(user)
    db.session.commit()
  else:
      user.active = active
      db.session.commit()
  return user


def updateTelegramUser(name, username, chatId, active):
  """ Update the user to active or inactive for forwarding messages."""
  # user = db.session.query(TelegramUser).filter(TelegramUser.username == username).first()
  # if user:
  #   user.active = active
  #   db.session.commit()
  # else:
  #     createTelegramUser(name, username, chatId, active)
  # return True
  return createTelegramUser(name, username, chatId, active)


class TelegramUser(db.Model):
    __tablename__ = "telegramusers"
    id = Column(Integer, Sequence("telegramuser_id_seq"), primary_key=True)
    name = Column(String(100))
    chatId = Column(Integer)
    username = Column(String(50))
    active = Column(Boolean, default=True)

    def __repr__(self):
        return "<User(name='%s', username='%s', chatId='%d, active=%s')>" % (
            self.name,
            self.fullname,
            self.chatId,
            "True" if self.active else "False"
        )
