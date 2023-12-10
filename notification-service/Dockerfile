FROM openjdk:17

COPY target/*.jar app.jar

EXPOSE 7400

ENTRYPOINT ["java", "--add-opens", "java.base/java.time=ALL-UNNAMED", "-jar", "/app.jar"]
