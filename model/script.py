from sympy import symbols, factor, solve
from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route('/factorisation', methods=['POST'])
def factorisation():
    try:
        data = request.get_json()
        polynome_str = data.get('polynome')
        x = symbols('x')
        poly = eval(polynome_str)
        factored_poly = factor(poly)
        return jsonify({'factorisation': str(factored_poly)})
    except Exception as e:
        return jsonify({'error': str(e)}), 400

@app.route('/racines', methods=['POST'])
def racines():
    try:
        data = request.get_json()
        polynome_str = data.get('polynome')
        x = symbols('x')
        poly = eval(polynome_str)
        roots = solve(poly, x)
        return jsonify({'racines': [str(root) for root in roots]})
    except Exception as e:
        return jsonify({'error': str(e)}), 400

if __name__ == '__main__':
    app.run(debug=True)
