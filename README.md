# Meu CRUD

Este é um projeto de exemplo de uma aplicação CRUD desenvolvida em Clojure, utilizando Docker, Kubernetes, Minikube e DynamoDB na AWS.

## Funcionalidades

- Criar, ler, atualizar e deletar dados usando DynamoDB.
- Implementação de containers com Docker.
- Orquestração de containers com Kubernetes.
- Desenvolvimento local utilizando Minikube.

## Pré-requisitos

- Docker
- Minikube
- Kubernetes CLI (`kubectl`)
- AWS CLI (configurado com suas credenciais)

## Configuração do Ambiente

### 1. Clonar o Repositório

git clone https://github.com/seu-usuario/my-crud-app.git
cd my-crud-app

### Construir a Imagem Docker
Configure o Docker para usar o ambiente Minikube e construa a imagem Docker:

eval $(minikube docker-env)
docker build -t my-crud-app .


### Criar o Secret AWS no Kubernetes

Crie um secret com suas credenciais AWS:
kubectl create secret generic aws-secret --from-literal=AWS_ACCESS_KEY_ID=SUA_access_key_id --from-literal=AWS_SECRET_ACCESS_KEY=SUA_secret_access_key

### Implantar no Kubernetes
Crie os manifests do Kubernetes para o deployment e o service:

#### deployment.yaml

apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-crud-app
spec:
  replicas: 1
  selector:
    matchLabels:
      app: my-crud-app
  template:
    metadata:
      labels:
        app: my-crud-app
    spec:
      containers:
      - name: my-crud-app
        image: my-crud-app:latest
        imagePullPolicy: Never
        ports:
        - containerPort: 3000
        env:
        - name: AWS_REGION
          value: "sa-east-1"
        - name: AWS_ACCESS_KEY_ID
          valueFrom:
            secretKeyRef:
              name: aws-secret
              key: AWS_ACCESS_KEY_ID
        - name: AWS_SECRET_ACCESS_KEY
          valueFrom:
            secretKeyRef:
              name: aws-secret
              key: AWS_SECRET_ACCESS_KEY     

#### service.yaml

apiVersion: v1
kind: Service
metadata:
  name: my-crud-app-service
spec:
  selector:
    app: my-crud-app
  ports:
    - protocol: TCP
      port: 80
      targetPort: 3000
  type: LoadBalancer


### Aplicar os Manifests no Kubernetes
Copiar código
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml

### Verificar o Status dos Pods
Copiar código
kubectl get pods

### Acessar os Logs dos Pods
Copiar código
kubectl logs <pod-name>

## Uso
Depois que os pods estiverem em execução, você pode acessar a aplicação através do IP externo fornecido pelo serviço LoadBalancer.