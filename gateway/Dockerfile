
FROM docker.io/openjdk:8-alpine

MAINTAINER imlent@163.com

RUN mkdir -p /wfe

WORKDIR /wfe

EXPOSE 80

ADD ./target/gateway-0.0.1.jar ./wfe-gateway.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "wfe-gateway.jar"]

CMD ["--spring.profiles.active=dev"]