# 1. Compilación (Usando Java 17 para evitar el error de versión)
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Creamos el WAR. El skipTests ahorra tiempo y errores de entorno.
RUN mvn clean package -DskipTests

# 2. Ejecución (Payara Micro sobre Java 17)
FROM payara/micro:6.2023.10-jdk17
WORKDIR /opt/payara

# Copiamos el WAR generado y lo renombramos a ROOT.war
# Esto hace que tu app responda en la URL principal (/) sin poner el nombre del proyecto
COPY --from=build /app/target/*.war /opt/payara/deployments/ROOT.war

# Límite de memoria para evitar el error "Killed" en Railway
ENV JAVA_TOOL_OPTIONS="-Xmx300m -Xms150m"

# Comando de arranque optimizado
ENTRYPOINT ["java", "-jar", "payara-micro.jar", "--deploymentDir", "deployments", "--port", "8080", "--nocluster"]