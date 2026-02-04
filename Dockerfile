# 1. Compilación
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Ejecución
FROM payara/micro:6.2023.10-jdk17
WORKDIR /opt/payara

# Copiamos el WAR renombrado a ROOT.war (para que sea la app principal)
COPY --from=build /app/target/*.war /opt/payara/deployments/ROOT.war

# Copiamos la configuración de base de datos
COPY glassfish-resources.xml /opt/payara/glassfish-resources.xml

# Configuración de memoria vital para Railway
ENV JAVA_TOOL_OPTIONS="-Xmx300m -Xms150m"

# --- EL COMANDO DEFINITIVO ---
# Usamos la ruta completa "/opt/payara/payara-micro.jar" para que no falle nunca.
ENTRYPOINT ["java", "-jar", "/opt/payara/payara-micro.jar", "--deploymentDir", "deployments", "--resources", "/opt/payara/glassfish-resources.xml", "--port", "8080", "--nocluster"]