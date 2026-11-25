pipeline {
    agent any

        stages {
            stage('Checkout') {
                steps {
                    checkout scm
                }
            }

        stages {
            stage('Build') {
                steps {
                    sh 'mvn compile'
                }
            }

        stage('Static Analysis') {
            steps {
                sh 'mvn checkstyle:check'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Check JAR') {
            steps {
                sh '''
                    JAR_FILE="target/hotel-system-1.0-SNAPSHOT.jar"

                    if [ -f "$JAR_FILE" ]; then
                        echo "JAR CHECK: SUCCESS"
                        echo "JAR file was found:"
                        ls -lh "$JAR_FILE"
                    else
                        echo "JAR CHECK: FAILURE"
                        echo "JAR file was not found: $JAR_FILE"
                        exit 1
                    fi
                '''
            }
        }
        
         post {
                success {
                    echo 'Pipeline completed successfully'
                }

                failure {
                    echo 'Pipeline failed. Check Console Output.'
                }

                always {
                    echo 'Pipeline execution finished'
                }
            }
    }
}