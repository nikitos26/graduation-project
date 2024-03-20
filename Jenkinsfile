pipeline {
    agent any

    tools {
        maven "MAVEN_HOME"
    }

    stages {
        stage('Build Project') {
            steps {
                sh 'mvn clean install -DskipTests=true'
            }
        }
        stage('Run Tests') {
            steps {
                sh 'mvn test -Dsuite=${SUITE} -Dconfig=${CONFIG}'
            }
        }
        stage('Archive Logs') {
            steps {
                archiveArtifacts artifacts: 'target/logs/*', followSymlinks: false
            }
        }
    }
    post("Allure report") {
        always {
            allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
        }
    }
}
