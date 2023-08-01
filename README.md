# SWING -  Study With Image N Game!

:rocket: SWING은 즐겁게 영어 공부하고 싶은 당신을 위해 탄생했습니다. <br>
AI와 함께 세가지 이미지 게임으로 놀면서 영어를 공부할 수 있다니 참 재밌을 것 같지 않나요? <br>
단어, 작문을 활용한 게임으로 영어 공부에 몰입하고, 혼자 하는 공부가 지겨울 땐 친구들과의 그림 대결도 즐겨보세요!
<br>

## **서비스 페이지: [SWING](https://swing.run/)**
<br>

## **서비스 소개 영상: [SWING UCC](https://drive.google.com/file/d/1GjBNeWZe1oHEl7sJUum2k0DcOztS3-TO/view?usp=share_link)**

<br>

## **프로젝트 기간**
SSAFY 2학기 특화PJT(2023.02.20 ~ 2023.04.07)

<br>

## **서비스 개요**
- `SWING`은 AI가 접목된 3가지 게임을 통해 영어와 친하지 않은 사람들도 재미있게 영어 공부를 할 수 있는 서비스입니다.

<br>

## **주요 기능**
### 게임1: **Sentency**
![Sentency](/docs/sentency.gif)
- 게임 진행 순서
  1. 게임이 시작되면 한 장의 이미지와 해당 이미지를 설명하는 한글 문장이 유저에게 주어집니다.
  2. 유저는 주어진 이미지와 문장을 참고하여 이미지를 설명하는 영어 문장을 작성합니다.
  3. 유저가 입력한 문장과 정답 문장의 `유사도가 90%` 이상일 경우 정답 처리됩니다. 
  4. 5번 연속으로 틀릴 경우 게임이 종료됩니다.
- 게임 특징
  1. 유사도 기반 정답 처리로 유저가 완벽한 문장을 만들지 못하더라도 게임을 계속 진행할 수 있도록 하여 흥미를 이어갈 수 있도록 고안했습니다.
  2. 틀리더라도 일부 단어가 정답 문장의 단어와 위치까지 일치할 경우 해당 단어는 유저에게 공개됩니다.
- 사용된 AI 기술
  1. 이미지 캡셔닝(Image Captioning): CLIP과 GPT를 활용하여 300장의 이미지에 대한 캡션을 만들어 게임에 활용했습니다.
  2. 문장 유사도: Sentence BERT pretrained 모델로 문장의 임베딩 벡터를 구하고 이를 코사인 유사도로 검사했습니다.

### 게임2: **Hi-Five**
![Hi-Five](/docs/hi-five.gif)
- 게임 진행 순서
  1. 게임이 시작되면 5장의 물체 이미지가 겹쳐진 상태로 유저에게 주어집니다. 
  2. 유저는 이미지들이 어떤 물체인지 영어로 맞힙니다.
  3. 5개의 물체를 모두 맞히면 게임이 종료됩니다.
- 게임 특징
  1. 동의어(ex. cell phone, handy)를 고려하여 `유사도가 85% 이상`인 경우 정답으로 처리합니다.
  2. 유저가 입력한 단어와 `유사도가 40% 이상 85% 미만`인 물체 이미지가 있으면 해당 이미지를 좌우로 흔들어 힌트를 줍니다.
- 사용된 AI 기술
  1. 단어 유사도: Sentence BERT pretrained 모델로 단어의 임베딩 벡터를 구하고 이를 코사인 유사도로 검사했습니다.

### 게임3: **Speedoodle**
![Speedoodle](/docs/speedoodle.gif)
- 게임 진행 순서
  1. 2명 이상 6명 이하 유저들이 게임방에 모여 게임을 시작합니다.
  2. 게임이 시작되면 모든 유저들에게 동일한 키워드가 주어지고, 유저들은 키워드를 그림으로 표현합니다.
  3. AI가 그림을 해당 키워드로 인식한 순서대로 순위가 매겨집니다.
  4. 5라운드로 진행되며, 각 라운드의 통과 시간을 종합하여 최종 순위가 매겨집니다.
