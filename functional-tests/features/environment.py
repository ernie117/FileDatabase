from behave import fixture, use_fixture
from OpenSSL import crypto
import os


# Not required for now but if I ever need to run tests against prod in HTTPS
# with a self-signed cert
@fixture(name="requests.ssl.certificate")
def ssl_cert_setup(context):
    context.pem = "cert.pem"
    with open("../src/main/resources/keystore.p12", "rb") as f:
        keystore = f.read()

    p12 = crypto.load_pkcs12(keystore, os.getenv("USER_PASSWORD_PLAIN"))
    cert = crypto.dump_certificate(crypto.FILETYPE_PEM, p12.get_certificate())
    with open(context.pem, "wb") as f:
        f.write(cert)

    yield context.pem
    os.remove(context.pem)


def auth_setup(context):
    context.user = os.getenv("USER_NAME")
    context.password = os.getenv("USER_PASSWORD_PLAIN")

    return context


def before_all(context):
    # use_fixture(ssl_cert_setup, context)
    use_fixture(auth_setup, context)
