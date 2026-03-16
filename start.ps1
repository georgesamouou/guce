Write-Host "=== Arret des containers et suppression des volumes ===" -ForegroundColor Yellow
docker-compose down -v

Write-Host "=== Build et lancement des containers ===" -ForegroundColor Green
docker-compose up -d --build

Write-Host ""
Write-Host "=== Services demarres ===" -ForegroundColor Cyan
Write-Host "Application:  http://localhost:8084/application-service"
Write-Host "Swagger UI:   http://localhost:8084/application-service/swagger-ui/index.html"
Write-Host "PostgreSQL:   localhost:5436"
Write-Host ""
Write-Host "Logs: docker-compose logs -f application-service" -ForegroundColor Gray
