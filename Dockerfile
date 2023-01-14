FROM maven:3.8.6-amazoncorretto-8 AS maven_build
WORKDIR /phone_book_app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM openjdk:8
WORKDIR /phone_book_app
WORKDIR /phone_book_app
COPY --from=maven_build /phone_book_app/target/Web-Based-Phone-Book-App-1.0-SNAPSHOT.jar my_app.jar
ENTRYPOINT ["java","-jar","my_app.jar"]
EXPOSE 8080