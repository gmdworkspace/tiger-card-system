FROM openjdk:11-jdk

WORKDIR /source

COPY ./ ./

ARG buildTask="clean build"

RUN ./gradlew $buildTask