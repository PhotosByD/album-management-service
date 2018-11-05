#Album management service
##Docker build locally
docker build .

##Database
docker run -d --name pg-albums -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=album -p 5434:5432 postgres:10.5
