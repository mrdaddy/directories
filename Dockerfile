FROM openjdk:8
COPY ./com/rw/directories/DirectoriesApplication /tmp
WORKDIR /tmp
ENTRYPOINT ["java","Main"]