FROM clojure:openjdk-11-lein AS builder

WORKDIR /app
COPY . /app

RUN lein uberjar

FROM openjdk:11-jre-slim

ENV AWS_REGION=sa-east-1

COPY --from=builder /app/target/uberjar/my-crud-app-0.1.0-SNAPSHOT-standalone.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]
