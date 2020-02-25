# micro-orders runs under JAVA
FROM maven:3.6.3-jdk-14
MAINTAINER sergeblr
WORKDIR /home/cafemenu

# Fetch maven dependencies declared in SUPER pom.xml file (w/o project modules & artifacts)
COPY ./pom.xml /home/cafemenu
RUN mvn dependency:go-offline dependency:resolve-plugins -DexcludeGroupIds=com.epam.summer19 -N
#RUN mvn dependency:go-offline -DexcludeGroupIds=com.epam.summer19 -N    // Do not recurse in modules

# Build all project with previously fetched dependencies, compiles all sumbodules
COPY . /home/cafemenu
RUN mvn -DskipTests clean install
