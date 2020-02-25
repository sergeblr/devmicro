# micro-orders runs under JAVA
FROM maven:3.6.3-jdk-14 as mavencached
MAINTAINER sergeblr
WORKDIR /home/cafemenu

# Fetch maven dependencies declared in SUPER pom.xml file (w/o project modules & artifacts)
#COPY ./model/pom.xml /home/cafemenu/model/pom.xml
#COPY ./test-db/pom.xml /home/cafemenu/test-db/pom.xml
#OPY ./dao/pom.xml /home/cafemenu/dao/pom.xml
#COPY ./dao-api/pom.xml /home/cafemenu/dao-api/pom.xml
#COPY ./service/pom.xml /home/cafemenu/service/pom.xml
#COPY ./service-api/pom.xml /home/cafemenu/service-api/pom.xml
#COPY ./micro-items/pom.xml /home/cafemenu/micro-items/pom.xml
#COPY ./micro-iteminorders/pom.xml /home/cafemenu/micro-iteminorders/pom.xml
#COPY ./micro-orders/pom.xml /home/cafemenu/micro-orders/pom.xml
#COPY ./web-app-rabbit/pom.xml /home/cafemenu/web-app-rabbit/pom.xml

COPY ./pom.xml /home/cafemenu/pom.xml
RUN mvn -f /home/cafemenu/pom.xml -B dependency:resolve-plugins dependency:go-offline -N
#RUN mvn -B de.qaware.maven:go-offline-maven-plugin:1.2.5:resolve-dependencies -Dmaven.repo.local=.m2/repository -N
#RUN mvn org.apache.maven.plugins:maven-dependency-plugin:3.1.1:go-offline -B -N
#RUN mvn -f ./home/cafemenu/pom.xml -B dependency:go-offline
 # RUN mvn dependency:resolve-plugins dependency:go-offline -DexcludeGroupIds=com.epam.summer19 -N
 # RUN mvn dependency:go-offline -DexcludeGroupIds=com.epam.summer19 -N    // Do not recurse in modules

# Build all project with previously fetched dependencies, compiles all sumbodules
FROM mavencached
WORKDIR /home/cafemenu
COPY . /home/cafemenu
RUN mvn -f /home/cafemenu/pom.xml -B -DskipTests package
