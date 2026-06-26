# Paso 1: Compilar la aplicación con Maven usando Eclipse Temurin
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar el pom y descargar dependencias (Aprovecha la caché de Docker)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente y compilar saltándose los tests (Justo lo que le explicaremos al profe)
COPY src ./src
RUN mvn clean package -DskipTests

# Paso 2: Crear la imagen ligera usando Eclipse Temurin JRE
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar el JAR generado desde el paso de compilación de forma explícita
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto configurado en tu docker-compose
EXPOSE 8082

# Comando para ejecutar la app de manera segura
ENTRYPOINT ["java", "-jar", "app.jar"]