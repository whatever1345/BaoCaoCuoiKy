import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

import os


min_support = 0

dir = os.getcwd()
dataset = []
num_of_trans = 0

"""Xu ly du lieu raw"""
"""Sau khi xu ly xuat ra du lieu co dang [[1, 2, 3, 4],
                                          [5, 6, 7, 8].
                                          [9, 10, 11, 12]]"""
def read_data(input_file):
    data = []
    text = open(input_file).read().split("\n")
    for line in text:
        line = line.split(" ")
        line.remove("")
        if line is not None:
            data.append(line)

    return data

dataset = read_data("D:\\Project\\BaoCaoCuoiky\\chess.dat")
num_of_trans = len(dataset)

#Chuyen du lieu tu mang 2 chieu sang DataFrame
from mlxtend.preprocessing import TransactionEncoder

te = TransactionEncoder()
te_ary = te.fit(dataset).transform(dataset)
df = pd.DataFrame(te_ary, columns=te.columns_)
df = df.transpose()

#sap xep giam dan theo tan so cac item
df['count'] = df.sum(axis=1)
print(df)
