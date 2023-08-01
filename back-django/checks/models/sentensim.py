# import os
from sentence_transformers import SentenceTransformer, util

# NOW_DIR = os.path.dirname(os.path.realpath(__file__)) + '/'

model = SentenceTransformer('all-mpnet-base-v2')
# model = SentenceTransformer(NOW_DIR + 'weights/sbert')

def sentence_similarity(s1, s2):
    embeddings1 = model.encode(s1, convert_to_tensor=True)
    embeddings2 = model.encode(s2, convert_to_tensor=True)
    # 코사인 유사도
    cosine_scores = util.cos_sim(embeddings1, embeddings2)
    if cosine_scores[0][0].item() < 0:
        return 0
    else:
        return round(cosine_scores[0][0].item(), 2)


def word_similarity(answers, user_word):
    similarities = {}
    user_word_eb = model.encode(user_word, convert_to_tensor=True)
    for answer in answers:
        answer_eb = model.encode(answer, convert_to_tensor=True)
        cosine_scores = util.cos_sim(user_word_eb, answer_eb)
        if cosine_scores[0][0].item() < 0:
            similarities.update({answer: 0}) 
        else:
            similarities.update({answer: round(cosine_scores[0][0].item(), 2)}) 
    
    return similarities

# test
if __name__ == '__main__':
    s1 = 'I love you'
    s2 = 'I like you'
    print(sentence_similarity(s1, s2))