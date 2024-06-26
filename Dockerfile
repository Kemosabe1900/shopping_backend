FROM openjdk:8
EXPOSE 8080
ADD target/mydocker.jar mydocker.jar
ENTRYPOINT ["java", "-jar", "mydocker.jar"]