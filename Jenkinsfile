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
                bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml -Denv=dev"
            }
        }
    }

    stage('Regression API Test - QA') {
        steps {
            dir('api-framework') {
                bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml -Denv=qa"
            }
        }
    }
}


}
