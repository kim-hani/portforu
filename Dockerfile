FROM openjdk:17-jdk-alpine
WORKDIR /app
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.config.location=classpath:/application.yml,/app/config/application-local.yml", "-jar", "/app.jar"]