- 게임 특징
  1. Easy, Hard 중 하나를 택하여 게임을 진행합니다. `Easy` 모드는 키워드가 바로 주어지고, `Hard` 모드는 키워드에 대한 영어 설명이 주어집니다.
  2. 게임 히스토리(등수, 그림)를 `히스토리 페이지`에서 확인할 수 있습니다.
- 사용된 AI 기술
  1. 이미지 분류: keras 라이브러리의 `MobileNetV2`에 `Quick Draw Dataset` 이미지를 학습시켜 분류 모델을 만들었습니다.

### 오답 노트
![Review Note](/docs/review_note.gif)
- 오답 노트에는 Sentency 게임에서 틀린 `문장`, Hi-Five에서 틀린 `단어`가 자동으로 등록되어 있습니다. 
- 각 문장과 단어의 한글, 영어 설명을 확인할 수 있습니다.
- 오답 문장과 단어로 `복습 테스트`를 진행할 수 있습니다. 또한 테스트를 통과하면 Sentency 게임에 한 번 더 참여할 수 있는 쿠폰이 주어집니다.

### 히스토리
- `Speedoodle` 게임 히스토리를 확인할 수 있습니다.
- 게임 등수와 라운드마다 유저와 유저 친구들이 그린 그림들을 볼 수 있습니다.

### **기타 기능**
  - 회원가입/로그인: 카카오톡 소셜 로그인
  - 프로필 수정: 닉네임, 프로필 사진
  - 회원정보 수정: 회원 탈퇴

<br>

## **Architecture**
![Architecture](/docs/architecture.PNG)
    
<br>

