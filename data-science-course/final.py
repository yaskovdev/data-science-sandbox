import re

from collections import Counter

from scipy.spatial import distance

lines = [line for line in open('sentences.txt')]

lists_of_words_with_empty_words = [re.split('[^a-z]', line.lower()) for line in lines]

sentences = [filter(None, list_of_words) for list_of_words in lists_of_words_with_empty_words]

dicts_of_words = [Counter(sentence) for sentence in sentences]

all_words_with_repetitions = []
for sentence in sentences:
    all_words_with_repetitions.extend(sentence)

all_words = list(set(all_words_with_repetitions))

matrix_array = [[0 for x in range(len(all_words))] for y in range(len(sentences))]

for i in range(len(matrix_array)):
    sentence = sentences[i]
    for j in range(len(matrix_array[i])):
        word = all_words[j]
        matrix_array[i][j] = sentence.count(word)

distances = {}
first_sentence = matrix_array[0]
for i in range(len(matrix_array)):
    sentence = matrix_array[i]
    distances[distance.cosine(first_sentence, sentence)] = i

keys = distances.keys()
keys.sort()
first_and_second = keys[1:3]

print(str(sentences[0]))
print(str(distances[first_and_second[0]]))
print(str(distances[first_and_second[1]]))

output = open('submission-1.txt', 'w')
output.write(str(distances[first_and_second[0]]) + ' ' + str(distances[first_and_second[1]]))
