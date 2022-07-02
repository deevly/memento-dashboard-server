FROM openjdk:11-jdk-slim

WORKDIR /dashboard
COPY memento-grpc-interface memento-grpc-interface
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJAR

EXPOSE 8180 8190
ENTRYPOINT ["java","-jar","-Daws.key.access=${ACCESS_KEY}","-Daws.key.secret=${SECRET_KEY}", "/dashboard/build/libs/dashboard-0.0.1-SNAPSHOT.jar"]
