FROM openjdk:13-alpine
COPY target/spring-films-app.jar spring-films-app
EXPOSE 8888
ENTRYPOINT ["java", "--enable-preview", "-jar", "spring-films-app"]
