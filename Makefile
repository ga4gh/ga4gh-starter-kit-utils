DOCKER_ORG := ga4gh
DOCKER_REPO := ga4gh-starter-kit-utils
DOCKER_TAG := $(shell cat build.gradle | grep "^version" | cut -f 2 -d ' ' | sed "s/'//g")
DOCKER_IMG := ${DOCKER_ORG}/${DOCKER_REPO}:${DOCKER_TAG}

Nothing:
	@echo "No target provided. Stop"

.PHONY: clean
clean:
	@./gradlew clean

.PHONY: jar
jar:
	@./gradlew bootJar

.PHONY: native-image
native-image:
	@./gradlew nativeImage

.PHONY: docker-build
docker-build: clean jar
	docker build -t ${DOCKER_IMG} --build-arg VERSION=${DOCKER_TAG} .

.PHONY: docker-publish
docker-publish:
	docker image push ${DOCKER_IMG}
