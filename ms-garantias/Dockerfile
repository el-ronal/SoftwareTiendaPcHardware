FROM eclipse-temurin:21-jre-alpine
LABEL maintainer="PcHardware"
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 9008
ENTRYPOINT ["java", "-jar", "app.jar"]