from flask import Flask, jsonify
import requests
import os
import json
from datetime import datetime, timedelta

app = Flask(__name__)
CACHE_FILE = os.path.join('cache', 'quotes.json')
CURRENCIES = ['EUR', 'USD', 'GBP', 'CNY']
API_URL = 'https://economia.awesomeapi.com.br/all'

def get_cached_quotes():
    if os.path.exists(CACHE_FILE):
        with open(CACHE_FILE, 'r') as file:
            data = json.load(file)
            if datetime.now() - datetime.fromisoformat(data['created_at']) < timedelta(days=1):
                return data
    return None

def fetch_quotes():
    response = requests.get(API_URL)
    response.raise_for_status()
    quotes = response.json()
    data = {
        'BRL': {currency: float(quotes[currency]['ask']) for currency in CURRENCIES},
        'created_at': datetime.now().isoformat()
    }
    with open(CACHE_FILE, 'w') as file:
        json.dump(data, file)
    return data

@app.route('/quote/<currency_code>')
def get_quote(currency_code):
    if currency_code not in CURRENCIES:
        return jsonify({'error': 'Invalid currency code'}), 400
    data = get_cached_quotes() or fetch_quotes()
    return jsonify({
        'code': currency_code,
        'value': data['BRL'][currency_code],
        'created_at': data['created_at']
    })

@app.route('/quote')
def get_all_quotes():
    data = get_cached_quotes() or fetch_quotes()
    return jsonify({
        'quotes': data['BRL'],
        'created_at': data['created_at']
    })

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)