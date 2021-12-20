#!/bin/sh
docker run -d --name camcam-db -p 5435:5432 -e POSTGRES_PASSWORD=qwerty -e POSTGRES_USER=postgres -e POSTGRES_DB=camcam postgres:alpine
sleep 2
#For load backup on empty db
#cat init_db.sql | docker exec -i camcam-db psql -U postgres
read -n 1 -s -r -p "Press any key to continue"