import numpy as np
import pandas as pd
import FPgrowth
import os
import time

min_support = 0

dir = os.getcwd()
dataset = []
num_of_trans = 0

"""Xu ly du lieu raw"""
"""Sau khi xu ly co dang [[1, 2, 3, 4],
                          [5, 6, 7, 8].
                          [9, 10, 11, 12]]"""
def read_data(input_file):
    data = []
    text = open(os.path.join(dir, input_file)).read().split("\n")
    for line in text:
        line = line.split(" ")
        line.remove("")
        if line is not None:
            data.append(line)

    return data

dataset = read_data("chess.dat")
num_of_trans = len(dataset)

#Chuyen du lieu tu mang 2 chieu sang DataFrame
from mlxtend.preprocessing import TransactionEncoder

te = TransactionEncoder()
te_ary = te.fit(dataset).transform(dataset)
df = pd.DataFrame(te_ary, columns=te.columns_)
df = df.transpose()

#sap xep giam dan theo tan so cac item
df['count'] = df.sum(axis=1)
df_sort = df.sort_values(by = 'count', ascending=False)

#data = read_data("chess.dat")
data = [['Milk', 'Onion', 'Nutmeg', 'Kidney Beans', 'Eggs', 'Yogurt'],
        ['Dill', 'Onion', 'Nutmeg', 'Kidney Beans', 'Eggs', 'Yogurt'],
        ['Milk', 'Apple', 'Kidney Beans', 'Eggs'],
        ['Milk', 'Unicorn', 'Corn', 'Kidney Beans', 'Yogurt'],
        ['Corn', 'Onion', 'Onion', 'Kidney Beans', 'Ice cream', 'Eggs']]

start_time = time.time()
patterns = FPgrowth.find_frequent_patterns(data, 0.6)
elapsed_time = time.time() - start_time
print(patterns)
print(elapsed_time)
#dict = {}



"""
#Rut trich nhung item co tan so dap ung min_support
def one_itemset(df):
    f1 = []
    for index, row in df.iterrows():
        if row['count'] >= min_support:
            f1.append(index)
    return f1

def one_itemset_dict(df):
    dict = {}
    for index, row in df.iterrows():
        if row['count'] >= min_support:
            dict.update({index : row['count']})
    return dict

f1 = one_itemset(df_sort)
dict = one_itemset_dict(df_sort)

#Sap xep theo support giam dan
def sort(list, dic):
    n = len(list)

    for i in range(n):
        for j in range(0, n-i-1):
            if dic[list[j]] < dic[list[j+1]] :
                list[j], list[j+1] = list[j+1], list[j]

    return list

#Rut trich nhung item pho bien trong tung transaction va sap xep theo thu tu
def one_itemset_transaction(data):
    for i in range(0, num_of_trans):
        data[i] = [item for item in data[i] if item in f1]

    for i in range(0, num_of_trans):
        sort(data[i], dict)

    return data

temp = dataset.copy()
dataset2 = one_itemset_transaction(temp)"""
