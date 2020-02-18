# micro-orders runs under JAVA
FROM maven:3.6.3-jdk-14
MAINTAINER sergeblr
WORKDIR /home/cafemenu

# Fetch maven dependencies declared in SUPER pom.xml file (w/o project modules & artifacts)
COPY ./pom.xml /home/cafemenu
RUN mvn dependency:go-offline -DexcludeGroupIds=com.epam.summer19 -N

# Build all project with previously fetched dependencies, compiles all sumbodules
COPY . /home/cafemenu
RUN mvn -f /home/cafemenu/pom.xml -DskipTests clean install
