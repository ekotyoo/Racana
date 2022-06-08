FROM node:16.15-alpine

WORKDIR /app
COPY package*.json .

RUN ["npm", "install", "--production"]

COPY . .

CMD ["node", "index.js"]

