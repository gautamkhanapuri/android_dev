#!/bin/bash
# entrypoint.sh - Runs API or Bot server based on environment variable

set -e  # Exit on error

echo "***** SMS Forward Backend Starting *****"

if [ "$RUN_BOT_SERVER" = "true" ]; then
    echo "Starting Telegram Bot Server..."
    
    # Change to botserver directory and run
    cd /forwarder/botserver
    exec python -u botapp.py
    
else
    echo "Starting Flask API Server..."
    
    # Change to apiserver directory and run with Gunicorn
    cd /forwarder/apiserver
    exec gunicorn --bind 0.0.0.0:${APPPORT:-8000} \
                  --workers 2 \
                  --threads 2 \
                  --timeout 60 \
                  --access-logfile - \
                  --error-logfile - \
                  wsgi:application
fi
