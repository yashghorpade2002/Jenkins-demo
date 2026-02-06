pipeline{
    agent any
    stages{
        stage('start minikube') {
            steps {
                sh 'echo "checking the minikube status and starting it"'
                sh 'minikube start -- device = docker'
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
        failuare{
            echo 'deployment failuare'
        }
        always{
            echo 'Pipeline finished'
        }
        cleanup{
            steps{
                sh 'echo "cleaning up the resources"'
//                sh 'sudo rm -rf /workspace/*'
            }
        }
    }
}