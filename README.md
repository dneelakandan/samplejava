# Sample Java Hello World Project

This repository contains a simple Java 21 Hello World application automated with:
- **Maven** for building and testing
- **Docker** for containerization
- **Jenkins** for local CI pipeline in WSL
- **GitHub Actions** for cloud CI automation (`.github/workflows/ci.yml`)

## Run Locally with Maven
```bash
mvn clean package
java -jar target/samplejava-1.0.0.jar
```

## Run with Docker
```bash
docker build -t samplejava:latest .
docker run --rm samplejava:latest
```

## Run with Jenkins
Use the provided `Jenkinsfile` in your Jenkins Pipeline job.
