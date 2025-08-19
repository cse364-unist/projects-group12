FROM ubuntu:22.04


RUN apt-get update
RUN apt-get install -y vim openjdk-17-jdk maven curl

WORKDIR /app

ADD ./rest2night /app/backend
ADD run.sh /app/run.sh

EXPOSE 8080

RUN chmod +x run.sh

ENTRYPOINT ["sh", "/app/run.sh"]