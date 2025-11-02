Here’s your **categorized and structured Docker command reference**:

---

## **1. Container Management**

```sh
docker run <IMAGE-NAME> 
# Runs a container from the specified image using its default command.

docker run -d <IMAGE-NAME>
# Runs the container in detached mode (in the background).

docker run -it <IMAGE-NAME>
# Runs an interactive container with an attached terminal.

docker run -it <IMAGE-NAME> bash
# Runs an interactive container and starts a Bash shell inside it.

docker run <container-id> <command>
# Runs a specific command inside a container.

docker stop <CONTAINER-ID>
# Stops a running container.

docker stop -t 5 <CONTAINER-ID>
# Stops a container, waiting 5 seconds before forcefully stopping.

docker start <CONTAINER-ID or CONTAINER-NAME>
# Starts a stopped container.

docker start -a <CONTAINER-ID>
# Starts the container and attaches to its output.

docker start -i <CONTAINER-ID>
# Starts the container in interactive mode.

docker rm <CONTAINER-ID or CONTAINER-NAME>
# Removes a stopped container.

docker rm -f <CONTAINER-ID>
# Forcefully removes a running or stopped container.

docker container prune
# Removes all stopped containers (asks for confirmation).

docker container prune -f
# Forcefully removes all stopped containers without confirmation.
```

---

## **2. Image Management**

```sh
docker images
# Lists all available Docker images.

docker pull <IMAGE-NAME>
# Pulls the latest version of an image from the registry.

docker pull <IMAGE-NAME>:<VERSION-TAG>
# Pulls a specific version of an image using a tag.

docker rmi <IMAGE-ID>
# Removes an image by its ID.

docker rmi <IMAGE-ID or IMAGE-NAME>
# Removes an image by its ID or name.

docker rmi -f <IMAGE-ID>
# Forcefully removes an image even if it is in use.

docker commit <CONTAINER-ID> <NEW-IMAGE-NAME>:<TAG>
# Creates a new image from an existing container.

docker commit --author "Meghsham Kapure" --message "Updated Config" abc123 my-custom-image:v1
# Saves changes in a running container as a new image.
```

---

## **3. Container and Image Inspection**

```sh
docker ps
# Lists currently running containers.

docker ps -a
# Lists all containers (running and stopped).

docker inspect <IMAGE-ID or CONTAINER-ID>
# Displays detailed information about an image or container.
```

---

## **4. Logging and Monitoring**

```sh
docker logs <CONTAINER-ID>
# Fetches logs from a running or stopped container.

docker logs --since 5s <CONTAINER-ID>
# Fetches logs from the last 5 seconds.
```

---

## **5. Executing Commands in Running Containers**

```sh
docker exec -it <CONTAINER-ID or CONTAINER-NAME> bash
# Runs an interactive shell inside a running container.

docker run ubuntu echo hey
# Runs an Ubuntu container and executes `echo hey`.

docker run --rm <IMAGE-NAME> <COMMAND>
# Runs a container temporarily and removes it after execution.
```

---

## **6. Networking and Port Mapping**

```sh
docker run -d -p <HOST-PORT>:<DOCKER-PORT> <IMAGE-NAME>
# Runs a container in detached mode and maps a port from host to container.

docker run --rm <IMAGE-NAME> ping <ADDRESS>
# Runs a container and pings an address to test network connectivity.
```

---

This structured categorization helps in **quick reference and better understanding**. 🚀 Let me know if you need any modifications or additions!


-----



### **Categories of Docker Commands**

Docker commands can be categorized based on their functionality. Below is a well-structured breakdown:

---

## **1. Container Lifecycle Management**

Commands to create, start, stop, remove, and manage containers.

```sh
docker run <IMAGE-NAME>          # Create and start a container
docker start <CONTAINER-ID>       # Start a stopped container
docker stop <CONTAINER-ID>        # Stop a running container
docker restart <CONTAINER-ID>     # Restart a container
docker kill <CONTAINER-ID>        # Force stop a container immediately
docker rm <CONTAINER-ID>          # Remove a stopped container
docker container prune            # Remove all stopped containers
```

---

## **2. Image Management**

Commands to pull, remove, inspect, and manage images.

```sh
docker images                     # List all available images with details
docker images < REPSITORY>         # Gives details of specified repository
docker images -q                   # List all available images id
docker pull <IMAGE-NAME>           # Download an image from Docker Hub
docker pull <IMAGE-NAME>:<TAG>     # Pull a specific image version
docker rmi <IMAGE-ID>              # Remove an image
docker inspect <IMAGE-ID>          # Get detailed information about an image
docker commit <CONTAINER-ID> <NEW-IMAGE-NAME>:<TAG>  
# Create a new image from a modified container
```

