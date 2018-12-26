FROM openjdk:11-jre-slim
ADD target-0.0.1.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "-Dserver.port=8080","-Dspring.profiles.active=prod", "/app.jar"]
