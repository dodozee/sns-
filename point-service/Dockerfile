FROM openjdk:17
VOLUME /tmp
COPY build/libs/point-service-0.0.1-SNAPSHOT.jar PointService.jar
ENTRYPOINT ["java", "-jar", "PointService.jar"]