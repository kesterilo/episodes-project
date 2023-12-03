FROM mysql:8.0
ENV MYSQL_DATABASE="episodes_task_db"
ENV MYSQL_ROOT_PASSWORD="episodes"
ADD db_backup.sql docker-entrypoint-initdb.d/db_backup.sql