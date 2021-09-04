@echo off

echo Stopping containers...
FOR /f "tokens=*" %%i IN ('docker ps -q') DO docker stop %%i
echo Deleting containers...
FOR /f "tokens=*" %%i IN ('docker ps -aq') DO docker rm %%i
echo Removing srygospizza images...
docker image rm srygospizza-backend
docker image rm srygospizza-frontend
echo Removing dangling images left after build
FOR /f "tokens=*" %%i IN ('docker images -f "dangling=true" -q') DO docker rmi %%i
echo Deleting volumes...
FOR /f "tokens=*" %%i IN ('docker volume ls -q') DO docker volume rm %%i
echo Deleting networks...
docker network prune --force

echo Images left:
docker image ls -q
echo Containers left:
docker ps -aq
echo Volumes left:
docker volume ls -q
echo Networks left (there should be three default ones):
docker network ls -q
