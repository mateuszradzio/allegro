# **Allegro REST API Test**
Table of Contents
 1. [Introduction](#introduction)
 2. [How to run](#how-to-run)
 
## Introduction
Hello Everyone!
My task was to perform acceptance tests for 3 GETs:
- GET IDs of Allegro categories
- GET a category by ID
- GET parameters supported by a category

As I have not received any documentation and due to that I have decided that the actual state is what I expect from those endpoints and saved it to JSONs.
 From that point, I was using those JSONs (with categories list and categories parameter lists) as my expected values. 
 I made only four tests that check all the fields of the objects at once. 
 For sure I could split them into smaller parts but without knowledge, witch parts are more likely to change I have not found a reason to do so. 
 I focused on building clean and maintainable architecture. From this point, it is easy to extend the whole project for other endpoints or write more tests for current ones.

One of the key features is using the [openapi generator](https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator-maven-plugin): <br>
Basing on allegro [swagger](https://developer.allegro.pl/swagger.yaml) generates all necessary objects (DTOs) to parse rest API responses to objects. 
Another benefit of using an openapi generator is that if something changes I can simply copy new swagger to the project and it will update automatically without interactions from the developer site. 
(Another idea is to add a new feature -> downloading swagger from remote directly during the build)

In the project I implemented some patterns like singleton (not thread-safe one) or some kind of Facade (AbstractApiClient class is a facade for RestAssured) and used some other patterns like builder)
## How to run
1. Clone and build project (Java 11+) `mvn clean install -DskipTests`
2. Download and install Lombok plugin then enable annotation processing
3. Set values in [configuration](src/main/resources/configuration.properties)
- `authorization.client_id` - ClientID from [here](https://apps.developer.allegro.pl/)
- `authorization.client_secret` - Client Secret from [here](https://apps.developer.allegro.pl/)
- `test_properties.update_data` - `true` - to generate updated JSONs from current API responses
4. Run one of the test located in [test](src/test) or run `mvn test`

  