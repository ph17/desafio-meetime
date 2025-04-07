#Build da aplicação
FROM openjdk:17-jdk AS build
WORKDIR /app

# Copia arquivos do Maven e código-fonte
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY src src

# Permissão para o wrapper e build do projeto
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Imagem final
FROM openjdk:17-jdk
WORKDIR /app

# Define volume temporário
VOLUME /tmp

# Copia JAR gerado do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Ponto de entrada da aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]