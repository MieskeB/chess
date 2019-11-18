pipeline {
    agent any
    tools {
        maven 'Maven'
        jdk 'jdk8'
    }
	options {
        timeout(time: 5, unit: 'MINUTES')
    }
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }

		
		
        stage ('Build client') {
            steps {
				dir('chessClient') {
					sh 'mvn -Dmaven.test.failure.ignore=true install'
				}
            }
        }
		stage('CodeStyleCheck client') {
            steps{
				dir('chessClient') {
					sh 'mvn clean package sonar:sonar'
				}
            }
        }
		
		
		
		stage ('Build websocket') {
            steps {
				dir('chessWebsocketServer') {
					sh 'mvn -Dmaven.test.failure.ignore=true install'
				}
            }
        }
		stage('CodeStyleCheck websocket') {
            steps{
				dir('chessWebsocketServer') {
					sh 'mvn clean package sonar:sonar'
				}
            }
        }
		
		
		
		stage ('Build api') {
            steps {
				dir('ChessRestServer') {
					sh 'mvn -Dmaven.test.failure.ignore=true install'
				}
            }
        }
		stage('CodeStyleCheck api') {
            steps{
				dir('ChessRestServer') {
					sh 'mvn clean package sonar:sonar'
				}
            }
        }
    }
}