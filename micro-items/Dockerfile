# micro-items runs under JVM
FROM cafemenu_mvn:latest as cmbuild
CMD [""]

FROM anapsix/alpine-java
MAINTAINER sergeblr
COPY --from=cmbuild /home/cafemenu/micro-items/target/micro-items-1.0.0-SNAPSHOT.jar /
ENTRYPOINT ["java", "-jar", "micro-items-1.0.0-SNAPSHOT.jar"]