import json

from behave import *

use_step_matcher("re")


@then("it should return a JSON list")
def step_impl(context):
    assert type(context.response) is list


@step("it should contain an object that matches this (?P<title>.+)")
def step_impl(context, title):
    """
    Title searches can retrieve multiple results so we loop through
    the results looking for the expected title.
    :param context: behave context
    :param title: title to search
    """
    presence = False
    for film in context.response:
        if film["title"].lower() == title:
            presence = True
            break

    assert presence


@step("we receive a response containing this (?P<mongo_id>.+)")
def step_impl(context, mongo_id):
    """
    Deliberately searches whole json string result as a film title
    search can retrieve multiple films/ids.
    :param context: behave context
    :param mongo_id: id field to search
    """
    assert mongo_id in json.dumps(context.response[0])


@step("the film objects contain the expected fields")
def step_impl(context):
    presence = False
    for film in context.response:
        if not all(
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
                "dateAdded",
            )
        ):
            break
    else:
        presence = True

    assert presence