---

## **3. Container Inspection & Monitoring**

Commands to list, inspect, and monitor running and stopped containers.

```sh
docker ps                          # List running containers
docker ps -a                       # List all containers, including stopped ones
docker inspect <CONTAINER-ID>       # Get detailed container information
docker logs <CONTAINER-ID>          # View logs of a container
docker stats                        # Display real-time container resource usage
docker top <CONTAINER-ID>           # Show processes running inside a container
docker events                       # Get real-time events from the Docker daemon
```

---

## **4. Container Execution & Interaction**

Commands to execute commands inside a running container.

```sh
docker exec -it <CONTAINER-ID> bash  # Start an interactive bash shell in a running container
docker exec -it <CONTAINER-ID> <COMMAND>  # Run a command inside a running container
docker attach <CONTAINER-ID>          # Attach to a running container’s console
docker run --rm <IMAGE-NAME> <COMMAND>  
# Run a temporary container and remove it after execution
```

---

## **5. Networking & Port Mapping**

Commands to configure container networking.

```sh
docker network ls                    # List all networks
docker network inspect <NETWORK-NAME> # Get network details
docker network create <NETWORK-NAME>  # Create a custom network
docker network connect <NETWORK-NAME> <CONTAINER-ID>  
# Connect a container to a network
docker network disconnect <NETWORK-NAME> <CONTAINER-ID>  
# Disconnect a container from a network
docker run -d -p <HOST-PORT>:<DOCKER-PORT> <IMAGE-NAME>  
# Run a container with port mapping
```

---

## **6. Volume & Storage Management**

Commands to manage persistent data in Docker.

```sh
docker volume ls                      # List all volumes
docker volume create <VOLUME-NAME>     # Create a new volume
docker volume inspect <VOLUME-NAME>    # Get volume details
docker volume rm <VOLUME-NAME>         # Remove a volume
docker volume prune                    # Remove all unused volumes
docker run -v <VOLUME-NAME>:/path <IMAGE-NAME>  
# Attach a volume to a container
```

---

## **7. Docker Compose**

Commands for managing multi-container applications.

```sh
docker-compose up                      # Start services defined in `docker-compose.yml`
docker-compose up -d                    # Start services in detached mode
docker-compose down                     # Stop and remove services
docker-compose ps                       # List services
docker-compose logs                      # View logs of all services
docker-compose restart <SERVICE-NAME>    # Restart a specific service
```

---

## **8. Build & Deployment**

Commands to build custom images and manage deployments.

```sh
docker build -t <IMAGE-NAME>:<TAG> .     # Build an image from a Dockerfile
docker push <IMAGE-NAME>:<TAG>           # Push an image to Docker Hub
docker tag <IMAGE-NAME>:<TAG> <NEW-NAME>:<NEW-TAG>  
# Tag an image with a new name
docker save -o <FILENAME>.tar <IMAGE-NAME>  
# Save an image to a tar file
docker load -i <FILENAME>.tar            # Load an image from a tar file
```

---

## **9. Security & User Management**

Commands for security and access control.

```sh
docker login                             # Authenticate with Docker Hub
docker logout                            # Log out from Docker Hub
docker scan <IMAGE-NAME>                 # Scan an image for vulnerabilities
docker history <IMAGE-NAME>              # Show image history and layers
docker update --memory=512M <CONTAINER-ID>  
# Update resource limits for a running container
```

---

## **10. System & Cleanup**

Commands to manage Docker's system resources.

```sh
docker system df                         # Show disk usage by Docker
docker system prune                       # Remove unused data (stopped containers, networks, images)
docker system prune -a                    # Remove all unused images and containers
docker info                               # Display system-wide Docker information
docker version                            # Show Docker client and server version
```

---

### **Summary Table**

|**Category**|**Purpose**|
|---|---|
|**Container Lifecycle**|Start, stop, restart, remove containers|
|**Image Management**|Pull, remove, inspect, and commit images|
|**Inspection & Monitoring**|View container and system details|
|**Execution & Interaction**|Run commands inside containers|
|**Networking**|Manage container networks and ports|
|**Volumes & Storage**|Manage persistent storage|
|**Docker Compose**|Work with multi-container apps|
|**Build & Deployment**|Build and push custom images|
|**Security & User Management**|Manage users, authentication, and security|
|**System & Cleanup**|Maintain and free up Docker resources|
```shell

docker rmi $(docker images -q) # This removes all Docker images.
docker rmi $(docker images -q) -f # This removes all Docker images forcefully.

```