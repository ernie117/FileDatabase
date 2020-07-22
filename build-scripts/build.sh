#!/bin/sh

set -e

if [ -n "$TRAVIS" ]; then
  # In Travis CI tests will already have been run at this point, so skip them.
  mvn clean -DskipTests package spring-boot:repackage && mvn -DskipTests spring-boot:build-image
else
  mvn clean -DskipTests package spring-boot:repackage && mvn spring-boot:build-image
fi

docker-compose -f docker-compose.yml up -d --build --force-recreate
