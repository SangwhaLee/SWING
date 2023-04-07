from rest_framework.response import Response
from rest_framework.decorators import api_view
from .models.sentensim import sentence_similarity, word_similarity
from .models.classification import get_class


@api_view(['GET'])
def test(request):
    return Response('success')

@api_view(['GET'])
def sentency(request):
    # 제시 단어
    solution = request.GET.get('solution')
    # 유저 입력 단어
    answer = request.GET.get('answer')

    # 유사도 체크
    similarity = sentence_similarity(solution, answer)
    message = 'success'

    context = {
        'similarity': similarity,
        'message': message,
    }
    return Response(context)


@api_view(['GET'])
def five(request):
    # 제시 단어
    solutions = request.GET.getlist('solution')
    # 유저 입력 단어
    answer = request.GET.get('answer')

    # 유사도 체크
    similarity = word_similarity(solutions, answer)
    message = 'success'

    context = {
        'similarity': similarity,
        'message': message,
    }
    return Response(context)


@api_view(['POST'])
def doodle(request):
    # 유저 그림
    answer = request.FILES['answer']
    answered = request.POST['answered']
    result = get_class(answer, answered)

    message = 'success'

    context = {
        'class': result,
        'message': message,
    }
    return Response(context)