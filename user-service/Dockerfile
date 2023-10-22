FROM openjdk:17
VOLUME /tmp
COPY build/libs/user-service-1.0.jar UserService.jar
ENTRYPOINT ["java", "-jar", "UserService.jar"]