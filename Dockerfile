FROM openjdk:11.0.4-jre-slim-buster

WORKDIR /usr/src/app

COPY build/libs/ga4gh-starter-kit-utils-0.1.0.jar ga4gh-starter-kit-utils.jar

ENTRYPOINT [ "java", "-jar", "ga4gh-starter-kit-utils.jar" ]
