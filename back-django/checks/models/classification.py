import os
import cv2
import numpy as np
from PIL import Image, ImageChops
from tensorflow.keras.models import load_model
import tensorflow as tf

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

NOW_DIR = os.path.dirname(os.path.realpath(__file__)) + '/'

def crop(im_np):
    im = Image.fromarray(im_np)
    im_np = np.array(im)
    # area = im_np.shape[0] * im_np.shape[1]
    background = Image.new(im.mode, im.size, im.getpixel((0, 0)))
    diff = ImageChops.difference(im, background)
    diff = ImageChops.add(diff, diff)
    bbox = diff.getbbox()
    offset = 40
    bbox_new = [
        max(bbox[0]-offset, 0), 
        max(bbox[1]-offset, 0), 
        min(bbox[2]+offset*2, im.size[0]),
        min(bbox[3]+offset*2, im.size[1])]
    # if bbox and (area//2.5 > (bbox[2] * bbox[3])):
    return np.array(im.crop(bbox_new)).astype('float32')
    # else:
    #     return np.array(im).astype('float32')

model1 = load_model(NOW_DIR + 'weights/cnn/quick_draw_mobilenet_100.h5', compile=False)
model2 = load_model(NOW_DIR + 'weights/cnn/quick_draw_mobilenet_200.h5', compile=False)

model1.compile(optimizer='adam',
                  loss=tf.keras.losses.SparseCategoricalCrossentropy(),
                  metrics=['accuracy'])
model2.compile(optimizer='adam',
                  loss=tf.keras.losses.SparseCategoricalCrossentropy(),
                  metrics=['accuracy'])
models = [model1, model2]

class1_name = NOW_DIR + 'weights/cnn/quick_draw_mobilenet_100_classes.txt'
class2_name = NOW_DIR + 'weights/cnn/quick_draw_mobilenet_200_classes.txt'
class_names = []
for c in [class1_name, class2_name]:
    temp = []
    with open(c, 'r') as f:
        while True:
            text = f.readline()
            if not text:
                break
            else:
                text = text[:-1]
                temp.append(text)
        f.close()
    class_names.append(temp)

kernel = np.ones((3, 3), np.uint8)
def get_class(image, suggested):
    if type(image) == str:
        image = cv2.imread(image, cv2.IMREAD_GRAYSCALE)
    else:
        image = cv2.imdecode(np.fromstring(image.read(), np.uint8), cv2.IMREAD_GRAYSCALE)
    image = crop(image)
    image = cv2.erode(image, kernel, iterations=5)
    image = cv2.resize(image, (32, 32), cv2.INTER_AREA)
    image = np.array(image).astype('float')
    image = 255 - image
    image /= 255.0
    predictions = np.array([model.predict(np.expand_dims(image, axis=0)) for model in models])
    predictions = np.reshape(predictions, (200,))
    
    
    # 예측값 기준 내림차순 정렬
    predictions_sorted = np.argsort(predictions, axis=0)[::-1]
    idx = 0
    while True:
        now_class = predictions_sorted[idx]
        # 첫 번째 모델
        if now_class < 100: 
            class_name = class_names[0][now_class]
        else:
        # 두 번째 모델
            class_name = class_names[1][now_class-100]
        if class_name not in suggested:
            break
        else:
            idx += 1
    
    return class_name


# 서버가 켜질 때 model을 한 번 구동하여 api 요청 시 바로 값을 return하도록 만듦
temp = get_class(NOW_DIR + 'weights/cnn/umbrella.png', [])
del temp

# if __name__ == '__main__':
#     print(get_class('umbrella.png'))