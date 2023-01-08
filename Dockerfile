FROM openjdk:8
ENV MY_DIR /usr/src/mymaven

RUN apt-get update && apt-get install -y graphicsmagick

WORKDIR $MY_DIR
ADD target/Web-Based-Phone-Book-App-1.0-SNAPSHOT.jar $MY_DIR/my_app.jar
ENTRYPOINT ["java", "-jar", "/usr/src/mymaven/my_app.jar"]
EXPOSE 8080

# docker build -t my_phone_book_app -f Dockerfile .
# docker ps
# docker images -a
# docker network ls
# docker run --name my_phone_book_app -p 8080:8080 my_phone_book_app
# if want to delete use docker image rm my_phone_book_app