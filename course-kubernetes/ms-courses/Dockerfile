FROM openjdk:17-jdk-alpine as builder

WORKDIR /app/ms-courses

COPY ./pom.xml /app
COPY ./ms-courses/.mvn ./.mvn
COPY ./ms-courses/mvnw .
COPY ./ms-courses/pom.xml .

#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target

RUN ./mvnw dependency:go-offline

COPY ./ms-courses/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

RUN mkdir ./logs

COPY --from=builder /app/ms-courses/target/ms-courses-0.0.1-SNAPSHOT.jar .

EXPOSE 8001

ENTRYPOINT ["java","-jar","ms-courses-0.0.1-SNAPSHOT.jar"]