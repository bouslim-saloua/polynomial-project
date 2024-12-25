# Stage 1: Build the Angular application
FROM node:14.20.0-alpine as builder
WORKDIR /app
COPY ./app-polynomial ./app-polynomial
WORKDIR /app/app-polynomial

# Install dependencies and build the project
RUN npm install
RUN npm run build --prod

# Stage 2: Serve the Angular app using Nginx
FROM nginx:alpine
COPY --from=builder /app/app-polynomial/dist/app-polynomial /usr/share/nginx/html
