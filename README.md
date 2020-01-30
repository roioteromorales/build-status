# Build status

For a graphical way of viewing builds and promoting them

## How to run it
Just start the spring boot project and will be under the url: 

http://localhost:8080/env-status

### Docker

`docker run -e DRONE_SERVER=$DRONE_SERVER -e DRONE_TOKEN=$DRONE_TOKEN -e ORGANIZATION=$ORGANIZATION -e GITHUB_TOKEN=$GITHUB_TOKEN roioteromorales/build-status:latest `

or you can use the docker-compose file:

`docker-compose up`

### Requirements:
Add to your environment variables these values:

  - DRONE_TOKEN ex: 123456789abcd
  - DRONE_SERVER ex: https://ci.build.yourcompany.com
  - ORGANIZATION ex: roisoftstudio
  - GITHUB_TOKEN ex: 123456789abcd

