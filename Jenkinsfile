pipeline {
    agent any

    stages {
        stage('Compile & Test') {
            steps {
                echo 'Building and running tests with Maven...'
                sh 'mvn clean test'
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Building Docker container...'
                sh 'docker build -t samplejava:latest .'
            }
        }

        stage('Docker Run') {
            steps {
                echo 'Running Docker container and displaying output...'
                sh 'docker run --rm samplejava:latest'
            }
        }
    }

    post {
        always {
            echo 'Pipeline execution complete.'
        }
    }
}
