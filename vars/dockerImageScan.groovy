def call(String imageName, String imageTag, String hubUser){
    sh """
     trivy image ${hubUser}/${imageName} ${hubUser}/${imageName}:latest > scan.txt
     cat scan.txt
    """
}