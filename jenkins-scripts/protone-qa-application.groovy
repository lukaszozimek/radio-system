node {
    def mvnHome
    def nodeHome
    stage('Clean Workspace') {
        deleteDir()
    }
    stage('Setup build enviroment') {
        nodeHome = tool name: 'ProtoneNodejs', type: 'jenkins.plugins.nodejs.tools.NodeJSInstallation'
        env.PATH = "${nodeHome}/bin:${env.PATH}"
        dir('server') {
            git branch: 'develop', credentialsId: 'github', url: 'https://github.com/lukaszozimek/Protone-Backend.git'
        }
        dir('client') {
            git branch: 'develop', credentialsId: 'github', url: 'https://github.com/lukaszozimek/Protone-Client.git'
        }
        mvnHome = tool 'M3'
    }
    stage('Check tools') {

        sh "${nodeHome}/bin/node -v"
        sh "node -v"
        sh "npm -v"
        sh "bower -v"
        sh "gulp -v"

    }
    stage('Build Client') {
        dir('client') {
            sh "npm install"
            sh "bower install"
            sh "gulp build"
        }
    }

    stage('Build Server') {
        dir('server') {
            if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
            } else {
                bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean package/)
            }
        }
    }
    stage('Test Client') {
        dir('client') {
            //sh 'gulp test'
        }
    }
    stage('Test Server') {
        dir('server') {
            sh "'${mvnHome}/bin/mvn' test"
        }
    }

    stage('Merge Frontend and backend') {
        //integrate client sources
        sh "rm -rf ${WORKSPACE}/server/src/main/webapp/app"
        sh "cp -avr ${WORKSPACE}/client/dist  ${WORKSPACE}/server/src/main/webapp/app"
        //provide protone main page to server
        sh "rm -rf ${WORKSPACE}/server/src/main/webapp/app/index.html"
        sh "cp -avr ${WORKSPACE}/client/dist/index.html  ${WORKSPACE}/server/src/main/webapp/app"

        //provide bower from client repository
        sh "rm -rf ${WORKSPACE}/server/bower.json"
        sh "rm -rf ${WORKSPACE}/server/package.json"
        sh "cp -avr ${WORKSPACE}/client/bower.json  ${WORKSPACE}/server/bower.json"
        sh "cp -avr ${WORKSPACE}/client/package.json  ${WORKSPACE}/server/package.json"

    }
    stage('Pack development version') {
        dir('server') {
            sh "./mvnw clean"
            sh "./mvnw package -Pdev -DskipTests"
        }
    }
}
