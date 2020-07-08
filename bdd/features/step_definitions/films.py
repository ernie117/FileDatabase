import behave
import requests
import json


RESPONSE = None


@behave.given("the application is running")
def step_call_healthcheck():
    assert requests.get("http://localhost:8888/actuator/health").json()["status"] == "UP"


@behave.when("we search for a '{film:str}'")
def step_send_film_request(context, film):
    global RESPONSE
    RESPONSE = requests.post("http://localhost:8888/v1/findFilmByTitle", data=film)


@behave.then("is should return '{document:int}'")
def step_compare_result_with_doc(context, document):
    with open("../../../init-db/init-data.json") as f_obj:
        film_data = json.load(f_obj)

    assert film_data[document] is context.response[0]
