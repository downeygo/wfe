
FROM docker.io/openjdk:8-alpine

MAINTAINER imlent@163.com

RUN mkdir -p /wfe

WORKDIR /wfe

EXPOSE 8081

ADD ./target/user-service-0.0.1.jar ./wfe-user-service.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "wfe-user-service.jar"]

CMD ["--spring.profiles.active=test"]