FROM eclipse-temurin:21-jre-alpine
LABEL maintainer="PcHardware"
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 9006
ENTRYPOINT ["java", "-jar", "app.jar"]