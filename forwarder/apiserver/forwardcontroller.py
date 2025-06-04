"""Forward controller for user apis and forwarding."""
import os
import datetime

import sendgrid
from sendgrid.helpers.mail import *

from flask import Blueprint, jsonify, g, request
from api_init import auth, app, db, LOGGER, bot
from utils import ERROR, OK, NOK, STATUS
from models.user import User
from models.telegramuser import updateTelegramUser, getTelegramUser
from models.forward import createForward

# Define the blueprint: 'login'
# MODFORWARD = Blueprint('forward', __name__, url_prefix=app.config['APIPREFIX'])
MODFORWARD = Blueprint('forward', __name__)

@MODFORWARD.route('/forward/api/version', methods=['POST', 'GET'])
@auth.login_required
def app_version():
    "Return the current version of the application, does require authentication."
    data = {STATUS: OK, 'message': app.config['APPNAME']}
    return jsonify(data)


@MODFORWARD.route('/forward/localapi/telegram/user', methods=['POST'])
@auth.login_required
def update_telegramuser():
    """ Update telegram user data """
    LOGGER.info(request.url)
    if 'Content-Type' not in request.headers or request.headers['Content-Type'] != 'application/json':
        return jsonify({STATUS: NOK, "message": "Invalid payload format"})
    args = request.json
    LOGGER.info('ARGS: %s', args)
    user = updateTelegramUser(args['name'], args['username'], args['chatId'], args['active'])
    if user:
        return jsonify({STATUS: OK, "message": "Telegram user updated"})
    return jsonify({STATUS: NOK, "message": "Telegram user update failed"})


@MODFORWARD.route('/forward/api/user/<username>', methods=['GET'])
@auth.login_required
def get_telegramuser(username):
    """ get telegram user data """
    LOGGER.debug(request.url)
    LOGGER.debug(request.headers)
    user = getTelegramUser(username)
    if user:
        return jsonify({STATUS: OK, "message": "User '%s' is valid" % username})
    return jsonify({STATUS: NOK, "message": "User '%s' is invalid" % username})


@MODFORWARD.route('/forward/api/message', methods=['POST'])
@auth.login_required
def forward_message():
    """ Forward message to telegram or email """
    LOGGER.info(request.url)
    data = request.json
    LOGGER.info('ARGS: %s', data)
    if data['email']:
        send_email(data)
    elif data['telegram']:
        send_telegram(data)
    else:
        data['status'] = False
        createForward(data)
        return jsonify({STATUS: NOK, "message": "Message was not forwarded"})
    data['status'] = True
    createForward(data)
    return jsonify({STATUS: OK, "message": "Message was forwarded"})


def send_email(data):
    sg = sendgrid.SendGridAPIClient(api_key=app.config['SENDGRID'])
    from_email = Email(app.config['FROMEMAIL'])
    to_email = To(data['email'])
    subject = "SMS Forward -  from: %s on %s " % (data['from'], datetime.datetime.now().strftime("%Y-%m-%d %H:%S"))
    content = Content("text/plain", data['message'])
    mail = Mail(from_email, to_email, subject, content)
    response = sg.client.mail.send.post(request_body=mail.get())
    LOGGER.info("Email send status: %d", response.status_code)


def send_telegram(data):
    user = getTelegramUser(data['telegram'])
    if user:
      bot.send_message(user.chatId, data['message'])
      return True
    return False
        

@auth.verify_password
def verify_password(username, password):
    """
    Verify password called by the authorization module, before allowing the API call to proceed
    Allows both token and username based authentication
    """
    # try to authenticate with username/password
    user = User.query.filter_by(username=username).first()
    if user:
        if user.verify_password(password):
            g.user = user
        else:
            return False
    else:
            return False
    return True


