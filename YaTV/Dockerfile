FROM gradle:6.7.1-jdk8

COPY ./ ./

RUN apt-get update && \
apt-get install -y mysql-client && \
rm -rf /var/lib/apt && \
./gradlew jar


CMD java -jar ./build/libs/YaTV-1.0-SNAPSHOT.jar