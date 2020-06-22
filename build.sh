#!/bin/sh

mvn clean package spring-boot:repackage && mvn spring-boot:build-image

docker-compose -f docker-compose.yml up
