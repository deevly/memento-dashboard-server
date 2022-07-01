FROM openjdk:11.0.15-jre-slim AS base

FROM base as builder

WORKDIR /dashboard
COPY memento-grpc-interface memento-grpc-interface
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJAR

FROM base as dashboard

COPY --from=builder /dashboard/build/libs/*.jar /dashboard.jar
EXPOSE 8180 8190
ENTRYPOINT ["java","-jar","-Daws.key.access=${ACCESS_KEY}","-Daws.key.secret=${SECRET_KEY}", "/dashboard.jar"]