## **기술 스택**
  - 협업, 버전관리: ![img](https://camo.githubusercontent.com/a9a95986631c3d4945a63d42d2864e3918a834d672d907e174a29f743a1bc3f1/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f6769742d4630353033323f7374796c653d666f722d7468652d6261646765266c6f676f3d676974266c6f676f436f6c6f723d7768697465)![img](https://camo.githubusercontent.com/d20c06f1854face8c434a4fa2ffa62a2c6d52368120cc7dafd77166da5732caf/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4e6f74696f6e2d3030303030303f7374796c653d666f722d7468652d6261646765266c6f676f3d6e6f74696f6e266c6f676f436f6c6f723d7768697465)<img src="https://img.shields.io/badge/Jira -0052CC?style=for-the-badge&logo=Jira Software&logoColor=white"><img src="https://img.shields.io/badge/Gitlab -FC6D26?style=for-the-badge&logo=Gitlab&logoColor=white"><img src="https://img.shields.io/badge/SWAGGER-6E64C7?style=for-the-badge&logo=SWAGGER&logoColor=white">
  - 언어: ![img](https://camo.githubusercontent.com/146641825a4dcaf7d047629441f6596b8d9d7327ec8c8104ea54d3b6aa1080b3/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4a6176615363726970742d4637444631453f7374796c653d666f722d7468652d6261646765266c6f676f3d6a617661736372697074266c6f676f436f6c6f723d7768697465)![img](https://camo.githubusercontent.com/5a7100155d1a7b75357a90e8810530b21c8723c59f2976d0dafc7950205336d7/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f68746d6c352d4533344632363f7374796c653d666f722d7468652d6261646765266c6f676f3d68746d6c35266c6f676f436f6c6f723d7768697465)![img](https://camo.githubusercontent.com/395bcd1fa353e86f422e5f01abf3260b8c76720be050e5f4688ab7fc7634f50f/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4353532d3135373242363f7374796c653d666f722d7468652d6261646765266c6f676f3d63737333266c6f676f436f6c6f723d7768697465)<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=JAVA&logoColor=white"><img src="https://img.shields.io/badge/PYTHON-4E1EBF?style=for-the-badge&logo=PYTHON&logoColor=white">
  - FE/BE framework: <img src="https://img.shields.io/badge/REACT-109CA1?style=for-the-badge&logo=REACT&logoColor=white"><img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white"><img src="https://img.shields.io/badge/DJANGO-9C4716?style=for-the-badge&logo=DJANGO&logoColor=white"><img src="https://img.shields.io/badge/TENSORFLOW-F5E341?style=for-the-badge&logo=TENSORFLOW&logoColor=white"><img src="https://img.shields.io/badge/PYTORCH-DD28E4?style=for-the-badge&logo=PYTORCH&logoColor=white"> 
  - DB: <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"><img src="https://img.shields.io/badge/Amazon S3-EBAB6B?style=for-the-badge&logo=Amazon S3&logoColor=white">
  - 배포: ![img](https://camo.githubusercontent.com/c75eb74744dd435c8f353a621e97b392201c530225b32b1be88d6cd38a1b1448/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f416d617a6f6e4157532d4646393930423f7374796c653d666f722d7468652d6261646765266c6f676f3d616d617a6f6e617773266c6f676f436f6c6f723d7768697465)<img src="https://img.shields.io/badge/NGINX-009639?style=for-the-badge&logo=NGINX&logoColor=white"><img src="https://img.shields.io/badge/Docker -2496ED?style=for-the-badge&logo=docker&logoColor=white">
  - CI/CD: <img src="https://img.shields.io/badge/jenkins-7b2d00?style=for-the-badge&logo=jenkins&logoColor=white">
  - 디자인: <img src="https://img.shields.io/badge/ICONIFY-c48da4?style=for-the-badge&logo=ICONIFY&logoColor=white"><img src="https://img.shields.io/badge/REACT BOOTSTRAP ICONS-621BCD?style=for-the-badge&logo=Bootstrap&logoColor=white">

<br>

## 프로젝트 산출물
  - [요구사항 명세서](https://docs.google.com/spreadsheets/d/1s_UTZVAodNhPvDjIMDSNBT1baresso420z9M_2zhXjc/edit?usp=sharing)
  - [기능 정의서](https://docs.google.com/spreadsheets/d/1KFXJwTrRsMFKdR7upxqIdvwMql-TsgRdQ9_bpVl5UeY/edit?usp=sharing)
  - [목업](https://www.figma.com/file/D7l1yJ4uPUi6tI1Byq56VY/sub1?node-id=1%3A2&t=XOTZk9Ez7gymd0zi-1)
  - [ERD](https://drive.google.com/file/d/1DWOISAf1ZNGQNr_eQ5gnvsDK7yM6tdBy/view?usp=sharing)
  - [API 명세서](https://docs.google.com/spreadsheets/d/1FzohMrqxhmOpdn23zfUqp4oM6xWVXGCUMq-h7OiGy_M/edit?usp=sharing)
  - [UCC](https://drive.google.com/file/d/1GjBNeWZe1oHEl7sJUum2k0DcOztS3-TO/view?usp=share_link)
  - [최종 발표 자료](https://drive.google.com/file/d/1cyWOuYWc37MHR7v3mVv_rw5BmBcUkFiI/view?usp=share_link)
  - [포팅 매뉴얼](https://docs.google.com/document/d/14uNQdsXdRBT4Gpzts3enwDEhEqrjuDFZxUm6kXN5DZY/edit?usp=sharing)
  - [시연 시나리오](https://docs.google.com/document/d/1Aj5QOxrrSI84Ck7exmFprGPfqBd8_HmUdCznBLLsdA8/edit?usp=sharing)
  - [AI 개발 상세](https://docs.google.com/document/d/12zAM9SJSx91n6lDtMtWGrgzpzvFKDT2_YYW_9-_MaOY/edit?usp=sharing)
  

<br>

## 팀원 소개
![팀원소개](/docs/팀원소개.png)