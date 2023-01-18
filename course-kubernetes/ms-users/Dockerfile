FROM openjdk:17.0.2

WORKDIR /app/ms-users

COPY ./pom.xml /app
COPY ./ms-users .

RUN ./mvnw clean package -DskipTests

EXPOSE 8001

ENTRYPOINT ["java","-jar","./target/ms-users-0.0.1-SNAPSHOT.jar"]