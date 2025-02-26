FROM openjdk:17-jdk-alpine
MAINTAINER capo.com
COPY target/redisVersion2-0.0.1-SNAPSHOT.jar asignacion.jar
ENTRYPOINT ["java","-jar","/asignacion.jar"]
