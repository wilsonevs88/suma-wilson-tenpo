
FROM openjdk:11
MAINTAINER wilsonev.saldarriaga88@gmail.com
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} suma-wilson-tenpo-0.0.1-SNAPSHOT.jar
EXPOSE 8081
VOLUME ["/app"]
ENTRYPOINT ["java","-jar","/suma-wilson-tenpo-0.0.1-SNAPSHOT.jar"]

