FROM node:16-buster

WORKDIR /firebase_project

RUN apt-get update && \
    apt-get -y install sudo && \
    curl -sL https://firebase.tools | bash

EXPOSE 4000 5000 5001 8080 8085 9000 9005 9099 9199