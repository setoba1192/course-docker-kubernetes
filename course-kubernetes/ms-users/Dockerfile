ARG MS_NAME=ms-users

FROM openjdk:17-jdk-alpine as builder

#Define variable as global
ARG MS_NAME

WORKDIR /app/$MS_NAME

COPY ./pom.xml /app
COPY ./$MS_NAME/.mvn ./.mvn
COPY ./$MS_NAME/mvnw .
COPY ./$MS_NAME/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target

#RUN ./mvnw dependency:go-offline

COPY ./$MS_NAME/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

RUN mkdir ./logs
#define variable again to reload value
ARG MS_NAME
ARG TARGET_FOLDER=/app/$MS_NAME/target
COPY --from=builder $TARGET_FOLDER/ms-users-0.0.1-SNAPSHOT.jar .

ARG PORT_APP=8001
ENV PORT $PORT_APP

EXPOSE $PORT

ENTRYPOINT ["java","-jar","ms-users-0.0.1-SNAPSHOT.jar"]