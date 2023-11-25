FROM openjdk:17
VOLUME /tmp
COPY build/libs/notification-service-0.0.1-SNAPSHOT.jar NotificationService.jar
ENTRYPOINT ["java", "-jar", "NotificationService.jar"]