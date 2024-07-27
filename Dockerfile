FROM eclipse-temurin:17.0.8.1_1-jdk-jammy
WORKDIR /app
RUN mkdir -p /app/music/images-user \
               /app/music/videos-user \
               /app/music/images-post \
               /app/music/videos-post \
               /app/music/files
               
RUN apt-get update && \
     apt-get install -y maven && \
     rm -rf /var/lib/apt/lists/*
COPY . .
RUN chmod +x ./mvnw && ./mvnw clean install -DskipTests
ENTRYPOINT ["java", "-jar", "target/rede-social-0.0.1-SNAPSHOT.jar"]
