# Stage 1: Build
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copiar todo el proyecto
COPY . .

# Dar permisos de ejecución al Maven Wrapper
RUN chmod +x mvnw

# Compilar el proyecto
RUN ./mvnw clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiar el JAR compilado desde el stage anterior
COPY --from=build /app/target/PruebaTecSupermercado-0.0.1-SNAPSHOT.jar app_pruebatecsuper.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_pruebatecsuper.jar"]