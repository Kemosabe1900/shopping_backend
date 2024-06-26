FROM openjdk:8git
EXPOSE 8080
ADD target/mydocker.jar mydocker.jar
ENTRYPOINT ["java", "-jar", "mydocker.jar"]