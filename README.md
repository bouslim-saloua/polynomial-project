# Automatic Calculation of Polynomial Roots with Symbolic Factorization
---
## Description

This project develops an automated tool that calculates the roots of polynomials of any degree. By using numerical methods, such as Newton's method, combined with symbolic factoring techniques, this tool allows users to enter a polynomial (e.g., \(x^3 - 6x^2 + 11x - 6\)) and immediately receive exact or approximate roots based on the coefficients and the degree of the polynomial.
![calc](https://github.com/user-attachments/assets/f854e669-e020-4c11-b6bb-23e7d6849e75)


The application also offers a symbolic factoring feature to simplify polynomials while providing numerical solutions.
## Eureka Server
Eureka Server role in our project is a service registry that plays a central role in the automatic detection of devices and services on a network.
## Front-End (client)

## Cloud Gateway
an API gateway built on top of the Spring Framework, designed to provide a simple, effective way to route requests to various microservices and handle common cross-cutting concerns such as security, monitoring, and resiliency.
```sh
  spring:
  cloud:
    gateway:
      routes:
        - id: r1
          uri: http://localhost:8088
          predicates:
            - Path=/api/auth/**
        - id: r2
          uri: http://localhost:8089
          predicates:
            - Path=/historiques/**
        - id: r3
          uri: http://localhost:8090
          predicates:
            - Path=/calcule/**

```

## Python Model
We used Flask to create access to the functions that contains our calculations by providing routes to each one.
Route Example
```python
@app.route('/factorisation', methods=['POST'])
```
Function Example
```python
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
```
Serving the app
```python
if __name__ == '__main__':
    app.run(debug=True)
```
## Services
- service-user
- service-calcul-polynomial
- service-historique



