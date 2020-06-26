FROM mongo:4.2.2
COPY /init-db/init-data.json init-data.json
COPY /init-db/init-db-data.sh /docker-entrypoint-initdb.d/init-db-data.sh
EXPOSE 27017
