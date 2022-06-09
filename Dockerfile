FROM python:3.10.4

# Copy all files to /app directory
COPY . /app

# Set working directory to /app
WORKDIR /app

# Upgrade PIP
RUN pip install --upgrade pip

# Install dependencies
RUN pip install -r requirements.txt

# Set environtment variable
ENV PORT=8080

CMD exec gunicorn --bind :$PORT --workers 1 --threads 8 --timeout 0 Recomendation_Function:app

