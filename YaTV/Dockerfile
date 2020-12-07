FROM gradle:6.7.1-jdk8

COPY ./ ./

RUN ./gradlew jar

CMD java -jar ./build/libs/YaTV.jar