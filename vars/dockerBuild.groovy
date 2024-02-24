def call(String imageName, String imageTag, String hubUser){
    sh """
     docker image build -t ${hubUser}/${imageName} .
     docker image tag ${hubUser}/${imageName} ${hubUser}/${imageName}:${imageTag}
     docker image tag ${hubUser}/${imageName} ${hubUser}/${imageName}:latest
    """
}