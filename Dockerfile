FROM openjdk:17
ARG JAR_FILE=api/build/libs/*.jar
CMD ["java", "-jar", "app.jar"]
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
