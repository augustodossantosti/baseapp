pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Sonar Analysis') {
            steps {
                sh 'mvn sonar:sonar \
                      -Dsonar.projectKey=com.development.baseapp:baseapp \
                      -Dsonar.host.url=http://134.209.38.106:9000 \
                      -Dsonar.login=4b311e01bfd05d2c7b707bd69213ca9a2083fc67'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}