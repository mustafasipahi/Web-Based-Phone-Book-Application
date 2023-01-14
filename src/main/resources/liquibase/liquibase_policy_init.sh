/liquibase/liquibase \
    --driver=com.mysql.cj.jdbc.Driver \
    --url=jdbc:mysql://phone-book-app-mysql:3306/phone_book_app \
    --changeLogFile=src/main/resources/liquibase/changelog-master.yml \
    --username=root \
    --password=root \
    update