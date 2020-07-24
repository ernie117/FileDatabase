import requests
from behave import *

use_step_matcher("re")


@given("the application is running")
def step_impl(context):
    assert (
        requests.get(
            "http://localhost:8888/actuator/health",
            auth=(context.user, context.password),
        ).json()["status"]
        == "UP"
    )


@when("we call the find film by title endpoint with (?P<title>.*)")
def step_impl(context, title):
    context.response = requests.get(
        "http://localhost:8888/v1/findFilmByTitle",
        auth=(context.user, context.password),
        params={"title": title},
    ).json()


@when("we call the get film ID endpoint with (?P<title>.+)")
def step_impl(context, title):
    context.response = requests.get(
        "http://localhost:8888/v1/getFilmId",
        auth=(context.user, context.password),
        params={"title": title},
    ).json()
