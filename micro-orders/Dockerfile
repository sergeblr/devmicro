# micro-orders runs under JAVA
MAINTAINER sergeblr

FROM anapsix/alpine-java
ADD target/micro-orders-1.0.0-SNAPSHOT.jar /
ENTRYPOINT ["java", "-jar", "micro-orders-1.0.0-SNAPSHOT.jar"]