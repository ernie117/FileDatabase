[![Build Status](https://travis-ci.com/ernie117/FilmDatabase.svg?branch=master)](https://travis-ci.com/ernie117/FilmDatabase)

Remember to start the Docker daemon first

> systemctl start docker.service

Run everything with:

> docker-compose -f docker-compose.yml up (-d)

Environment variables are defined in a locally stored `.env` file.

Shell command to dump the "films" collection.
```shell script
mongoexport --username <username> \
    --password <password> \
    --authenticationDatabase admin \
    --db film_collection \
    --collection films \
    --jsonArray --pretty \
    --out init-data.json
```
