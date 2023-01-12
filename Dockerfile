FROM openjdk:8
EXPOSE 8080
ADD target/Web-Based-Phone-Book-App-1.0-SNAPSHOT.jar Web-Based-Phone-Book-App-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "Web-Based-Phone-Book-App-1.0-SNAPSHOT.jar"]

# docker build -t my_phone_book_app -f Dockerfile .
# docker ps
# docker images -a
# docker network ls
# docker run --name my_phone_book_app -p 8080:8080 my_phone_book_app
# if want to delete use docker image rm my_phone_book_app