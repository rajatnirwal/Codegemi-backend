FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy backend files
COPY backend/mvnw .
COPY backend/.mvn .mvn
COPY backend/pom.xml .
COPY backend/src src

RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/itservices-backend-0.0.1-SNAPSHOT.jar"]
