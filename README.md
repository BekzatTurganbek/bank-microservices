# bank-microservices

## Описание
Это микросервис для обработки банковских транзакций и управления лимитами расходов. Он предоставляет API для создания, обработки и мониторинга транзакций в различных валютах, а также позволяет устанавливать и контролировать месячные лимиты расходов.

## Требования
Java 11+: Основной язык программирования для проекта.
Maven: Для управления зависимостями и сборки проекта.
Docker: Для контейнеризации приложения и упрощения развертывания.
PostgreSQL: В качестве основной базы данных для хранения транзакций и лимитов.

## Установка

1. Клонируйте репозиторий:
   git clone https://github.com/ваш_репозиторий/bank-microservice.git
   cd bank-microservice
   
2. Настройте базу данных PostgreSQL:
   Установите PostgreSQL, если он не установлен:
   sudo apt-get install postgresql postgresql-contrib
   Создайте базу данных:
   psql -U postgres -c "CREATE DATABASE bank_db;"

3. Настройте переменные окружения:
   Создайте файл .env в корневой папке проекта и добавьте следующие переменные:
   DB_HOST=localhost
   DB_PORT=5432
   DB_NAME=bank_db
   DB_USERNAME=postgres
   DB_PASSWORD=your_password

4. Соберите проект с помощью Maven:
   mvn clean install

5. Соберите Docker-образ:
   docker build -t bank-microservice .
   Запустите контейнер:
   docker run --env-file .env -p 8080:8080 bank-microservice

## Использование
   После запуска микросервиса, API будет доступно по адресу http://localhost:8080. Вот некоторые из доступных API-эндпоинтов:

   POST /transactions: Создание новой транзакции.
   GET /transactions/{id}: Получение деталей транзакции по ID.
   POST /limits: Установка месячного лимита расходов.
   GET /limits/{id}: Получение информации о месячном лимите по ID.

## Примеры запросов

1. Создание транзакции:
   curl -X POST http://localhost:8080/transactions \
   -H "Content-Type: application/json" \
   -d '{"amount": 100, "currency": "USD", "description": "Grocery shopping"}'

2. Установка лимита расходов:
   curl -X POST http://localhost:8080/limits \
   -H "Content-Type: application/json" \
   -d '{"amount": 1000, "currency": "USD", "month": "2024-08"}'

## Тестирование
   Проект включает модульные тесты для проверки различных функциональностей. Для запуска тестов используйте следующую команду:
   mvn test

## Развертывание
   Для развертывания приложения в продакшн среде можно использовать Docker Compose или Kubernetes. Настройте необходимые конфигурации в файле docker- 
   compose.yml или kubernetes.yml.

## Поддержка
   Если у вас возникли вопросы или проблемы с проектом, пожалуйста, создайте issue на GitHub или свяжитесь с автором проекта.
