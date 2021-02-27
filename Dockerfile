FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN apk update
RUN apk upgrade
RUN apk add ca-certificates && update-ca-certificates
# Change TimeZone
RUN apk add --update tzdata
ENV TZ=America/Sao_Paulo
# Clean APK cache
RUN rm -rf /var/cache/apk/*
RUN apk --update add fontconfig ttf-dejavu

ENTRYPOINT ["java","-jar","/app.jar"]

