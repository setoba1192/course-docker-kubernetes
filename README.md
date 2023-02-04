# course-docker-kubernetes
Developed course from udemy about Docker and Kubernetes with Spring Boot

## Tech Stack

Spring Boot 3, Docker, Kubernetes, Mysql, Postgres, Minikube, AWS


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
  #force delete all docker images not being used
  docker image prune -a
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
```bash
  #create an internal network
  docker network create NETWORK_NAME
```
```bash
  #list networks
  docker network ls
```
```bash
  #run a docker image with specific external:internal port with container name and specific network
  docker run -p 8001:8001 -d --rm --name ms-users --network spring users
```
```bash
  #run docker mysql image 
  docker run -d -p 3307:3306 --name mysql8 --network spring -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=ms_users mysql:5
```
```bash
  #run docker postgres image 
  docker run -p 5433:5432 --name postgres13 --network spring -e POSTGRES_PASSWORD=1234 -e POSTGRES_DB=ms_courses -d postgres:14-alpine
```
```bash
  #run docker mysql with a specific volume path to persist database after delete a container (--restart=always is for start a container when docker start for the first time)
  docker run -d -p 3307:3306 --name mysql8 --network spring -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=ms_users -v data-mysql:/var/lib/mysql --restart=always mysql:5
```
```bash
  #run docker postgres with a specific volume path to persist database after delete a container (--restart=always is for start a container when docker start for the first time)
  docker run -p 5433:5432 --name postgres13 --network spring -e POSTGRES_PASSWORD=1234 -e POSTGRES_DB=ms_courses -d -v data-postgresql:/var/lib/postgresql --restart=always postgres:14-alpine
```
```bash
  #run docker image with mysql interactive mode to use bash (Only for utility purpose)
  docker run -it --rm --network spring mysql:8 bash
```
```bash
  #run docker image with postgres interactive mode to use bash (Only for utility purpose)
  docker run -it --rm --network spring postgres:14-alpine psql -h postgres13 -U postgres
```
```bash
  #run docker image with overwriting an environment variable from Dockerfile
  docker run -p 8001:8090 --env PORT=8090 -d --rm --name ms-users --network spring  users
```
```bash
  #run docker image with overwriting an environment variable from Dockerfile
  docker run -p 8001:8090 --env PORT=8090 -d --rm --name ms-users --network spring  users
```
```bash
  #run docker image with overwriting environments Dockerfile  variable from .env file
  docker run -p 8001:8001 --env-file .\ms-users\.env -d --rm --name ms-users --network spring  users
```
```bash
  #run docker image overwriting args in Dockerfile
  docker build -t users . -f .\ms-users\Dockerfile --build-arg PORT_APP=8080
```
```bash
  #create image copy from existing
  docker tag course-kubernetes-ms-users setoba06/users
```
```bash
  #push docker image in hub.docker
  docker push userName/imageName
```
```bash
  #pull docker image in hub.docker
  docker pull userName/imageName
```

## Useful docker-compose command lines
```bash
  #run docker-compose.yaml defined in current folder
  docker-compose up -d
```
```bash
  #force build images and run docker-compose.yaml defined in current folder
  docker-compose up --build -d
```
```bash
  #stop and delete containers. -v to delete volumes
  docker-compose down -v
```

## Install docker in linux amazon
```bash
   sudo yum -y update
   sudo amazon-linux-extras install docker
   sudo service docker start
```

## Install docker compose in linux amazon
```bash
   sudo curl -L https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose

   sudo chmod +x /usr/local/bin/docker-compose

   docker-compose version  

   #global mode
   sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
```
```bash
  #copy file form local machine to ec2 instance
  scp -i "key.pem" filename ec2-user@ec2-44-211-139-45.compute-1.amazonaws.com:/home/ec2-user
```

## Useful commands to work with Kubernetes

```bash
  #start minikube in windows usen hyperv
  minikube start --driver=hyperv
```
```bash
  #create a deployment in imperative way
  kubectl create deployment ms-users --image=setoba06/users:1.1 --port=8001
```
```bash
  #create a yaml to create deployment in declarative way #--dry-run=client : to avoid create deployment
  kubectl create deployment mysql8 --image=mysql:8 --port=3306 --dry-run=client -o yaml > deployment-mysql.yaml
  kubectl create deploy ms-users --port=8001 --image=setoba06/users:1.2 --dry-run=client -o yaml > deployment-users.yaml
```
```bash
  #create a deployment from yaml file
  kubectl apply -f .\deployment-mysql.yaml
```
```bash
  #list all deployments
  kubectl get deploy
```
```bash
  #delete a deployment
  kubectl delete deployment deploymentName
```
```bash
  #describe a pod
  kubectl describe pod mysql8-86b85b545c-zgspp
```
```bash
  #log pod
  kubectl logs  mysql8-86b85b545c-zgspp
```
```bash
  #expose a deployment by port in an internal cluster
  kubectl expose deployment mysql8 --port=3306 --type=ClusterIP
```
```bash
  #expose a deployment by port in an internal cluster LoadBalancer (Balance external)
  kubectl expose deployment ms-users --port=8001 --type=LoadBalancer
```
```bash
  #list services
  kubectl get svc
```
```bash
  #get information from service
  kubectl describe service mysql8
```
```bash
  #get cluster public ip
  minikube service ms-users --url
```
```bash
  #update image in container (#users: container-name, #ms-users: service-name), image must have different version replacing latest by specific version
  kubectl set image deployment ms-users users=setoba06/users:latest
```
```bash
  #scale replicas in deployment
  kubectl scale deployment ms-users --replicas=3
```
```bash
  #print yaml file of service 
  kubectl get service mysql8 -o yaml
```