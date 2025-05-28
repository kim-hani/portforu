#FROM openjdk:17-jdk-alpine
#WORKDIR /app
#ARG JAR_FILE=build/libs/portforu-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} app.jar
#
#ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
#EXPOSE 8080