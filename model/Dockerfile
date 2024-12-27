# Utiliser une image de base avec Python
FROM python:3.9-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier des dépendances
COPY requirements.txt ./

# Ajouter SymPy et Flask au fichier requirements.txt si ce n'est pas déjà fait
RUN echo "sympy\nflask" >> requirements.txt

# Installer les dépendances
RUN pip install --no-cache-dir -r requirements.txt

# Copier le script Python
COPY script.py ./

# Exposer le port si le script ouvre un serveur Flask
EXPOSE 5000

# Lancer le script Python
CMD ["python", "script.py"]
