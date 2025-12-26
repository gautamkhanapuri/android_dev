#!/bin/bash

set -e

APP_DIR="~/smsfwd"
ENV_FILE="/etc/environment_smsfwd"
APISERVER_DATA_DIR="$APP_DIR/apiserver/data"
BOTSERVER_DATA_DIR="$APP_DIR/botserver/data"
DOCKER_IMAGE="gautamkhanapuri/sms-forwarder:latest"

echo "***** Starting SMS Forward Backend Containers ***** "

# Check .env exists
if [ ! -f "$ENV_FILE" ]; then
    echo "ERROR: $ENV_FILE not found"
    exit 1
fi

echo "Stopping existing containers (if any)..."
docker stop sms-forward-api sms-forward-bot 2>/dev/null || true
docker rm sms-forward-api sms-forward-bot 2>/dev/null || true

# Start API Server
echo "Starting API Server..."
docker run -d \
  --name sms-forward-api \
  --env-file $ENV_FILE \
  -e RUN_BOT_SERVER=false \
  -e APPPORT=8000 \
  --network host
  -v $APISERVER_DATA_DIR/db:/forwarder/apiserver/data/db \
  -v $APISERVER_DATA_DIR/log:/forwarder/apiserver/data/log \
  -v $BOTSERVER_DATA_DIR/db:/forwarder/botserver/data/db \
  -v $BOTSERVER_DATA_DIR/log:/forwarder/botserver/data/log \
  --restart unless-stopped \
  $DOCKER_IMAGE

sleep 10

  # Start Bot Server
echo "Starting Bot Server..."
docker run -d \
  --name sms-forward-bot \
  --network host
  --env-file $ENV_FILE \
  -e RUN_BOT_SERVER=true \
  -v $APISERVER_DATA_DIR/db:/forwarder/apiserver/data/db \
  -v $APISERVER_DATA_DIR/log:/forwarder/apiserver/data/log \
  -v $BOTSERVER_DATA_DIR/db:/forwarder/botserver/data/db \
  -v $BOTSERVER_DATA_DIR/log:/forwarder/botserver/data/log \
  --restart unless-stopped \
  $DOCKER_IMAGE

sleep 10

# Verify containers are running
echo ""
echo "=========================================="
if docker ps | grep -q sms-forward-api && docker ps | grep -q sms-forward-bot; then
  echo "Both containers started successfully!"
  echo "=========================================="
  echo ""
  docker ps --filter "name=sms-forward"
  echo ""
  echo "Check logs:"
  echo "   docker logs -f sms-forward-api"
  echo "   docker logs -f sms-forward-bot"
  echo ""
  echo "API accessible at: http://$(hostname -I | awk '{print $1}'):8000"
else
  echo "Failed to start containers!"
  echo "=========================================="
  echo ""
  echo "Check logs:"
  docker logs sms-forward-api 2>&1 || true
  docker logs sms-forward-bot 2>&1 || true
  exit 1
fi
