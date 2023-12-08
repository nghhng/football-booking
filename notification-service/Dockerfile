FROM openjdk:17

COPY target/*.jar app.jar

EXPOSE 7400

ENTRYPOINT ["java","-jar","/app.jar"]