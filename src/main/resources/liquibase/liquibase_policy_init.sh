\liquibase \
  --driver=com.mysql.cj.jdbc.Driver \
  --url=jdbc:mysql://"${MYSQL_HOST}":3306/"${MYSQL_DB}" \
  --changeLogFile=changelog/dbchangelog-master.yaml \
  --username="${MYSQL_USER}" \
  --password="${MYSQL_PASSWORD}" \
  update