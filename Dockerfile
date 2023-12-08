FROM openjdk:20-jdk-slim
COPY target/*.jar task-management-system-1.0.0.jar

ENTRYPOINT ["java", "-jar", "task-management-system-1.0.0.jar"]