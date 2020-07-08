import requests
from behave import *

use_step_matcher("re")


@given("the application is running")
def step_impl(context):
    """
    :type context: behave.runner.Context
    """
    assert (
        requests.get("http://localhost:8888/actuator/health").json()["status"] == "UP"
    )


@when("we search for a (?P<film>.*)")
def step_impl(context, film):
    """
    :param film: String title of film
    :type context: behave.runner.Context
    """
    context.response = requests.get(
        "http://localhost:8888/v1/findFilmByTitle", params={"title": film}
    ).json()


@then("it should return (?P<mongo_id>.*)")
def step_impl(context, mongo_id):
    """
    :param mongo_id: ID of expected mongo document
    :type context: behave.runner.Context
    """
    assert mongo_id == context.response[0]["id"]
