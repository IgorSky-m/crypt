FROM openjdk:11.0.8-jre-slim-buster
EXPOSE 9000
ADD /target/crypto-rest-0.0.1-SNAPSHOT.jar crypto-rest-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","crypto-rest-0.0.1-SNAPSHOT.jar"]
