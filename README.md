<br>
<p align="center">
  <a href="https://travis-ci.com/ernie117/FilmDatabase"><img src="https://travis-ci.com/ernie117/FilmDatabase.svg?branch=master" alt="Travis CI Info"></a>
  <a href="https://codecov.io/gh/ernie117/FilmDatabase"><img src="https://codecov.io/gh/ernie117/FilmDatabase/branch/master/graph/badge.svg" alt="Code Coverage Info"></a>
  <a href="https://github.com/psf/black"><img alt="Code style: black" src="https://img.shields.io/badge/code%20style-black-000000.svg"></a>
</p>

## WIP RESTful API to query Films from my personal collection based on Director, Actor, Writer, etc.

This project is designed to be dockerized alongside a MongoDB container, run with `docker-compose` and hosted on EC2.

Functional feature tests are being written using python package [behave](https://github.com/behave/behave) and formatted with [black](https://github.com/psf/black).

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

### TODO
- [ ] Unit tests for Service component
- [ ] Flesh out functional tests
- [ ] Extend to include TV series collection?
- [ ] Automate image deployment to ECR
- [x] Setup TravisCI pipeline
- [x] Provision EC2 instance for deployment
- [x] Build and run with docker-compose
