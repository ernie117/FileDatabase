import json

import requests
from behave import *

use_step_matcher("re")


@given("the application is running")
def step_impl(context):
    assert (
        requests.get("http://localhost:8888/actuator/health").json()["status"] == "UP"
    )


@when("we call the find film by title endpoint with (?P<title>.*)")
def step_impl(context, title):
    context.response = requests.get(
        "http://localhost:8888/v1/findFilmByTitle", params={"title": title}
    ).json()


@when("we call the get film ID endpoint with (?P<title>.+)")
def step_impl(context, title):
    context.response = requests.get(
        "http://localhost:8888/v1/getFilmId", params={"title": title}
    ).json()


@then("it should return a JSON list")
def step_impl(context):
    assert type(context.response) is list


@step("it should contain an object that matches this (?P<title>.+)")
def step_impl(context, title):
    presence = False
    for film in context.response:
        if film["title"].lower() == title:
            presence = True
            break

    assert presence


@step("we receive a response containing this (?P<mongo_id>.+)")
def step_impl(context, mongo_id):
    assert mongo_id in json.dumps(context.response[0])


@step("the film objects contain the expected fields")
def step_impl(context):
    presence = False
    for film in context.response:
        if (
            not all(
                key in film
                for key in (
                    "id",
                    "title",
                    "genre",
                    "director",
                    "cinematographer",
                    "writers",
                    "composer",
                    "releaseDate",
                    "actors",
                    "dateAdded"
                )
            )
        ):
            break
    else:
        presence = True

    assert presence
