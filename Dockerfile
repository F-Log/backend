FROM openjdk:17-slim as build

WORKDIR /app

# build first
COPY ../docker/backend/gradlew gradlew
COPY ../docker/backend/build.gradle build.gradle

# Grant execution rights on the Gradle Wrapper script
RUN chmod +x ./gradlew

COPY ../docker/backend .

# build the application without running tests and without starting the Gradle Daemon
RUN ./gradlew build --no-daemon -x test

FROM openjdk:17-slim

WORKDIR /app

# locate nutrition.csv directly in /app in the container
COPY --from=build /app/src/main/java/com/f_log/flog/csvdata/nutritiondata.csv nutritiondata.csv

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
