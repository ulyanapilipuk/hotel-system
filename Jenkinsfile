pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn compile'
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
    }
}