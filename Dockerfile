# 1. Compilación
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Ejecución
FROM payara/micro:6.2023.10-jdk17
WORKDIR /opt/payara

# Copiamos el WAR
COPY --from=build /app/target/*.war /opt/payara/deployments/ROOT.war

# Copiamos tu archivo de recursos
COPY glassfish-resources.xml /opt/payara/glassfish-resources.xml

# --- CORRECCIÓN VITAL ---
# 1. Creamos un archivo de texto con el comando "add-resources"
RUN echo "add-resources /opt/payara/glassfish-resources.xml" > /opt/payara/postboot_commands.txt

# Configuración de memoria
ENV JAVA_TOOL_OPTIONS="-Xmx300m -Xms150m"

# 2. Usamos la bandera REAL "--postbootcommandfile"
ENTRYPOINT ["java", "-jar", "/opt/payara/payara-micro.jar", "--deploymentDir", "deployments", "--postbootcommandfile", "/opt/payara/postboot_commands.txt", "--port", "8080", "--nocluster"]