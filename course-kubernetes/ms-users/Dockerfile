FROM openjdk:17-jdk-alpine as builder

WORKDIR /app/ms-users

COPY ./pom.xml /app
COPY ./ms-users/.mvn ./.mvn
COPY ./ms-users/mvnw .
COPY ./ms-users/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target

#RUN ./mvnw dependency:go-offline

COPY ./ms-users/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

RUN mkdir ./logs

COPY --from=builder /app/ms-users/target/ms-users-0.0.1-SNAPSHOT.jar .

ENV PORT 8001

EXPOSE $PORT

ENTRYPOINT ["java","-jar","ms-users-0.0.1-SNAPSHOT.jar"]