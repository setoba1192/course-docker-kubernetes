FROM openjdk:17.0.2

WORKDIR /app/ms-users

COPY ./pom.xml /app
COPY ./ms-users/.mvn ./.mvn
COPY ./ms-users/mvnw .
COPY ./ms-users/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target

#RUN ./mvnw dependency:go-offline

COPY ./ms-users/src ./src

RUN ./mvnw clean package -DskipTests

EXPOSE 8001

ENTRYPOINT ["java","-jar","./target/ms-users-0.0.1-SNAPSHOT.jar"]