""" Run telegram bot """
from bot_init import initBot

if __name__ == "__main__":
  _, bot = initBot()
  bot.infinity_polling()
  
