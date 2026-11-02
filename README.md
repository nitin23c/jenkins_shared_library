# jenkins_shared_library

Reusable steps for Jenkins pipelines that test, scan, build and publish a Maven
application as a container image. Pipelines call one short step per stage instead
of repeating shell commands.

[java_app](https://github.com/nitin23c/java_app) is a demo application whose
`Jenkinsfile` uses this library, and
[cicd_server_build](https://github.com/nitin23c/cicd_server_build) provisions a
server to run it on.

## Steps

| Group | Step | What it does |
|---|---|---|
| Git | `gitCheckout(branch:, url:, credentialsId:)` | Checks out a repository |
| Maven | `mvnTest()` | Runs unit tests |
| Maven | `mvnIntegrationTest()` | Runs `mvn verify` without unit tests |
| Maven | `mvnBuild()` | Runs `mvn clean install` |
| SonarQube | `staticCodeAnalysis(credentialsId)` | Runs the Sonar analysis |
| SonarQube | `qualityGateStatus(credentialsId)` | Waits for the quality gate result, without aborting the pipeline |
| Docker Hub | `dockerBuild(imageName, imageTag, hubUser)` | Builds and tags the image |
| Docker Hub | `dockerImageScan(imageName, imageTag, hubUser)` | Scans the image with Trivy into `scan.txt` |
| Docker Hub | `dockerImagePush(imageName, imageTag, hubUser)` | Pushes the image |
| Docker Hub | `dockerImageCleanup(imageName, imageTag, hubUser)` | Removes the local images |
| ECR | `ecrBuild(aws_account_id, region, ecr_repo_name)` | Builds and tags the image |
| ECR | `ecrImageScan(aws_account_id, region, ecr_repo_name)` | Scans the image with Trivy into `scan.txt` |
| ECR | `ecrImagePush(aws_account_id, region, ecr_repo_name)` | Logs in to ECR and pushes the image |
| ECR | `ecrImageCleanup(aws_account_id, region, ecr_repo_name)` | Removes the local images |

## Usage

Add this repository in Jenkins as a Global Pipeline Library, then load it by the
name you gave it:

```groovy
@Library('my-jenkins-library') _

pipeline {
    agent any
    stages {
        stage('Unit tests') {
            steps {
                script {
                    mvnTest()
                }
            }
        }
        stage('Build image') {
            steps {
                script {
                    dockerBuild('my-app', 'v1', 'my-dockerhub-user')
                }
            }
        }
    }
}
```

The Docker and Trivy steps need `docker` and `trivy` on the agent, and the ECR steps
also need the AWS CLI.
