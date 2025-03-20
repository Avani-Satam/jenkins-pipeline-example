pipeline {
    agent any
    tools {
        maven "MAVEN"
        jdk "JDK"
    }
    stages {
        stage('Initialize') {
            steps {
                echo "Initializing build process..."
                echo "M2_HOME = /opt/maven"
            }
        }
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/Avani-Satam/jenkins-pipeline-example.git'
            }
        }
        stage('Build') {
            steps {
                dir("http://localhost:8080/job/Maven-Jenkins-Pipeline/ws") {
                    sh 'mvn -B -DskipTests clean package'
                }
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Deploy to Tomcat') {
            steps {
                sh '''
                cp target/*.war /var/lib/tomcat9/webapps/
                '''
            }
        }
    }
    post {
        always {
            junit(
                allowEmptyResults: true,
                testResults: '*/test-reports/.xml'
            )
        }
    }
}
