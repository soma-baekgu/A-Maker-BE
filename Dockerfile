FROM openjdk:17
RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && echo "Asia/Seoul" > /etc/timezone
ARG JAR_FILE=api/build/libs/*.jar
COPY ${JAR_FILE} /root/app.jar
CMD ["java", "-jar", "/root/app.jar"]
