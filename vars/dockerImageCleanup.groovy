def call(String imageName, String imageTag, String hubUser){
    sh """
     docker rmi ${hubUser}/${imageName}:${imageTag}
     docker rmi ${hubUser}/${imageName}:latest
    """
}