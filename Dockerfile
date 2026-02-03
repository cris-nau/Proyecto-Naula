FROM payara/micro:6.2023.10-jdk17
WORKDIR /opt/payara
COPY target/*.war /opt/payara/deployments/app.war
ENV JAVA_TOOL_OPTIONS="-Xmx300m -Xms150m"
ENTRYPOINT ["java", "-jar", "payara-micro.jar", "--deploymentDir", "deployments", "--port", "8080", "--nocluster"]