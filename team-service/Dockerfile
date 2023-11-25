FROM openjdk:17
VOLUME /tmp
COPY build/libs/team-service-0.0.1-SNAPSHOT.jar TeamService.jar
ENTRYPOINT ["java", "-jar", "TeamService.jar"]