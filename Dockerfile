FROM maven:3.6.3-jdk-11 AS build

COPY src /home/app/src
COPY pom.xml /home/app

RUN mvn package -f /home/app

FROM openjdk:8-jdk

COPY --from=build /home/app/target/RaceControl.jar /home/app/RaceControl.jar

ENTRYPOINT ["java", "-jar", "/home/app/RaceControl.jar"]