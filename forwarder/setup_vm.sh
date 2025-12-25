#!/bin/bash

set -e

echo "***** SMS Forward - VM First-Time Setup *****"

APP_DIR="~/smsfwd"
DOCKER_IMAGE="gautamkhanapuri/sms-forwarder:1.0"

echo "Updating system packages..."
apt update && apt upgrade -y

if ! command -v docker &> /dev/null; then
    echo "Installing Docker..."
    curl -fsSL https://get.docker.com -o get-docker.sh
    sh get-docker.sh
    rm get-docker.sh
    echo "Docker installed"
else
    echo "Docker already installed"
fi

echo "Creating application directories..."
mkdir -p $APP_DIR/apiserver/data/db
mkdir -p $APP_DIR/apiserver/data/log
mkdir -p $APP_DIR/botserver/data/db
mkdir -p $APP_DIR/botserver/data/log
chmod 755 $APP_DIR
chmod 700 $APP_DIR/apiserver/data
chmod 700 $APP_DIR/botserver/data

echo "Pulling Docker image from Docker Hub..."
docker pull $DOCKER_IMAGE
