import os
from dotenv import load_dotenv

load_dotenv()

headersCredenciales = {
    "X-clientId": os.getenv("EMT_CLIENT_ID"),
    "passKey": os.getenv("EMT_PASS_KEY"),
}
