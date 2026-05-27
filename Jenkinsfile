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
                withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                    sh 'mvn sonar:sonar \
                          -Dsonar.projectKey=com.development.baseapp:baseapp \
                          -Dsonar.host.url=${SONAR_HOST_URL} \
                          -Dsonar.login=${SONAR_TOKEN}'
                }
            }
        }
        stage('Deploy') {
            steps {
                withCredentials([
                    string(credentialsId: 'docker-registry-url', variable: 'REGISTRY_URL'),
                    usernamePassword(credentialsId: 'docker-registry-credentials',
                        usernameVariable: 'REGISTRY_USER', passwordVariable: 'REGISTRY_PASS')
                ]) {
                    sh '''
                        docker build -t ${REGISTRY_URL}/baseapp:${BUILD_NUMBER} .
                        echo ${REGISTRY_PASS} | docker login ${REGISTRY_URL} -u ${REGISTRY_USER} --password-stdin
                        docker push ${REGISTRY_URL}/baseapp:${BUILD_NUMBER}
                        docker tag ${REGISTRY_URL}/baseapp:${BUILD_NUMBER} ${REGISTRY_URL}/baseapp:latest
                        docker push ${REGISTRY_URL}/baseapp:latest
                    '''
                }
            }
        }
    }
}