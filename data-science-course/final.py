import re

from collections import Counter

from scipy.spatial import distance

raw_sentences = [sentence.lower() for sentence in open('sentences.txt')]

sentences_with_empty_words = [re.split('[^a-z]', sentence) for sentence in raw_sentences]

sentences = [filter(None, sentence) for sentence in sentences_with_empty_words]

dicts_of_words = [Counter(sentence) for sentence in sentences]

all_words_with_repetitions = []
for sentence in sentences:
    all_words_with_repetitions.extend(sentence)

all_words = list(set(all_words_with_repetitions))

matrix = [[sentences[i].count(all_words[j]) for j in range(len(all_words))] for i in range(len(sentences))]

distances = {}
first_sentence = matrix[0]
for i in range(len(matrix)):
    sentence = matrix[i]
    distances[distance.cosine(first_sentence, sentence)] = i

closest = sorted(distances.keys())[1:3]

output = open('submission-1.txt', 'w')
output.write(str(distances[closest[0]]) + ' ' + str(distances[closest[1]]))
