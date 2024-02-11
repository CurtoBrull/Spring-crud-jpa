FROM amazoncorretto:17-alpine-jdk
LABEL authors="Curto"
COPY target/crud-jpa-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]