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
                echo 'Deploying application container...'
                sh '''
                    docker rm -f samplejava-app 2>/dev/null || true
                    docker run --name samplejava-app samplejava:latest
                '''
            }
        }
    }

    post {
        always {
            echo '===================================='
            echo 'Build and Deployment completed!'
            echo '===================================='
        }
    }
}
