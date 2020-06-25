#!/bin/bash

mongoimport --db film_collection --collection films --type json --drop --file init-data.json --jsonArray