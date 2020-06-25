#!/bin/sh

# The build-image step runs tests so skip in package step.
mvn clean -DskipTests package spring-boot:repackage && mvn spring-boot:build-image

docker-compose -f docker-compose.yml up -d --build
