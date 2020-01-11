Remember to start the Docker daemon first

> systemctl start docker.service

Then start a mongo container

To connect to the mongo container:

> docker exec -it <container-name> mongo

package:
mvn clean package

build:
docker build -f Dockerfile -t docker-films-test .

run:

> docker run --volume /data/db:/data/db -p 27017:27017 --name <name-of-mongo-container> mongo
