FROM openjdk:8-alpine
ADD target/spring-films-app.jar spring-films-app
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "spring-films-app"]
