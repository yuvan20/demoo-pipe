# cicd-liquibase
## Introduction
Liquibase is an open-source solution for managing revisions of your database schema scripts. 
-	It works across various types of databases and supports various file formats for defining the DB structure. 
-	Liquibase is its ability to roll changes back and forward from a specific point — saving you from needing to know what the last change/script was you ran on a specific DB instance.

Liquibase uses scripts — referred to as "changesets" — to manage the changes you do to your DB. The changesets files can be in various formats including XML, JSON, YAML, and SQL. 
As you continue to change and enhance your DB structure through the development lifecycle, you'll add more changesets. A master file lists all the changeset files (or the directories where they are). In parallel, Liquibase tracks in your database which changesets have already run.

When you issue a Liquibase update command, Liquibase looks at the current state of your DB and identifies which changes have already happened. Then, it runs the rest of the changes, getting you to the latest revision of the structure you are defining.

By integrating Liquibase into your overall code version management system and continuous integration platform, you can sync up your database versions with your app version. 

Liquibase uses a changelog to explicitly list database changes in order. The changelog acts as a ledger of changes and contains a list of changesets (units of change) that Liquibase can execute on a database.
Tracking tables
Liquibase tracks which changeSets have or have not been deployed in a tracking table called a DATABASECHANGELOG. If your database does not already contain a tracking table, Liquibase will create it for you.
Changelogs and tracking tables allow Liquibase to:Track and version database changes
Users know which changes have been deployed to the database and which changes have not yet been deployed.
Deploy changes
Liquibase compares the changelog against the tracking table and only deploys changes that have not been deployed to the database.
Liquibase also has advanced features such as contexts, labels, and preconditions to precisely control when and where a changeset is deployed.


### Install Liquibase in Jenkins Linux server

```
$ wget https://github.com/liquibase/liquibase/releases/download/v3.10.2/liquibase-3.10.2.tar.gz
```

### Changelog.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd">

   <include file="changesets/script1.sql" relativeToChangelogFile="true"/>
   <include file="changesets/script2.sql" relativeToChangelogFile="true"/>

</databaseChangeLog>
```
### liquibase-prod.properties
```driver=com.mysql.cj.jdbc.Driver
classpath=/opt/liquibase-bin/drivers/mysql-connector-java-8.0.21/mysql-connector-java-8.0.21.jar
url=jdbc:mysql://myrdsdb.#.us-east-2.rds.amazonaws.com:3306/rdsdb
changeLogFile=changelog.xml
logLevel: DEBUG
```

### changesets - script1.sql

```--liquibase formatted sql
--changeset dbadmin:4
create table devops (
    id int primary key,
    name varchar(50) not null,
    year varchar(50),
    domain varchar(30)
);
```
### Jenkinsfile
```
Jenkinsfile with password
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
```
