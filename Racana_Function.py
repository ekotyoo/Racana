#!/usr/bin/env python
# coding: utf-8

from gettext import find
from http.client import responses
import os
from unittest import result
from flask import Flask, request, jsonify
import numpy as np
import pandas as pd
import requests
import json
from keras.models import load_model
from pandas import json_normalize
import pickle

app = Flask(__name__)
app.config['JSON_SORT_KEYS'] = False

#loaddatabase
def api(link):
    global data
    api_url = link
    response = requests.get(api_url)
    json_response = json.loads(response.text)
    data = json_normalize(json_response['data']) 
    return data
#loadmodel database destinasi    
api('https://racana-test.herokuapp.com/api/alldestinations')
place_df = data[['id','name','categoryId','rating','weekdayPrice']]
place_df.columns = ['id','place_name','category','rating','price']
#loadmodel database ratingg
api('https://racana-test.herokuapp.com/api/datasetrating')
df = data.copy()

# loadmodel
from tensorflow import keras
new_model = keras.models.load_model('path_to_my_model')

def dict_encoder(col, data=df):
  unique_val = data[col].unique().tolist()
  val_to_val_encoded = {x: i for i, x in enumerate(unique_val)}
  val_encoded_to_val = {i: x for i, x in enumerate(unique_val)}
  return val_to_val_encoded, val_encoded_to_val


@app.route("/recomendation/predict/<int:id>", methods=["GET"])
def predict(id):
    user_id = id
    place_visited_by_user = df[df.User_Id == user_id]
    place_to_place_encoded, place_encoded_to_place = dict_encoder('Place_Id')
    df['place'] = df['Place_Id'].map(place_to_place_encoded)
    user_to_user_encoded, user_encoded_to_user = dict_encoder('User_Id')
    df['user'] = df['User_Id'].map(user_to_user_encoded)

    num_users, num_place = len(user_to_user_encoded), len(place_to_place_encoded)
    df['Place_Rating'] = df['Place_Rating'].values.astype(np.float32)
    min_rating, max_rating = min(df['Place_Rating']), max(df['Place_Rating'])

    place_not_visited = place_df[~place_df['id'].isin(place_visited_by_user.Place_Id.values)]['id'] 
    place_not_visited = list(set(place_not_visited).intersection(set(place_to_place_encoded.keys())))
 
    place_not_visited = [[place_to_place_encoded.get(x)] for x in place_not_visited]
    user_encoder = user_to_user_encoded.get(user_id)
    user_place_array = np.hstack(([[user_encoder]] * len(place_not_visited), place_not_visited))

    ratings = new_model.predict(user_place_array,50).flatten()
    top_ratings_indices = ratings.argsort()[:][::-1]
    global recommended_place_ids
    recommended_place_ids = [
    place_encoded_to_place.get(place_not_visited[x][0]) for x in top_ratings_indices
    ]
    recommended_place_ids = list(recommended_place_ids)
    return jsonify(recommended_place_ids)

def filter(input):
    Place_Upto_100 = place_df[(place_df['price'] >= 100000)]
    Place_Upto_100 = list(Place_Upto_100["id"])
    if input >= 100000:
        data = [x for x in recommended_place_ids if x in Place_Upto_100]
    else:
        data = [x for x in recommended_place_ids if x not in Place_Upto_100]
    return(data)

@app.route('/recomendation/predictfinal', methods = ['POST'])
def predict1():
    if request.method == 'POST':
        budget = int(request.form['budget'])
        data = filter(budget)
        jumlah_destinasi = int(request.form['jumlah_destinasi'])
        datafinal = data[:jumlah_destinasi]
        return jsonify(
            {
                "status": "Success",
                "message": "Successfully making prediction",
                "data": {
                    "input1": datafinal,
                }
            }
        )




if __name__ == '__main__':
    app.run(debug=False, host='0.0.0.0')



