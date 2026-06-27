
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copiamos primero el wrapper y el pom para cachear las dependencias
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Ahora el codigo fuente y empaquetamos (saltamos tests: ya corren en el CI)
COPY src/ src/
RUN ./mvnw clean package -DskipTests -B

# ---------- Etapa 2: runtime ----------
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app

# Creamos un usuario sin privilegios (NADA de root)
RUN groupadd --system spring && useradd --system --gid spring spring

# Copiamos solo el jar ya construido desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar
RUN chown spring:spring app.jar

# A partir de aqui, el contenedor corre como el usuario 'spring'
USER spring

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]