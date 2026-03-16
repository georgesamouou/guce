#!/bin/bash
echo "================================================="
echo "   FIMEX Application Service - Docker Launcher   "
echo "================================================="

echo "1. Stopping any existing containers and removing volumes..."
docker-compose down -v

echo "2. Building the application and starting the cluster..."
docker-compose up -d --build

echo ""
echo "================================================="
echo "   Deployment completed successfully!            "
echo "================================================="
echo "Services:"
echo " - Application Service : http://localhost:8084/application-service"
echo " - Swagger UI          : http://localhost:8084/application-service/swagger-ui/index.html"
echo " - PostgreSQL          : localhost:5436"
echo " - Kafka               : localhost:9093"
echo "================================================="

echo "You can view logs with: docker-compose logs -f application-service"
