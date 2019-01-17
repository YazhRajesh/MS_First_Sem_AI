"""
The Decision Tree Learning algorithm adopts a greedy divide-and-conquer strategy: 
Always test the most important attribute first. 
This test divides the problem up into smaller subproblems that can then be solved recursively.
By “most important attribute,” we mean the one that makes the most difference to
 the classification of an example. That way, we hope to get to the 
correct classification with a small number of tests, meaning that all paths in the tree will 
be short and the tree as a whole will be shallow.
Entropy- Calculates the homogeneity of a sample
Information Gain- The information gain is based on the decrease in entropy 
after a dataset is split on an attribute
"""
from pprint import pprint
from sys import argv
import numpy as np
import pandas as pd


file_name = argv[1]


def same_value(s):
    """takes values of class as parameter and returns if all have same value

    parameters:
        s (list):
        list of values of class

    return:
         (boolean):
         True if pure else false
    """

    return len(set(s)) == 1


def split(a):
    """takes values of an attribute as parameter and splits it based on values

    parameters:
        a (list):
        list of values of attribute

    return:
         (dict):
         dictionary where keys are various values of attribute and value is array of position where the values occurs
    """
    return {c: (a==c).nonzero()[0] for c in np.unique(a)}


def entropy(s):
    """takes values of an attribute as parameter and returns entropy

    parameters:
        s (list):
        list of values of attribute

    return:
         ent (float):
         Entropy value of the attribute
    """
    ent = 0
    val, counts = np.unique(s, return_counts=True)
    freqs = counts.astype('float')/len(s)
    for p in freqs:
        if p != 0.0:
            ent -= p * np.log2(p)
    return ent

def information_gain(y, x):
    """takes values of an attribute as parameter and returns entropy

    parameters:
        y (list):
        list of values of class
        x (list):
        list of values of attribute

    return:
         ent (float):
         Information gain of the attribute
    """

    ent = entropy(y)

    # We split x, according to attribute values x_i
    val, counts = np.unique(x, return_counts=True)
    freqs = counts.astype('float')/len(x)

    # We calculate a weighted average of the entropy
    for p, v in zip(freqs, val):
        ent -= p * entropy(y[x == v])

    return ent

def best_split(x, y, name_attribute):
    """takes examples and values of class as parameter and returns best split of the data

    parameters:
        x (list of list):
        list of examples having various attributes
        y (list)
        list of class values of the examples
        name_attribute (list of string)
        list of names of the attributes

    return:
         (dictionary):
         key is the best split with max information gain and values is either a class or a dictionary (decision tree)
    """
    
    # If no more split is possible, just return the original set
    if same_value(y) or len(y) == 0:
        return str(y[0])

    # Get the attribute that gives the highest mutual information
    gain = np.array([information_gain(y, x_attr) for x_attr in x.T])
    attribute_selected = np.argmax(gain)

    # If  no gain at all, nothing has to be done, return the original set
    if np.all(gain < 1e-6):
        return y

    #  split using the selected attribute
    sets = split(x[:, attribute_selected])
   
    ent = {}
    for a, b in sets.items():
        
        # create subsets of data based on the split
        y_subset = y.take(b, axis=0)
        x_subset = x.take(b, axis=0)
        x_subset = np.delete(x_subset, attribute_selected, 1)
        name_attribute_subset = name_attribute[:attribute_selected] + name_attribute[attribute_selected+1:]
        #recurse on subset of data left
        ent[name_attribute[attribute_selected] + " = " + a ] = best_split(x_subset, y_subset, name_attribute_subset)
        
    return ent


#read examples from the csv file
data = np.loadtxt(open(file_name, "rb"), delimiter=",", dtype='str', converters = {3: lambda s: s.strip()})
#get first name for the attribute name
name_attribute = data.take(0,0)[:-1].tolist()
#get last column for class attribute value
y = data[...,-1][1:]
#get rest of the data for the examples
X = data[...,:-1]
X = np.delete(X,0,0)

#call best_split to train the decision tree
tree = best_split(X, y, name_attribute)

satisfied_attributes = []
for i in range(10):
    contengency = pd.crosstab(X.T[i], y)
    print('\n',name_attribute[i])
    print(contengency)
print ('\nDecision tree-\n')
pprint(tree)

