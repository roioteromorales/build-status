SHELL := /bin/bash

compile:
	chmod +x ./gradlew
	./gradlew $$GRADLE_PROXY clean build jacocoTestReport jacocoTestCoverage

docker: compile
	docker build -t build-status . && \
	docker tag build-status roioteromorales/build-status:latest && \
	docker push roioteromorales/build-status:latest
