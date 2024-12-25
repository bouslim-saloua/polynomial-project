# Use a base image with Python
FROM python:3.9-slim

# Set the working directory
WORKDIR /app

# Copy the requirements file if you have dependencies
COPY requirements.txt ./

# Install dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Copy the Python script
COPY script.py ./

# Expose the port if the script opens one (optional)
EXPOSE 5000

# Run the Python script
CMD ["python", "script.py"]
