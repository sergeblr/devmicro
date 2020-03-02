# CafeMenu image file with CACHED dependencies
FROM cafemenu_cached_img:latest
MAINTAINER sergeblr
WORKDIR /home/cafemenu

COPY . .
RUN mvn -f pom.xml -B -DskipTests package

CMD [""]
