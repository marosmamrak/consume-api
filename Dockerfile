# Use Maven with JDK 17 to build the application
FROM openjdk:17-jdk-slim-buster AS builder
WORKDIR /app
COPY . .
RUN mvn -e -X clean package

# Use OpenJDK 17 to run the application
FROM openjdk:17-jdk-slim-buster
COPY --from=builder /app/target/*.jar /app/consumeapi.jar
ENTRYPOINT ["java", "-jar", "/app/consumeapi.jar"]


