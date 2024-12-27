# Automatic Calculation of Polynomial Roots with Symbolic Factorization
---
## Description

This project develops an automated tool that calculates the roots of polynomials of any degree. By using numerical methods, such as Newton's method, combined with symbolic factoring techniques, this tool allows users to enter a polynomial (e.g., \(x^3 - 6x^2 + 11x - 6\)) and immediately receive exact or approximate roots based on the coefficients and the degree of the polynomial.
![calc](https://github.com/user-attachments/assets/f854e669-e020-4c11-b6bb-23e7d6849e75)

---

## Technologies Used
- **Spring Boot** : Backend microservices
- **Python** : Symbolic factorization and numerical calculation
- **Angular** : Frontend user interface
- **Eureka Server** : Service discovery
- **JWT** : Secure authentication
- **Docker** : Containerization

---

## Structure Project

```plaintext
.
├── polynomial-project
|   ├── Eureka-server
|        ├──Dockerfile
|        ├── src/
│        └── pom.xml
│   ├── Gateway
|       ├── Dockerfile
|       ├── src/
│       └── pom.xml
│   ├── model
|      ├── script.py
|      ├── requiments.txt
|      └── Dockerfile
|   ├── service-calcul-polynomial
|      ├── Dockerfile
|      ├── src/
│      └── pom.xml
|   ├── service-historique
|      ├── Dockerfile
|      ├── src/
│      └── pom.xml
|   ├── service-user
|      ├── Dockerfile
|      ├── src/
│      └── pom.xml
|   ├── frontend
|      ├── app-polynomial
|      ├── Dockerfile
|      └── package-lock.json
└── docker-compose.yml
 
```
![structure](https://github.com/user-attachments/assets/5dfec6d0-f7cc-4933-92bd-0c814245efd5)

---
The application also offers a symbolic factoring feature to simplify polynomials while providing numerical solutions.
## Eureka Server
Eureka Server role in our project is a service registry that plays a central role in the automatic detection of devices and services on a network.
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
- **Service-user** :This microservice handles user authentication and management. It uses JWT (JSON Web Token) to ensure secure access control for registered users. Core functionalities include user registration, login, and profile management.
- **Service-calcul-polynomial** :This microservice is responsible for performing polynomial calculations.
  It provides APIs to compute the roots of polynomials, supports symbolic factorization, and delivers both exact and approximate solutions depending on the input.
- **Service-historique** :This microservice stores and manages the history of all polynomial calculations.
  It ensures users can access their past computations, making it easier to track and review previous results.

## Front-End (client)
The front-end client is built using **Angular** and serves as the user interface for the polynomial project. 
 It communicates with the backend services via the Gateway, enabling users to:  
- Input polynomials for computation.  
- View calculation results in real-time.  
- Access and manage their calculation history.


---

## Getting Started  

Follow these steps to set up and run the project locally.

### Prerequisites  

1. **Git**  
   - Ensure Git is installed. [Download Git](https://git-scm.com/).

2. **Docker**  
   - Install Docker and Docker Compose. [Get Docker](https://www.docker.com/products/docker-desktop).

3. **Node.js and Angular**  
   - Install Node.js (v14.11.0) and Angular CLI globally:
     ```bash
     npm install -g @angular/cli
     ```

---

### Backend Setup  

1. **Clone the Project**  
   ```bash
   git clone https://github.com/bouslim-saloua/polynomial-project.git
   cd polynomial-project
2. **Build and Start Services**
   - Ensure Docker is running.
   - Use docker-compose to start all services:
```sh
docker-compose up --build
```
3. **Verify Services**
 - Eureka Server: http://localhost:8761
  - Gateway: http://localhost:8888

### Frontend Setup
1. **Install Dependencies**
 ```sh
  cd frontend/app-polynomial
  npm install
 ```
2. **Run the Frontend**
 ```sh
  ng serve --open
 ```

## Video Demonstration
https://drive.google.com/file/d/1k3Gob8sCNY2FbahtMEVUNPeIxNGMQw7h/view?usp=sharing

### Contributors
This project was made possible through the collaborative efforts of the following contributors:
- BOULAMAAT Amina ([GitHub] (https://github.com/amina-bl))
- BOUSLIM Saloua ([Github] (https://github.com/bouslim-saloua))
- EL BAHRAOUY Fatiha ([Github] (https://github.com/Fatielbah))
