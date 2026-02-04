# 1. Compilación
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Ejecución
FROM payara/micro:6.2023.10-jdk17
USER root
WORKDIR /opt/payara

# --- NUEVO: Descargamos el driver oficial de Postgres en una carpeta externa ---
# Esto soluciona el error de "unnamed module of loader"
RUN mkdir -p /opt/payara/libs
ADD https://jdbc.postgresql.org/download/postgresql-42.7.2.jar /opt/payara/libs/postgresql.jar
RUN chmod 644 /opt/payara/libs/postgresql.jar

# Regresamos al usuario payara por seguridad
USER payara

# Copiamos el WAR
COPY --from=build /app/target/*.war /opt/payara/deployments/ROOT.war

# Copiamos el XML corregido
COPY glassfish-resources.xml /opt/payara/glassfish-resources.xml

# Creamos el comando de post-arranque
RUN echo "add-resources /opt/payara/glassfish-resources.xml" > /opt/payara/postboot_commands.txt

# Configuración de memoria
ENV JAVA_TOOL_OPTIONS="-Xmx300m -Xms150m"

# --- COMANDO FINAL ---
# Agregamos "--addLibs /opt/payara/libs/postgresql.jar" para que Payara encuentre el driver
ENTRYPOINT ["java", "-jar", "/opt/payara/payara-micro.jar", "--deploymentDir", "deployments", "--postbootcommandfile", "/opt/payara/postboot_commands.txt", "--addLibs", "/opt/payara/libs/postgresql.jar", "--port", "8080", "--nocluster"]