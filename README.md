Remember to start the Docker daemon first

Then start a mongo container

To connect to the mongo container:
> docker exec -it <container-name> <mongo-container-name> mongo

package:
./mvnw package

build:
docker build -f Dockerfile -t docker-films-test .

run:
docker run -p 8888:8888 --link=<name_of_mongo_container> docker-films-test 
