FROM python:3.11-slim

WORKDIR /forwarder

COPY . .

RUN pip install --no-cache-dir -r requirements.txt

RUN chmod +x ./entrypoint.sh

RUN mkdir -p /forwarder/apiserver/data/db /forwarder/apiserver/data/log /forwarder/botserver/data/db /forwarder/botserver/data/log

EXPOSE 8000

ENTRYPOINT ["./entrypoint.sh"]
