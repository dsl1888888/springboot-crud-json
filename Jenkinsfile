pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-1.21.0-openjdk-amd64'  // Set Java environment variable
        MAVEN_HOME = '/usr/share/maven'  // Set Maven home (make sure this is correct for your Jenkins setup)
        BUILD_TIMESTAMP = sh(script: "date +'%Y-%m-%d_%H-%M-%S'", returnStdout: true).trim()  // Timestamp
    }

    stages {
        
        stage('Initialize') {
            steps {
                echo ".................................................................................................."
                echo "Job Name: ${JOB_NAME}"
                echo "Build Number: ${BUILD_NUMBER}"
                echo "Workspace: ${WORKSPACE}"
                echo "Build Timestamp: ${BUILD_TIMESTAMP}"
                echo ".................................................................................................."
            }
        }

        stage('Maven Build') {
            steps {
                echo "Running Maven clean install..."
                script {
                    // Run Maven clean install
                    sh "${MAVEN_HOME}/bin/mvn clean install -DskipTests"
                }
            }
        }

        stage('Build and Copy JAR') {
            steps {
                echo "Start copying JAR file..."
                script {
                    // Create directory and copy the jar to a specific location
                    sh 'mkdir -p /opt/jar'
                    sh "cp ${WORKSPACE}/target/springboot-crud-json-0.0.1-SNAPSHOT.jar /opt/jar/springboot-crud-json-${BUILD_TIMESTAMP}-${BUILD_NUMBER}.jar"
                }
            }
        }

        stage('Kill Spring Boot Application') {
            steps {
                echo "Killing existing application..."
                script {
                    // Kill the existing process (if exists)
                    sh 'pgrep -f "springboot-crud-json" | xargs kill -9 || true'  // Ignore error if no process is found
                }
            }
        }


        stage('Start Spring Boot Application') {
            steps {
                echo "Starting the new application..."
                script {
                    // Start the new application in the background with nohup, ensuring it keeps running
                    sh """
                        export JENKINS_NODE_COOKIE=dontKillMe
                        nohup java -jar /opt/jar/springboot-crud-json-${BUILD_TIMESTAMP}-${BUILD_NUMBER}.jar --server.port=8600 > /dev/null 2>&1 &
                    """
                }
            }
        }





        stage('Verify and Check Application') {
            steps {
                echo "Verifying the process and checking if application started..."
                script {
                    // Check if the application is running
                    sh "ps aux | grep springboot-crud-json-${BUILD_TIMESTAMP}-${BUILD_NUMBER}.jar"
                    // Optionally check the endpoint
                    echo "Check the link: http://20.205.24.40:8600/api/users"
                }
            }
        }
    }

    post {
        success {
            echo "Spring Boot application deployed successfully!"
        }
        failure {
            echo "Deployment failed. Please check the logs and fix the issue."
        }
    }
}
