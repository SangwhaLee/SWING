from django.urls import path
from . import views


urlpatterns = [
    # path('temp/', views.temp),
    path('test', views.test),
    path('sentency/check', views.sentency),
    path('five/check', views.five),
    path('doodle/check', views.doodle),
]
