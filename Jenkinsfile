pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-17'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/YOUR_USERNAME/YOUR_REPO.git'
            }
        }

        stage('Build Product Service') {
            steps {
                sh 'mvn clean package -pl product-service -am -Dspring.profiles.active=ci'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t product-service:latest -f product-service/Dockerfile .'
            }
        }

        stage('List Images') {
            steps {
                sh 'docker images'
            }
        }
    }
}
