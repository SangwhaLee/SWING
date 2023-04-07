FROM python:3.9

WORKDIR /usr/src/app

COPY requirements.txt ./
RUN pip install --upgrade pip
RUN pip install -r requirements.txt
RUN apt-get update
RUN apt-get -y install libgl1-mesa-glx
COPY . .

EXPOSE 8000

CMD ["python3", "manage.py", "runserver", "0.0.0.0:8000"]
