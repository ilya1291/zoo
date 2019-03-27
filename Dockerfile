FROM maven:3.6-jdk-8-alpine as mvnbuilder
COPY . /build/
WORKDIR /build
RUN mvn clean package

FROM openjdk:8-jdk
COPY --from=mvnbuilder /build/zoo-impl/target/zoo-impl.jar /opt/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/zoo-impl.jar"]