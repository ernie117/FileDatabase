#!/bin/sh

mvn clean package

docker-compose -f docker-compose.yml up --build
