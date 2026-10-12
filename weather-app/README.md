# Weather App (Spring Boot + OpenWeatherMap)

## Run locally (needs JDK 17 + Maven)
    mvn spring-boot:run
Open http://localhost:8080

## Run with Docker
    docker build -t weather-app .
    docker run -d --name weather -p 8080:8080 --env-file .env weather-app
Open http://localhost:8080  (JSON: /api/weather?city=Mumbai)

## API key
Put it in `.env`:  OPENWEATHER_API_KEY=your_key
`.env` is git-ignored and docker-ignored.

## Push to Git
    git init
    git add .
    git commit -m "Spring Boot weather app with Docker"
    git branch -M main
    git remote add origin https://github.com/<username>/weather-app.git
    git push -u origin main
