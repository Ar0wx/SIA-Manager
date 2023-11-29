from flask import Flask, jsonify, request
from flask_cors import CORS

app = Flask(__name__)
CORS(app)  # Enable CORS for all routes


@app.route('/login', methods=['POST'])
def login():
    data = request.get_json()
    # Replace this with your actual authentication logic
    if data['username'] == 'example' and data['password'] == 'password':
        return jsonify({'message': 'Login successful'})
    else:
        return jsonify({'message': 'Login failed'}), 401


if __name__ == '__main__':
    app.run(debug=True)
