pipeline {
  agent {
    docker { image 'liquibase/liquibase:4.4.2' }
  }
  stages {
    stage('Test') {
      steps {
        sh 'liquibase --version'
      }
    }
  }
}
//properties([parameters([choice(choices: ['prod', 'qa'], description: 'Target environment', name: 'DEPLOY_ENV')])])
//node {
//    def env
//    env = "${params.DEPLOY_ENV}"
//    echo "Current environment is ${env}"
//    stage('Preparation') { // for display purposes
        // Get some code from a GitHub repository
       // git 'https://github.com/jglick/simple-maven-project-with-tests.git'
     //  sh "sudo cp -pr /root/dbchanges ."
      // sh "ls -la"
    }
   // stage('Update') {
        // Run the liquibase commands
      //  def location = "dbchanges/"+env
       // dir(location) {
        //    echo "the env is ${env}"
          //   sh "pwd"
             //sh "ls -la"
           // sh "sudo liquibase --defaultsFile=liquibase-${env}.properties update"
        }
    }
    
}
