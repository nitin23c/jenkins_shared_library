def call(String imageName, String imageTag, String hubUser){
   withCredentials([usernamePassword(
    credentialsId: 'docker', 
    passwordVariable: 'PASS', 
    usernameVariable: 'USER'
    )]) {
    sh "docker login -u '$USER' -p '$PASS'"
   }

   sh "docker image push ${hubUser}/${imageName}:${imageTag}"
   sh "docker image push ${hubUser}/${imageName}:latest"
}