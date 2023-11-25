FROM openjdk:17
VOLUME /tmp
COPY build/libs/booking-service-0.0.1-SNAPSHOT.jar BookingService.jar
ENTRYPOINT ["java", "-jar", "BookingService.jar"]