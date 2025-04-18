FROM node:23-alpine AS build

WORKDIR /app

COPY ../../frontend/package*.json ./
COPY ../../frontend/config .
RUN npm install

COPY ../../frontend .
RUN npm run build

FROM nginx:alpine

COPY --from=build /app/build /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]

