FROM openjdk:11-jdk-oracle AS builder

WORKDIR /dashboard
COPY memento-grpc-interface memento-grpc-interface
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJAR

FROM openjdk:11-jdk-oracle
COPY --from=builder /dashboard/build/libs/*.jar /dashboard.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Daws.key.access=${ACCESS_KEY}","-Daws.key.secret=${SECRET_KEY}", "/dashboard.jar"]
