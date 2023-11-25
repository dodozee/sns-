FROM openjdk:17
VOLUME /tmp
COPY build/libs/matching-service-0.0.1-SNAPSHOT.jar MatchingService.jar
ENTRYPOINT ["java", "-jar", "MatchingService.jar"]