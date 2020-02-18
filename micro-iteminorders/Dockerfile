# micro-iteminorders runs under JVM
FROM cafemenu_mvn:latest as cmbuild
CMD [""]

FROM anapsix/alpine-java
MAINTAINER sergeblr
COPY --from=cmbuild /home/cafemenu/micro-iteminorders/target/micro-iteminorders-1.0.0-SNAPSHOT.jar /
ENTRYPOINT ["java", "-jar", "micro-iteminorders-1.0.0-SNAPSHOT.jar"]