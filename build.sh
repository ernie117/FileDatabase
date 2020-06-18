#!/bin/sh

mvn clean package spring-boot:repackage

docker-compose -f docker-compose.yml up --build
