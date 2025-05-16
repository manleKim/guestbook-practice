FROM eclipse-temurin:17-jdk-jammy as build
WORKDIR /workspace/app

COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .

RUN ./gradlew dependencies

COPY src src
RUN ./gradlew build -x test

FROM eclipse-temurin:17-jre-jammy
VOLUME /tmp
COPY --from=build /workspace/app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
