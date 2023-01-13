FROM openjdk:8
EXPOSE 8080
ADD target/Web-Based-Phone-Book-App-1.0-SNAPSHOT.jar Web-Based-Phone-Book-App-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "Web-Based-Phone-Book-App-1.0-SNAPSHOT.jar"]