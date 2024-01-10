FROM maven:3.8.5-openjdk-17
WORKDIR /backend
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run
#ADD target/sia-manager-mongo-docker.jar app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]


# FROM maven:3.8.2-jdk-8 # for Java 8


