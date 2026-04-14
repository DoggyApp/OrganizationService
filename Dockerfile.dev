FROM gradle:8-jdk21 AS GRADLE_BUILD

WORKDIR /OrganizationService
COPY . .
RUN gradle clean build

FROM eclipse-temurin:21-jre-alpine

WORKDIR /OrganizationService
COPY --from=GRADLE_BUILD /OrganizationService/build/libs/OrganizationService-0.0.1-SNAPSHOT.jar /OrganizationService/OrganizationService.jar

CMD ["java", "-jar", "/OrganizationService/OrganizationService.jar"]