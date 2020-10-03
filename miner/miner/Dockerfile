FROM openjdk:11.0.8-jre-slim-buster
EXPOSE 9000
ADD /target/miner-0.0.1-SNAPSHOT.jar miner-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","miner-0.0.1-SNAPSHOT.jar"]
#база данных находится в сети database_default
#docker-compose бд по адресу C:\work\docker\cryprto\Database