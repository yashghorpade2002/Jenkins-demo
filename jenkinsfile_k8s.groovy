pipeline{
    agent any
    stages{
        stage('start minikube') {
            steps {
                sh 'echo "checking the minikube status and starting it"'
                sh 'whoami'
                sh 'minikube start --device=docker'
            }
        }
        stage('apply deployment') {
            steps {
                scripts{
                    dir("k8s_practice"){
                        sh 'echo "getting the deployment file applied"'
                        sh 'kubectl apply -f deployment.yaml'
                    }
                }
            }
        }
        stage('apply service') {
            steps {
                scripts {
                    dir("k8s_practice") {
                        sh 'echo "getting the service file applied"'
                        sh 'kubectl apply -f service.yaml'
                    }
                }
            }
        }
    }
    post{
        success{
            echo 'deployment successfull'
        }
        failure{
            echo 'deployment failure'
        }
        always{
            echo 'Pipeline finished'
        }
        cleanup{
            echo "cleaning up the resources"
        }
    }
}