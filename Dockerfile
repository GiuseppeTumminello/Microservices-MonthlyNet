#FROM gradle:7.4-jdk11-alpine AS build
FROM openjdk:11-jre-slim
EXPOSE 8092
COPY  /build/libs/*.jar ./app.jar
CMD ["java","-jar","app.jar"]