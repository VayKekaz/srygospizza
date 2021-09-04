FROM gradle:6.8-jdk15 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar --no-daemon

FROM openjdk:15-alpine

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-jar", "/app/spring-boot-application.jar"]
