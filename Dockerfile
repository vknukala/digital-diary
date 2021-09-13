# Filename: Dockerfile
#sets the base image from which the Docker container will be created.
ARG DOCKER_BASE_IMAGE=openjdk:11-jre-slim
FROM $DOCKER_BASE_IMAGE
MAINTAINER vknukala
ARG target_jar=target/*.jar
COPY ${target_jar} digital-diary-docker.jar
ENTRYPOINT ["java","-jar","/digital-diary-docker.jar"]
