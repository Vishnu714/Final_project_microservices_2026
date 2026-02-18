# -------- Stage 1: Build --------
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy entire multi-module project
COPY . .

# Build only product-service
RUN mvn clean package -pl inventory-service -am -DskipTests

# -------- Stage 2: Runtime --------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/inventory-service/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","app.jar"]
