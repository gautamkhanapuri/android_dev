import datetime
from sqlalchemy import Column, Integer, String, Boolean, DateTime
from sqlalchemy import Sequence
from api_init import db, LOGGER

def createForward(data):
  """Create forwards based on the result of forwarding"""
  LOGGER.info("Creating forward: %s", data['from'])
  forward = ForwardMessage(message=data['message'], sender=data['from'],
                           email=data['email'], telegram=data['telegram'],
                           status=data['status'])
  db.session.add(forward)
  db.session.commit()
  return forward


class ForwardMessage(db.Model):
    __tablename__ = "forwards"
    id = Column(Integer, Sequence("forwardmessage_id_seq"), primary_key=True)
    message = Column(String(512))
    sender = Column(String(128))
    forwardOn = Column(DateTime, default=datetime.datetime.utcnow)
    email = Column(String(128))
    telegram = Column(String(64))
    status = Column(Boolean, default=False)

    def __repr__(self):
        return "<Forward(sender='%s', forwardDate='%s', to='%d, status=%s')>" % (
            self.sender,
            self.forwardOn.strftime('%Y-%m-%d %H:%M:%S'),
            self.email if self.email else self.telegram,
            "Sent" if self.status else "NotSent"
        )
