FROM amazoncorretto:17-alpine-jdk
EXPOSE 8080
ADD target/mydocker.jar mydocker.jar
ENTRYPOINT ["java", "-jar", "/mydocker.jar"]
