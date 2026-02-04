# 1. Compilación
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Ejecución
FROM payara/micro:6.2023.10-jdk17
WORKDIR /opt/payara

# Copiamos el WAR a la carpeta de autodespliegue como ROOT.war
COPY --from=build /app/target/*.war /opt/payara/deployments/ROOT.war

# Copiamos el XML de recursos
COPY glassfish-resources.xml /opt/payara/glassfish-resources.xml

# Límite de memoria (Bien hecho, esto es vital en Railway)
ENV JAVA_TOOL_OPTIONS="-Xmx300m -Xms150m"

# --- CORRECCIÓN ---
# Eliminé el CMD y puse todo en el ENTRYPOINT.
# Agregué "--resources /opt/payara/glassfish-resources.xml" explícitamente.
ENTRYPOINT ["java", "-jar", "payara-micro.jar", "--deploymentDir", "deployments", "--resources", "/opt/payara/glassfish-resources.xml", "--port", "8080", "--nocluster"]