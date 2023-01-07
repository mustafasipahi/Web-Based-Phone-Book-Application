FROM openjdk:8
ADD target/Web-Based-Phone-Book-App-1.0-SNAPSHOT.jar Web-Based-Phone-Book-App-1.0-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "Web-Based-Phone-Book-App-1.0-SNAPSHOT.jar"]