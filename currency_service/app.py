from flask import Flask, jsonify, request
import requests
import redis
import json
from datetime import datetime, timedelta

app = Flask(__name__)

r = redis.Redis(host='redis', port=6379, db=0)

def get_currency_rate(from_currency, to_currency):
    cache_key = f"{from_currency}_{to_currency}"
    cached = r.get(cache_key)

    if cached:
        return json.loads(cached)
    else:
        response = requests.get(f"https://economia.awesomeapi.com.br/last/{from_currency}-{to_currency}")
        if response.status_code == 200:
            data = response.json()
            rate_key = f'{from_currency}{to_currency}'
            rate = data[rate_key]['ask']
            result = {'rate': rate, 'created_at': datetime.now().isoformat()}
            r.setex(cache_key, timedelta(hours=24), json.dumps(result))
            return result
        else:
            return {'error': 'Failed to fetch currency rate'}

@app.route('/')
def index():
    routes = []
    for rule in app.url_map.iter_rules():
        routes.append(f"{rule.endpoint}: {rule}")
    return jsonify(routes)

@app.route('/convert')
def convert():
    from_currency = request.args.get('from', default='BRL')
    print(from_currency)
    to_currency = request.args.get('to', default='USD')
    print(to_currency)
    result = get_currency_rate(from_currency, to_currency)
    return jsonify(result)

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')