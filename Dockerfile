FROM maven:3.5.2-jdk-8 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:8
COPY --from=build /usr/src/app/target/lotus-authen-service-1.0.0.jar /usr/app/lotus-authen-service-1.0.0.jar
EXPOSE 8088
ENTRYPOINT ["java","-jar","/usr/app/lotus-authen-service-1.0.0.jar"] 