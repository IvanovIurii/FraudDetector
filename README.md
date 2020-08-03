# Весеннее загрузочное приложение

This is simple Spring Boot REST Api with only one endpoint to check the transaction, whether is fraudulent or not.
As a result user should get the list of issues he can potentially face.  

## Dependencies

1. Java 1.8
2. Maven
3. Docker (to run PostgreSQL)

See the `pom.xml` for details.

Here is also used external service `https://ip-api.com` to get a location by IP (IPv4/v6).

## HOW TO

To install Maven:
- MacOS
```
brew install maven
```
- Debian Linux
```
apt-get update && apt-get install maven
```

THEN:

1. use this command to run Postgres in Docker: `docker run --name test_postgres -p 54320:5432 -e POSTGRES_PASSWORD=password postgres:11`
2. then create DB in the container: `docker exec test_postgres psql -U postgres -c "CREATE DATABASE payments"`
3. you can check DB by: `psql -h localhost -p 54320 -U postgres`
4. then run the application: `mvn clean spring-boot:run`

Use Postman or CURL to send a request to the application.

CURL:
```
curl -d '{"email": "bob@gmail.com", "location": "13.61.89.122", "amount": 2000}' -H "Content-Type: application/json" -v -X POST http://localhost:8080/fraud-detection/transaction/check
```

For example, you can substitute location by new one: `203.61.80.122 - Australia` and eventually you will get an issue: `Suspicious location: Australia, St Leonards`,
because for the user `bob@gmail.com` last location was in Munich, Germany.

NOTE: the data is created all the time from scratch in Postgres DB.
You can look at the data available in `data.sql` file in `resources` directory.

## TESTS

Run unit-tests:
```
mvn clean test
```