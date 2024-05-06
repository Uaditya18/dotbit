#Importing modules

import nltk
nltk.download('punkt')
nltk.download('wordnet')
from nltk.stem import WordNetLemmatizer
lemmatizer = WordNetLemmatizer()
import json
import pickle

import random
import numpy as np

import tensorflow as tf
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense
from tensorflow.keras.layers import Dropout


#initializeLists
words = []
classes = []
documents = []
ignore_words=['?','!','@','$']

#use json
data_file = open('intents.json').read()
intents = json.loads(data_file)

#populatingTheList
for intent in intents['intents']:
    for pattern in intent['patterns']:
        
        #takeEachWordAndTokenizeIt
        w = nltk.word_tokenize(pattern)
        words.extend(w)
        
        #addingDocuments
        documents.append((w,intent['tag']))
        
        #addingClassesToOurClassList
        if intent['tag'] not in classes:
            classes.append(intent['tag'])
            
words = [lemmatizer.lemmatize(w.lower())for w in words if w not in ignore_words]
word = sorted(list(set(words)))

classes = sorted(list(set(classes)))
"""
print(len(documents),"Documents: ",documents)

print(len(classes),"Classes: ",classes)

print(len(words), "Unique Lemmatized words: ",words)
"""
      
pickle.dump(words, open('words.pkl','wb'))
pickle.dump(classes, open('classes.pkl','wb'));
        
training = []
output_empty = [0]*len(classes)


for doc in documents:
    #initializing the bag of word
    bag = []
    #list of tokenized words for the pattern
    pattern_words = doc[0]
    pattern_words = [lemmatizer.lemmatize(word.lower()) for word in pattern_words]
    
    for w in words:
        bag.append(1) if w in pattern_words else bag.append(0)
        
        
    output_row = list(output_empty)
    output_row[classes.index(doc[1])] = 1

    training.append([bag,output_row])
    
print("training: ", training)

random.shuffle(training)
training = np.array(training, dtype=object)

train_x = list(training[:,0])
train_y = list(training[:,1])

print("training data created")
print("train_x", train_x)
print("train_y", train_y)

model = Sequential()
model.add(Dense(128, input_shape = (len(train_x[0]),), activation = 'relu'))
model.add(Dropout(0.5))
model.add(Dense(64, activation = 'relu'))
model.add(Dropout(0.5))
model.add(Dense(len(train_y[0]), activation = 'softmax'))

sgd = tf.keras.optimizers.SGD(learning_rate=0.01, weight_decay=1e-6, momentum=0.9, nesterov=True)
model.compile(loss = 'categorical_crossentropy', optimizer=sgd, metrics=['accuracy'])

hist = model.fit(np.array(train_x), np.array(train_y), epochs=200, batch_size=5, verbose=1)
model.save('chatbot_model.h5',hist)

print('model created')














        
