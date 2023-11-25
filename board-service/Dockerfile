FROM openjdk:17
VOLUME /tmp
COPY build/libs/board-service-0.0.1-SNAPSHOT.jar BoardService.jar
ENTRYPOINT ["java", "-jar", "BoardService.jar"]