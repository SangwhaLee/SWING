# 노드로 빌드
FROM node:18.2.0-alpine as builder

# 작업 경로 설정
WORKDIR /app

# 패키지 받아오기 
COPY package.json yarn.lock ./
RUN yarn install --production-false
COPY . .

# 환경 변수 설정
ENV PATH /app/node_modules/.bin:$PATH

# 빌드
RUN yarn build

# -----

FROM nginx

# 작업 경로 생성 후 설정
RUN mkdir /app
WORKDIR /app

# NginX 실행
RUN rm /etc/nginx/conf.d/default.conf
COPY ./nginx.conf /etc/nginx/conf.d
COPY --from=builder /app/build /app/build

# 3000번 포트 (사용?)
EXPOSE 3000

# NginX로 돌아가게 설정
CMD ["nginx", "-g", "daemon off;"]