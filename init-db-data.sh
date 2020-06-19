#!/bin/bash

mongoimport --db film_collection --collection films --authenticationDatabase admin --username ernie --password tytQZF11 --type json --drop --file init-data.json --jsonArray