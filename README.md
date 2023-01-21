# course-docker-kubernetes
Developed course from udemy about Docker and Kubernetes with Spring Boot

## Tech Stack

Spring Boot 3, Docker, Kubernetes, Mysql, Postgres


## Useful docker command lines

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
  #run detached docker image with tag and port:port (internal:external)
  docker run -d -p 8001:8001 image_tag_or_id
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
```bash
  #run an existing container detach automatically
  docker start container_id
```
```bash
  #run an existing attach container
  docker start -a container_id
```
```bash
  #attach a runing container
  docker attach CONTAINER_ID
```
```bash
  #log for runing container 
  docker logs CONTAINER_ID
```
```bash
  #log attached for runing container 
  docker logs -f CONTAINER_ID
```
```bash
  #delete docker container (must be stopped)
  #to delete multiple container, separate name by space
  docker rm container_name_or_id
```
```bash
  #delete docker containers with 'Exited' status
  docker container prune
```
```bash
  #delete docker image, add space to delete multiple (can delete image not being used)
  docker rmi image_id
```
```bash
  #delete all docker images not being used
  docker image prune
```
```bash
  #delete all docker images not being used
  docker image prune
```
```bash
  #run docker image in interactive mode (use CMD instead ENTRYPOINT in dockerfile)
  docker run -p 8001:8001 -it image_name /bin/sh
```
```bash
  #copy files from local machine to container
  docker cp .\File.ext CONTAINER_ID:/path/File.ext
  
  #Example
  docker cp .\Login.java CONTAINER_ID:/app/Login.java
```
```bash
  #copy files from from container to local machine
  docker cp CONTAINER_ID:/path/File.ext .\File.ext 
  
  #Example
  docker cp CONTAINER_ID:/app/Login.java .\Login.java
```
```bash
  #copy folder container to local machine
  docker cp CONTAINER_ID:/folder .\folder
```
```bash
  #inspect docker image to see details
  docker image inspect IMAGE_NAME_OR_ID
```
```bash
  #inspect docker container to see details
  docker container inspect IMAGE_NAME_OR_ID
```
```bash
  #build docker image with name and tag (tag is like version)
  docker build -t users:v2 . -f .\ms-users\Dockerfile
```
```bash
  #run docker image with specific version (--rm delete container after stop container)
  docker run -p 8001:8001 --rm -d --name CONTAINER_NAME users:v2
```