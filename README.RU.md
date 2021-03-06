[![Build Status](https://travis-ci.org/ilya1291/zoo.svg?branch=master)](https://travis-ci.org/ilya1291/zoo)
[![codecov](https://codecov.io/gh/ilya1291/zoo/branch/master/graph/badge.svg)](https://codecov.io/gh/ilya1291/zoo)

# zoo
Zoo это backend часть для системы управления зоопарком. Бизнес логика этого приложения основана на тестовом задании найденном в свободном доступе в сети.
Приложение предоставляет RESTful API для любых клиентов таких как WEB-интерфейс или мобильное приложение.

## Бизнес требования ##
Приложение обеспечивает только авторизованный доступ к информации зоопарка. 
Отображает список животных, клеток в которых они живут, и смотрителей, ухаживающих за этими животными. 
Также приложение предоставляет возможность загрузки данных о животных в систему из XML, а также выгрузку подобной информации в XML файл. 

После входа в систему у пользователя есть возможность посмотреть список животных зоопарка. 
Для каждого животного есть возможность посмотреть следующую информацию:
1. Имя.
2. Вид.
3. Возраст.
4. Клетка, в которой живет.
5. Имя и Фамилия смотрителя.

Клетка имеет ограниченную вместимость.
Пользователь не может перемещать хищное животное в клетку к травоядным и наоборот.
Несколько животных могут жить в одной клетке. Один смотритель может ухаживать за несколькими животными.
В зоопарке не может быть двух животных с одинаковым именем.

### Выгрузка и загрузка списка животных в XML: ###
Пользователь может загрузить и выгрузить список животных в формате XML.
XML файл может быть очень большим и не помещаться в оперативную память сервера.

## Техническое описание ##
### Zoo состоит из следующих модулей: ###
1. **Zoo api** клиентский rest интерфейс, DTO и swagger документацию.
2. **Zoo db** содержит liquibase скрипты для миграции БД и начальное наполнение.
3. **Zoo impl** это главный модуль модуль приложения который содержит реализацию REST API и бизнес-логики.
4. **Zoo model** Доменная модель приложения. Содержит ORM маппинг для сущностей.

### Запуск: ###
Простейший способ запуска - это запустить в docker контейнерах.

1. Pull form github
    
    ```
    git clone https://github.com/ilya1291/zoo.git
    cd ./zoo/zoo-impl/src/main/resources/docker
    ```
2. Up containers

    `docker-compose up -d`
    
3. После старта откройте веб-браузер и переидите на страницу swagger: http://localhost:8080/swagger-ui.html
    
### Usage: ###
Для использования api необходимо авторизоваться. Процесс авторизации реализован через access token (JWT).
После старта приложение имеет двух пользователей:

| User name  | email            | password   |
|------------|------------------|------------|
| admin1     | admin1@zoo.com   | 123456     |
| manager1   | manager1@zoo.com | 123456     |

Admin может создавать учетные записи для менеджеров.

1. Вы можете войти в систему как администратор или менеджер используя метод `POST api/auth/login` для получения токена. 
   Например:
    ```$json
    {
      "usernameOrEmail": "admin1",
      "password": "123456"
    }
    ```

2. в качетсве ответа вы получите access token:
    ```$json
    {
      "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTU1MTg1MDIxLCJleHAiOjE1NTU3ODk4MjF9.1l5jdeEgJcRUCRMPIiQajwQikB6TPqAHlGo7KV4_YWe3o94tP_SfC2cqlCR6-9mv1BJx3IlpJe1d2-53FtozGw",
      "tokenType": "Bearer"
    }
    ```
3. Скпопируйте значение accessToken.
4. Нажмите **Authorize** кнопку вверху справа и вставте `bearer <accessToken>` в качетсве значения.
Например, для токена выше: 
     ```
     bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTU1MTg1MDIxLCJleHAiOjE1NTU3ODk4MjF9.1l5jdeEgJcRUCRMPIiQajwQikB6TPqAHlGo7KV4_YWe3o94tP_SfC2cqlCR6-9mv1BJx3IlpJe1d2-53FtozGw
     ```
     
### Схема БД ###
#### Доменная модель: ####
![](https://user-images.githubusercontent.com/4398894/56156649-5bcf8200-5fc6-11e9-838c-635a945094aa.png)

#### Ролевая модель: ####
![](https://user-images.githubusercontent.com/4398894/56157277-c3d29800-5fc7-11e9-8075-0f9570f29d53.png)