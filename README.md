<br>
<p align="center">
  <a href="https://travis-ci.com/ernie117/FilmDatabase"><img src="https://travis-ci.com/ernie117/FilmDatabase.svg?branch=master" alt="Travis CI Info"></a>
  <a href="https://codecov.io/gh/ernie117/FilmDatabase"><img src="https://codecov.io/gh/ernie117/FilmDatabase/branch/master/graph/badge.svg" alt="Code Coverage Info"></a>
</p>

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
- [ ] Setup CircleCI pipeline
- [x] Setup TravisCI pipeline
- [x] Provision EC2 instance for deployment
- [x] Build and run with docker-compose
