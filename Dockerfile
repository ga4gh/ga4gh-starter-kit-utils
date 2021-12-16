FROM openjdk:11.0.4-jre-slim-buster

WORKDIR /usr/src/app

ARG VERSION

COPY build/libs/ga4gh-starter-kit-utils-${VERSION}.jar ga4gh-starter-kit-utils.jar

ENTRYPOINT [ "java", "-jar", "ga4gh-starter-kit-utils.jar" ]
