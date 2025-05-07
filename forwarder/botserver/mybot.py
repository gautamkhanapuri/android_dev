import requests
from bot_init import bot, LOGGER, botCfg
from models import createTelegramUser, updateTelegramUser, getTelegramUser, getUser

def send_message(username, msg):
  user = getTelegramUser(username)
  if user:
    bot.send_message(user.chatId, msg)
    return True
  return False

def send_to_apiserver(name, username, chatId, active):
  user = getUser(botCfg.USERNAME)
  if user:
    hdr = {"Authorization": "Basic %s" % user.pwd}
    url = "%s%s" % (botCfg.URL, botCfg.URLPATH)
    data = {"name": name, "username": username, "chatId": chatId, "active": active}
    resp = requests.post(url, json=data, headers=hdr)
    if resp.status_code == 200:
      LOGGER.info("Updated to apiserver: %s", resp.json())

           

@bot.message_handler(commands=['stopbot', 'delete'])
def accept_stop(message):
  name = "%s %s" % (message.chat.first_name, message.chat.last_name)
  LOGGER.info("Command(stopbot/delete) - %s: %s ==> %d", (name, message.chat.username, message.chat.id))
  updateTelegramUser(name, message.chat.username, message.chat.id, False)
  send_to_apiserver(name, message.chat.username, message.chat.id, False)
  bot.reply_to(message, "%s, will not send message until /start command - %s" % (name, botCfg.VERSION))

@bot.message_handler(commands=['start'])
def accept_start(message):
  name = "%s %s" % (message.chat.first_name, message.chat.last_name)
  LOGGER.info("Comand(start) - %s: %s ==> %d", (name, message.chat.username, message.chat.id))
  createTelegramUser(name, message.chat.username, message.chat.id, True)
  send_to_apiserver(name, message.chat.username, message.chat.id, True)
  bot.reply_to(message, "%s, will send messages until /stopbot command - %s" % (name, botCfg.VERSION))

@bot.message_handler(commands=['help'])
def accept_help(message):
  name = "%s %s" % (message.chat.first_name, message.chat.last_name)
  LOGGER.info("Comand(help) - %s: %s ==> %d", (name, message.chat.username, message.chat.id))
  msg = """
    
    %s
    Usage:
      /start -  Will forward messages.
      /stopbot - will stop forwarding messages. 
      /help - Will send this usage
    
    -gaksmsbot
    
  """ % botCfg.VERSION
  bot.reply_to(message, "%s, %s" % (name, msg))

@bot.message_handler(func=lambda message: True)
def echo_all(message):
  name = "%s %s" % (message.chat.first_name, message.chat.last_name)
  LOGGER.info("Other Messages - %s: %s ==> %d", (name, message.chat.username, message.chat.id))
  msg = "'gaksmsbot' is a bot to receive forwarded messages, for details send /help - %s" % botCfg.VERSION
  bot.reply_to(message, msg)

