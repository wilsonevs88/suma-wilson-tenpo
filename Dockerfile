
FROM openjdk:11
LABEL key="wilsonev.saldarriaga88@gmail.com" 
WORKDIR /app
ADD target/suma-wilson-tenpo-0.0.1-SNAPSHOT.jar /app/suma.jar
CMD ["java","-jar", "/app/suma.jar"]

