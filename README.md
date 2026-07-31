# Instruction to use

## To run locally:

1. start database container

```bash
# Run from repository root
docker compose up -d postgres_db
```

2. start springboot locally

```cmd
cd finance_app
.\mvnw.cmd spring-boot:run
```

3. Test with:

```powershell
# Create a Transaction (Account #1 and Category #1 auto-created by Flyway V2)
$body = @{
    accountId = 1
    categoryId = 1
    amount = 250.75
    occurredAt = "2026-07-30T12:00:00"
    description = "Supermarket Grocery"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/transactions" -Method POST -Body $body -ContentType "application/json"

# Retrieve all transactions
Invoke-RestMethod -Uri "http://localhost:8080/transactions" -Method GET
```

## To run kubernetes a containerized deployment:

### Prerequisites

- Minikube or Docker Desktop Kubernetes enabled (minikube start).
- kubectl and helm CLIs installed.
- Important: Make sure port 8080 is free

1. Build docker immage and load it into minikube

```bash
# 1. Build local container image
docker build -t finance-api:v2 ./finance_app
# 2. Load image into Minikube cluster memory
minikube image load finance-api:v2
```

2. Deploy Kubernetes Manifests

```bash
kubectl apply -f k8s/
# verify they are running
kubectl get pods -w
```

3. port forward the cluster to the local machine

```bash
kubectl port-forward svc/finance-api 8080:8080
```

4. Test with:

```powershell
$body = @{
    accountId = 1
    categoryId = 1
    amount = 250.75
    occurredAt = "2026-07-30T12:00:00"
    description = "Supermarket Grocery"
} | ConvertTo-Json
Invoke-RestMethod -Uri "http://localhost:8080/transactions" -Method POST -Body $body -ContentType "application/json"
```

5. install and access grafna and prometheus for monitoring

```powershell
# 1. Update & install Prometheus stack via Helm
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update
helm install monitoring prometheus-community/kube-prometheus-stack -n monitoring --create-namespace
# 2. Port-forward Grafana UI to port 3000
kubectl port-forward svc/monitoring-grafana 3000:80 -n monitoring
# 3. Print Grafana 'admin' password
[System.Text.Encoding]::UTF8.GetString([System.Convert]::FromBase64String((kubectl get secret --namespace monitoring monitoring-grafana -o jsonpath="{.data.admin-password}")))
```

6. Open http://localhost:3000 in your browser (Username: admin, Password: printed above).