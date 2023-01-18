# course-docker-kubernetes
Developed course from udemy about Docker and Kubernetes with Spring Boot

## Tech Stack

Spring Boot 3, Docker, Kubernetes, Mysql, Postgres


## Useful command lines

```bash
  #generate jar file
  mvn clean package
```
```bash
  #create docker image with tag
  docker image -t image_name
```
```bash
  #run docker image with tag and port:port (internal:external)
  docker run -p 8001:8001 image_tag_or_id
```
```bash
  #list docker container running
  docker ps
```
```bash
  #stop a docker container
  docker stop container_name
```
```bash
  #build docker image in specific path
  docker build -t users . -f .\ms-users\Dockerfile
```
