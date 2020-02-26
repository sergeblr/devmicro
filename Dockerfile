# micro-orders runs under JAVA
FROM maven:3.6.3-jdk-8-openj9
MAINTAINER sergeblr
WORKDIR /home/cafemenu

# Copy all subModules POMs that declared in SuperPOM
COPY ./model/pom.xml /home/cafemenu/model/pom.xml
COPY ./test-db/pom.xml /home/cafemenu/test-db/pom.xml
COPY ./dao/pom.xml /home/cafemenu/dao/pom.xml
COPY ./dao-api/pom.xml /home/cafemenu/dao-api/pom.xml
COPY ./service/pom.xml /home/cafemenu/service/pom.xml
COPY ./service-api/pom.xml /home/cafemenu/service-api/pom.xml
COPY ./micro-items/pom.xml /home/cafemenu/micro-items/pom.xml
COPY ./micro-iteminorders/pom.xml /home/cafemenu/micro-iteminorders/pom.xml
COPY ./micro-orders/pom.xml /home/cafemenu/micro-orders/pom.xml
COPY ./web-app-rabbit/pom.xml /home/cafemenu/web-app-rabbit/pom.xml
# COPY SuperPOM
COPY ./pom.xml /home/cafemenu/pom.xml
# Fetch maven dependencies declared in SUPER pom.xml file (w/o project modules artifacts)
RUN mvn -f /home/cafemenu/pom.xml de.qaware.maven:go-offline-maven-plugin:1.2.5:resolve-dependencies
# add '-N' to mvn -> submodules will be ignored (no need to copy all subPOMs), but modules dependencies will NOT be fetched
# add '-DexcludeGroupIds=com.epam.summer19' to mvn -> dependencies with that GroupId will be ignored
# DEPRECATED: '-Dmaven.repo.local=.m2/repository' to mvn -> set maven repository folder
# !!!!! ALL results TILL HERE will be cached by docker, until any pom.xml will be CHANGED !!!!!

# Build all project with previously fetched dependencies, packaged all sumbodules & services
COPY . /home/cafemenu
RUN mvn -f /home/cafemenu/pom.xml -B -DskipTests package
# DEPRECATED: '-Dmaven.repo.local=.m2/repository' to mvn -> set maven repository folder
