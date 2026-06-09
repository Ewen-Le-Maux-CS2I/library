# Étape 1 : Build de l'application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
# Cache les dépendances Maven
RUN mvn dependency:go-offline
COPY src ./src
# Compile sans rejouer les tests (déjà faits dans la CI/CD)
RUN mvn clean package -DskipTests

# Étape 2 : Exécution
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]  