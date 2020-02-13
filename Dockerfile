# micro-orders runs under JAVA
FROM maven:latest
MAINTAINER sergeblr

COPY . /home/cafemenu

RUN mvn -f /home/cafemenu/pom.xml -DskipTests clean install