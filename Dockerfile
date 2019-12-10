# Extends from tomcat 9.0...

FROM tomcat:9.0

MAINTAINER sergeblr

COPY rest-app/target/rest-app-1.0.0-SNAPSHOT.war /usr/local/tomcat/webapps/