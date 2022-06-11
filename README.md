# RESTful APIs

In making the **RESTful APIs** we use [Python](https://github.com/python) using the [Flask Framework](https://flask.palletsprojects.com/en/2.0.x/) and for responses using **JSON** format. For each URL that can be used will be explained below.

## RECOMENDATION USE PRICE, TOTAL DESTINATION, AND ID USER

This **RESTful API**s is used to convert the id from the **Machine Learning** model into a place id which is needed by **Mobile Development** to display tourist destination in the application. Response from each URL using **JSON** format.

**Base URL :**

> http://34.101.178.213:5000/recomendation/predictfinal

**Request :**

* iduser
* budget
* jumlah_destination


**Method :**

> `POST`

- **List all package**

```json
{
  "status": "Success",
  "message": "Successfully making predict",
  "data": [
          "1",
          "10",
          "100"
          ]
}
```

## RECOMENDATION HISTORY USER

This **RESTful API**s is used to convert the id from the **Machine Learning** model into a place id which is needed by **Mobile Development** to display tourist destination in the application. Response from each URL using **JSON** format.

**Base URL :**

> http://34.101.178.213:5000/predict
> and
> http://34.101.178.213:5000/predict2

**Request :**

* last_name of destination

**Method :**

> `POST`

- **List all package**

```json
{
  "status": "Success",
  "message": "Successfully making predict",
  "data": [
          "Air Terjun Aling Aling",
          ]
}
