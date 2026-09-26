pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out source code from GitHub...'
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                echo 'Building and testing application with Maven...'
                sh 'mvn clean test'
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Building Docker container image...'
                sh 'docker build -t samplejava:latest .'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying application container as a continuous web service on port 8081...'
                sh '''
                    # Stop & remove any existing container instance
                    docker rm -f samplejava-app 2>/dev/null || true

                    # Run newly built container mapped to host port 8081
                    docker run -d --name samplejava-app -p 8081:8081 --restart unless-stopped samplejava:latest

                    # Allow a moment for the server to bind
                    sleep 3

                    # Verify health check
                    curl -f http://localhost:8081/health || exit 1
                '''
            }
        }
    }

    post {
        success {
            echo '=================================================='
            echo 'Deployment SUCCESSFUL!'
            echo 'Access web app at: http://localhost:8081'
            echo '=================================================='
        }
        failure {
            echo 'Deployment FAILED. Check container logs.'
        }
    }
}
