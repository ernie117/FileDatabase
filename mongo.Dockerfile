FROM mongo:4.2
EXPOSE 27017
ENTRYPOINT ["mongod", "--bind_ip=0.0.0.0"]
