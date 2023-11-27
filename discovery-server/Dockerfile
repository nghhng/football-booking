FROM openjdk:17

COPY target/*.jar app.jar

EXPOSE 7654

ENTRYPOINT ["java","-jar","/app.jar"]