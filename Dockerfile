FROM openjdk

ADD target/*.jar /app.jar
WORKDIR /
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
