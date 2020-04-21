
FROM docker.io/openjdk:8-alpine

MAINTAINER imlent@163.com

RUN mkdir -p /wfe

WORKDIR /wfe

EXPOSE 8080

ADD ./target/uaa-0.0.1.jar ./wfe-uaa.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "wfe-uaa.jar"]

CMD ["--spring.profiles.active=test"]