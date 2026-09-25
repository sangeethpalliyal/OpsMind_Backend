# OpsMind – Autonomous AI DevOps Agent

## Current architecture
- Eureka Discovery Server: 8761
- API Gateway: 8080
- User Service: 8081 (H2 + JPA + BCrypt + JWT issuance)
- Analysis Service: 8082 (H2 + static rules + optional Spring AI + Kafka producer)
- Deployment Service: 8083 (Kafka consumer + staging decision records)
- Legacy Service: 8084 (legacy-system simulation)

## Prerequisites
- JDK 21 (JDK 17+ is supported by the current Spring Boot line)
- Maven 3.6.3+
- Git
- Kafka on localhost:9092 for asynchronous deployment events
- Optional: OpenAI API key for real Spring AI review
- IDE: IntelliJ IDEA Community/Ultimate, Eclipse, or VS Code with Java extensions

## Build
From this folder:
```bash
mvn clean package -DskipTests
```

## Run without IDE
Start each service in a separate terminal:
```bash
cd discovery-service && mvn spring-boot:run
cd user-service && mvn spring-boot:run
cd analysis-service && mvn spring-boot:run
cd deployment-service && mvn spring-boot:run
cd legacy-service && mvn spring-boot:run
cd api-gateway && mvn spring-boot:run
```

Start Eureka first. Then the other services. Give Eureka 20–30 seconds to register services before testing through the gateway.

## Kafka
Create Kafka locally and make sure a broker is listening on localhost:9092. Create topic:
```bash
kafka-topics --create --topic opsmind.analysis.completed --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```
If Kafka is not running, the HTTP analysis endpoint still saves its result; the Kafka publish is intentionally guarded so the beginner demo remains usable. The deployment consumer will only work when Kafka is available.

## Test the project
Register:
```bash
curl -X POST http://localhost:8080/api/users/register -H "Content-Type: application/json" -d '{"username":"sangeeth","password":"password123"}'
```
Login:
```bash
curl -X POST http://localhost:8080/api/users/login -H "Content-Type: application/json" -d '{"username":"sangeeth","password":"password123"}'
```
Webhook-shaped code review:
```bash
curl -X POST http://localhost:8080/api/analysis/webhook -H "Content-Type: application/json" -d '{"repository":"demo-repo","pullRequest":"42","code":"public void test(){ for(int i=0;i<n;i++){ for(int j=0;j<n;j++){ System.out.println(i+j); } } }"}'
```
List results:
```bash
curl http://localhost:8080/api/analysis
```
Manual deployment decision:
```bash
curl -X POST http://localhost:8080/api/deployments -H "Content-Type: application/json" -d '{"analysisId":1,"green":true}'
```
Legacy portal:
```bash
curl http://localhost:8080/legacy
```

## Enable real Spring AI
PowerShell:
```powershell
$env:OPENAI_API_KEY="YOUR_KEY"
```
Then change `analysis-service/src/main/resources/application.yml`:
```yaml
opsmind:
  ai:
    enabled: true
```
Restart analysis-service. Do not commit API keys.

## GitHub webhook next step
For a real GitHub integration, create a webhook on the repository pointing to:
`POST /api/analysis/webhook`
with JSON containing repository, pull request number, and changed code. In production, add HMAC signature verification and use the GitHub REST API to fetch the PR diff. The included endpoint is intentionally simplified so a fresher can understand the pipeline first.

