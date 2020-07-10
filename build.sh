#!/bin/sh

set -e

# The build-image step runs tests so skip in package step.
if [ -n "$TRAVIS" ]; then
  mvn clean -DskipTests package spring-boot:repackage && mvn -DskipTests spring-boot:build-image
else
  mvn clean -DskipTests package spring-boot:repackage && mvn spring-boot:build-image
fi

docker-compose -f docker-compose.yml up -d --build --force-recreate
