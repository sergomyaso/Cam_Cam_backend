#!/bin/sh
echo creating docker env...
./prebuild.sh
echo docker env created
echo creating build server...
docker build -t camcam ../
echo build was created
echo run compose servers...
./run_backend_image.sh
echo compose run was success
read -n 1 -s -r -p "Press any key to continue"


