FROM node:11.5.0-alpine as builder
RUN mkdir -p /web
WORKDIR /web
ADD . /web
RUN yarn install && \
	yarn run build && \
	yarn cache clean
FROM nginx:1.14.2-alpine as app
COPY --from=builder /web/dist/ /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]
