Remember to start the Docker daemon first

> systemctl start docker.service

Then start a mongo container attached to the local mongodb volume

> docker run --volume /data/db:/data/db -p 27017:27017 --name "name of mongo container" mongo

To connect to the mongo container (optional for now):

> docker exec -it <container-name> <mongo-container-name> mongo

package the spring boot app:

> mvn clean package

build the docker image:

> docker build -f Dockerfile -t docker-films-test .

run spring boot container and link it to the already running mongo container:

> docker run --volume /data/db:/data/db -p 27017:27017 --name "name of mongo container" mongo

> docker run -e DOCKER_MONGO_USERNAME=<> \
>-e DOCKER_MONGO_PASSWORD=<> \
>-e SSL_KEY_STORE_PASSWORD=<> \
>-e SSL_KEY_STORE_ALIAS=<> \
>-p 8888:8888 \
>--link="name of mongo container" \
>docker-films-test

