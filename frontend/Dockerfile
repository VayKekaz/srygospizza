FROM node:14-alpine AS builder
WORKDIR /app
COPY package.json ./
COPY yarn.lock ./
RUN yarn install --frozen-lockfile
COPY . .

ARG REACT_APP_SRYGOSPIZZA_BACKEND_URL
ENV REACT_APP_SRYGOSPIZZA_BACKEND_URL=$REACT_APP_SRYGOSPIZZA_BACKEND_URL

RUN yarn build

FROM nginx:1.19-alpine AS server
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder ./app/build /usr/share/nginx/html
