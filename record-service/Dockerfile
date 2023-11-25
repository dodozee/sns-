FROM openjdk:17
VOLUME /tmp
COPY build/libs/record-service-0.0.1-SNAPSHOT.jar RecordService.jar
ENTRYPOINT ["java", "-jar", "RecordService.jar"]