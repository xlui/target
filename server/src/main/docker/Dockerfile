FROM openjdk:11-jdk-slim
ADD target-0.0.1.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "-Dserver.port=8080","-Dspring.datasource.url=jdbc:mysql://mysql:3306/target?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Shanghai", "/app.jar"]