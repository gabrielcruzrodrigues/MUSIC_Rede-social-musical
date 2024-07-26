docker-compose down

docker build -t backend:latest ./

docker-compose up --build --force-recreate --remove-orphans