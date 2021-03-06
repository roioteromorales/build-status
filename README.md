[![CircleCI](https://circleci.com/gh/roioteromorales/build-status.svg?style=svg)](https://circleci.com/gh/roioteromorales/build-status)
[![codecov](https://codecov.io/gh/roioteromorales/build-status/branch/master/graph/badge.svg)](https://codecov.io/gh/roioteromorales/build-status)

# Build status

For a graphical way of viewing builds and promoting them

## How to run it
Just start the spring boot project and will be under the url: 


http://localhost:8080/

### Requirements:
Add to your environment variables these values:

  - DRONE_TOKEN ex: 123456789abcd
  - DRONE_SERVER ex: https://ci.build.yourcompany.com
  - ORGANIZATION ex: roisoftstudio
  - GITHUB_TOKEN ex: 123456789abcd

## Just Run it!

### To run the existing image from docker hub:

`docker run -e DRONE_SERVER=$DRONE_SERVER -e DRONE_TOKEN=$DRONE_TOKEN -e ORGANIZATION=$ORGANIZATION -e GITHUB_TOKEN=$GITHUB_TOKEN -p8080:8080 roioteromorales/build-status:latest `

## Want to personalize it?

#### Build your own

##### To build and run your own image:

docker build -t build-status .

`docker run -e DRONE_SERVER=$DRONE_SERVER -e DRONE_TOKEN=$DRONE_TOKEN -e ORGANIZATION=$ORGANIZATION -e GITHUB_TOKEN=$GITHUB_TOKEN -p8080:8080 build-status`

or you can use the docker-compose file:

`docker-compose up`
