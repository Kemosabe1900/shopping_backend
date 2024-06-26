from openjdk:8
EXPOSE 8080
ADD target/mydocker.jar mydocker.jar
EXTRYPOINT ["java", "jar", "mydocker.jar"]