\liquibase \
  --url=jdbc:mysql://phone-book-app-mysql:3306/phone_book_app?autoReconnect=true \
  --username=root \
  --password=root \
  --changeLogFile=changelog/dbchangelog-master.yaml \
  --driver=com.mysql.cj.jdbc.Driver \
  update