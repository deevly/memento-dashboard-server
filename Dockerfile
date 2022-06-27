FROM appinair/jdk11-maven as base

FROM base as builder

WORKDIR /memento-dashboard-server
COPY . /memento-dashboard-server

RUN chmod 700 gradlew
RUN ./gradlew bootJar

FROM base as producer

COPY --from=builder /memento-dashboard-server/build/libs/dashboard-0.0.1-SNAPSHOT.jar /dashboard.jar

ENTRYPOINT ["java","-jar","-Daws.key.access=${ACCESS_KEY}","-Daws.key.secret=${SECRET_KEY}", "/dashboard.jar"]
