### **Docker Commands Cheat Sheet**

#### **Container Lifecycle**

- **Run a container**  
    `docker run [OPTIONS] IMAGE [COMMAND] [ARG...]`
    ```shell
    docker run <image-name>
    ```
    
    ```bash
    docker run -d -p 80:80 --name my-container nginx
    ```
    
- **List running containers**  
    `docker ps`
    
    ```bash
    docker ps
    ```
    
- **List all containers (including stopped)**  
    `docker ps -a`
    
    ```bash
    docker ps -a
    ```
    
- **Stop a running container**  
    `docker stop <container_name_or_id>`
    
    ```bash
    docker stop my-container
    ```
    
- **Start a stopped container**  
    `docker start <container_name_or_id>`
    
    ```bash
    docker start my-container
    ```
    
- **Restart a container**  
    `docker restart <container_name_or_id>`
    
    ```bash
    docker restart my-container
    ```
    
- **Remove a container**  
    `docker rm <container_name_or_id>`
    
    ```bash
    docker rm my-container
    ```
    
- **Execute a command in a running container**  
    `docker exec -it <container_name_or_id> <command>`
    
    ```bash
    docker exec -it my-container bash
    ```
    

#### **Image Management**

- **List all Docker images**  
    `docker images`
    
    ```bash
    docker images
    ```
    
- **Pull an image from Docker Hub**  
    `docker pull <image_name>`
    
    ```bash
    docker pull nginx
    ```
    
- **Remove a Docker image**  
    `docker rmi <image_name_or_id>`
    
    ```bash
    docker rmi nginx
    ```
    
- **Build an image from a Dockerfile**  
    `docker build -t <image_name> <path>`
    
    ```bash
    docker build -t my-image .
    ```
    

#### **Logs**

- **View container logs**  
    `docker logs <container_name_or_id>`
    
    ```bash
    docker logs my-container
    ```
    
- **View real-time logs**  
    `docker logs -f <container_name_or_id>`
    
    ```bash
    docker logs -f my-container
    ```
    

#### **Networking**

- **List Docker networks**  
    `docker network ls`
    
    ```bash
    docker network ls
    ```
    
- **Create a new network**  
    `docker network create <network_name>`
    
    ```bash
    docker network create my-network
    ```
    
- **Connect a container to a network**  
    `docker network connect <network_name> <container_name_or_id>`
    
    ```bash
    docker network connect my-network my-container
    ```
    
- **Disconnect a container from a network**  
    `docker network disconnect <network_name> <container_name_or_id>`
    
    ```bash
    docker network disconnect my-network my-container
    ```
    

#### **Volumes (Persistent Storage)**

- **List Docker volumes**  
    `docker volume ls`
    
    ```bash
    docker volume ls
    ```
    
- **Create a volume**  
    `docker volume create <volume_name>`
    Here's a simplified version of the Docker commands with short descriptions, formatted for easy copy-paste:
    ```bash
    docker volume create my-volume
    ```
    
- **Remove a volume**  
    `docker volume rm <volume_name>`
    
    ```bash
    docker volume rm my-volume
    ```
    
- **Inspect a volume**  
    `docker volume inspect <volume_name>`
    
    ```bash
    docker volume inspect my-volume
    ```
    
Here's a simplified version of the Docker commands with short descriptions, formatted for easy copy-paste:
#### **Docker Compose**

- **Start services defined in `docker-compose.yml`**  
    `docker-compose up`
    
    ```bash
    docker-compose up
    ```
    
- **Run Docker Compose in detached mode**  
    `docker-compose up -d`
    
    ```bash
    docker-compose up -d
    ```
    
- **Stop services defined in `docker-compose.yml`**  
    `docker-compose down`
    
    ```bash
    docker-compose down
    ```
    
- **View logs from services**  
    `docker-compose logs`
    
    ```bash
    docker-compose logs
    ```
    

#### **System Cleanup**

- **Remove unused containers, images, volumes, and networks**  
    `docker system prune`
    
Here's a simplified version of the Docker commands with short descriptions, formatted for easy copy-paste:    ```bash
    docker system prune
    ```
    
- **Remove all stopped containers, unused networks, and dangling images**  
    `docker system prune -a`
    
    ```bash
    docker system prune -a
    ```
    

#### **System Info**

- **View Docker system information**  
    `docker info`
    
    ```bash
    docker info
    ```
    
- **View Docker version**  
    `docker version`
    
    ```bash
    docker version
    ```
    

#### **Container Health Checks**

- **Run a container with a health check (add to Dockerfile)**  
    `HEALTHCHECK --interval=5m --timeout=3s --retries=3 Here's a simplified version of the Docker commands with short descriptions, formatted for easy copy-paste:CMD <check_command>`
    
    ```Dockerfile
    HEALTHCHECK --interval=5m --timeout=3s --retries=3 CMD curl --fail http://localhost:8080/health || exit 1
    ```
    

#### **Useful Docker Options**

- **Run a container interactively**  
    `docker run -it <image> <command>`
    
    ```bash
    docker run -it ubuntu bash
    ```
    
- **List all Docker processes (including stopped)**  
    `docker ps -a`
    
    ```bash
    docker ps -a
    ```
    
- **Get a shell inside a running container**  
    `docker exec -it <container_name> /bin/bash`
    
    ```bash
    docker exec -it my-container /bin/bash
    ```
    
