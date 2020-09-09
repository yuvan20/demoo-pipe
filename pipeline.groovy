properties([parameters([choice(choices: ['prod', 'qa'], description: 'Target environment', name: 'DEPLOY_ENV')])])
node {
    def env
    env = "${params.DEPLOY_ENV}"
    echo "Current environment is ${env}"
    stage('SCM Checkout') { 
        git 'https://github.com/mohsin996/cicd-liquibase.git'
    }
    stage('LiquibaseUpdate') {
        // Run the liquibase commands
        withCredentials(bindings: [usernamePassword(credentialsId: 'prod_rds', \
                                                       passwordVariable: 'pass', \
                                                       usernameVariable: 'key')]) {                                                
            dir(env) {
                echo "the env is ${env}"
                sh "pwd"
                def output = sh (script: "sudo liquibase --defaultsFile=liquibase-${env}.properties --username=$key --password=$pass update",returnStdout:true).trim()
                echo " ${output}"
            }
        }
    } 
}
