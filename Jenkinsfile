pipeline {
agent any

tools {
    maven 'maven'
}

stages {

    stage('Checkout Sample Project') {
        steps {
            dir('sample-project') {
                git 'https://github.com/jglick/simple-maven-project-with-tests.git'
            }
        }
    }

    stage('Build Sample Project') {
        steps {
            dir('sample-project') {
                bat "mvn clean package -Dmaven.test.failure.ignore=true"
            }
        }
    }

    stage('Checkout API Framework') {
        steps {
            dir('api-framework') {
                git branch: 'main', url: 'https://github.com/Madhusudan-1990/RestAssuredAPIAutomationFramework.git'
            }
        }
    }

    stage('Sanity API Test - DEV') {
        steps {
            dir('api-framework') {
                script {
                    def status = bat(
                        script: "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml -Denv=dev",
                        returnStatus: true
                    )
                    if (status != 0) {
                        currentBuild.result = 'UNSTABLE'
                        echo "DEV failures are expected"
                    }
                }
            }
        }
    }

    stage('Regression API Test - QA') {
        steps {
            dir('api-framework') {
                script {
                    def status = bat(
                        script: "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml -Denv=qa",
                        returnStatus: true
                    )
                    if (status != 0) {
                        currentBuild.result = 'UNSTABLE'
                        echo "QA failures are expected"
                    }
                }
            }
        }
    }

    stage('Sanity API Test - STAGE') {
        steps {
            dir('api-framework') {
                script {
                    def status = bat(
                        script: "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml -Denv=stage",
                        returnStatus: true
                    )
                    if (status != 0) {
                        currentBuild.result = 'UNSTABLE'
                        echo "STAGE failures are expected"
                    }
                }
            }
        }
    }

    stage('Sanity API Test - PROD') {
        steps {
            dir('api-framework') {
                bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml -Denv=prod"
            }
        }
    }
}

post {
    always {
        echo "Pipeline completed"
    }
    success {
        echo "PROD passed"
    }
    unstable {
        echo "Non-prod failures occurred (expected)"
    }
    failure {
        echo "PROD failed"
    }
}


}
