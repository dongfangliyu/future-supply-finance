sleep 100
java -Djava.security.egd=file:/dev/./urandom -Dspring.config.location=/config/application.yml -jar /app/app.jar