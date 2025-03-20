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
                git branch: 'main', url: 'https://github.com/your-repository-url.git'
            }
        }
        stage('Build') {
            steps {
                dir("your-workspace-path") {
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
