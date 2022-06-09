#!/usr/bin/env python
# coding: utf-8

from gettext import find
from http.client import responses
import os
from unittest import result
from flask import Flask, request, jsonify
import numpy as np
import pandas as pd
import pickle
import re

app = Flask(__name__)
app.config['JSON_SORT_KEYS'] = False

model = pickle.load(open("apriori.pkl", "rb"))
output = list(model)
pd.set_option('max_colwidth', 1000)
Result=pd.DataFrame(columns=['Rule'])
for item in output:
    pair = item[2]
    for i in pair:
        items = str([x for x in i[0]])
        if i[3]!=1:
            Result=Result.append({
                'Rule':str([x for x in i[0]])+ " -> " +str([x for x in i[1]])
                },ignore_index=True)
Result

def find1(item):
    out = []
    for x in range(len(Result)):
        if item in Result["Rule"].iloc[x]:
            out.append(re.sub('[^\w]', " ", Result["Rule"].iloc[x]).replace(item, "").strip())
    for x in range(len(out)): #doubles
        if out[x] != "":
            return out[x]

def find2(item1, item2):
    out = []
    for x in range(len(Result)):
        if item2 in Result["Rule"].iloc[x]:
            out.append(re.sub('[^\w]', " ", Result["Rule"].iloc[x]).replace(item1, "").replace(item2, "").strip())
    for x in range(len(out)):
        if out[x] != "":
            return out[x]

@app.route('/')
def main():
    return 'Hallow'

data = None
@app.route('/predict', methods = ['POST'])
def predict():
    global data
    if request.method == 'POST':
        input1 = request.form['input1']
        data = find1(input1)
        return jsonify(
            {
                "status": "Success",
                "message": "Successfully making prediction",
                "data": {
                    "input1": data,
                }
            }
        )
@app.route('/predict2', methods = ['POST'])
def predict1():
    if request.method == 'POST':
        input1 = request.form['input1']
        data1 = find2(data, input1)
        return jsonify(
            {
                "status": "Success",
                "message": "Successfully making prediction",
                "data": {
                    "input1": data1,
                }
            }
        )


if __name__ == '__main__':
    app.run(debug=False, host='0.0.0.0')