<h2>Digital Innovation: Expert class - Desenvolvimento de testes unitários para validar uma API REST de gerenciamento de estoques de cerveja.</h2>

Nesta live coding, vamos aprender a testar, unitariamente, uma API REST para o gerenciamento de estoques de cerveja. Vamos desenvolver testes unitários para validar o nosso sistema de gerenciamento de estoques de cerveja, e também apresentar os principais conceitos e vantagens de criar testes unitários com JUnit e Mockito. Além disso, vamos também mostrar como desenvolver funcionalidades da nossa API através da prática do TDD.

Durante a sessão, serão abordados os seguintes tópicos:

* Baixar um projeto através do Git para desenolver nossos testes unitários. 
* Apresentação conceitual sobre testes: a pirâmide dos tipos de testes, e também a importância de cada tipo de teste durante o ciclo de desenvolvimento.
* Foco nos testes unitários: mostrar o porque é importante o desenvolvimento destes tipos de testes como parte do ciclo de desenvolvimento de software.
* Principais frameworks para testes unitários em Java: JUnit, Mockito e Hamcrest. 
* Desenvolvimento de testes unitários para validação de funcionalides básicas: criação, listagem, consulta por nome e exclusão de cervejas.
* TDD: apresentação e exemplo prático em 2 funcionaliades importantes: incremento e decremento do número de cervejas no estoque.

Para executar o projeto no terminal, digite o seguinte comando:

```shell script
mvn spring-boot:run 
```

Para executar a suíte de testes desenvolvida durante a live coding, basta executar o seguinte comando:

```shell script
mvn clean test
```

Após executar o comando acima, basta apenas abrir o seguinte endereço e visualizar a execução do projeto:

```
http://localhost:8080/api/v1/beers
```

São necessários os seguintes pré-requisitos para a execução do projeto desenvolvido durante a aula:

* Java 14 ou versões superiores.
* Maven 3.6.3 ou versões superiores.
* Intellj IDEA Community Edition ou sua IDE favorita.
* Controle de versão GIT instalado na sua máquina.
* Muita vontade de aprender e compartilhar conhecimento :)

Abaixo, seguem links bem bacanas, sobre tópicos mencionados durante a aula:

* [SDKMan! para gerenciamento e instalação do Java e Maven](https://sdkman.io/)
* [Referência do Intellij IDEA Community, para download](https://www.jetbrains.com/idea/download)
* [Palheta de atalhos de comandos do Intellij](https://resources.jetbrains.com/storage/products/intellij-idea/docs/IntelliJIDEA_ReferenceCard.pdf)
* [Site oficial do Spring](https://spring.io/)
* [Site oficial JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
* [Site oficial Mockito](https://site.mockito.org/)
* [Site oficial Hamcrest](http://hamcrest.org/JavaHamcrest/)
* [Referências - testes em geral com o Spring Boot](https://www.baeldung.com/spring-boot-testing)
* [Referência para o padrão arquitetural REST](https://restfulapi.net/)
* [Referência pirâmide de testes - Martin Fowler](https://martinfowler.com/articles/practical-test-pyramid.html#TheImportanceOftestAutomation)

[Neste link](https://drive.google.com/file/d/1KPh19mvyKirorOI-UsEYHKkmZpet3Ks6/view?usp=sharing), seguem os slides apresentados como o roteiro utilizado para o desenvolvimento do projeto da nossa sessão.

<h2> Database </h2>

| Config H2 | Url : http://localhost:8080/h2-console/ | 
|-----------|:---------------------------------------:|
|Saved Settings | Generic H2 (Embeded) |  
|Setting Name | Generic H2 (Embeded) |	
|Driver Class |	org.h2.Driver |
| JDBC URL | jdbc:h2:mem:beerstock |
|User Name | sa | 	
|Password | |


<h3> BeerStock API Specification v1.0 </h3>
Verbs Http:

| Uri | Method| Description      | Request Stream | Response Steam | Status Code Return |    
|:----|:------|:-----------------|:---------------|:---------------|:-------------------|
| /person | GET | all people in the system | n/a  | Person Collection| 200/204 |
| /person/{id} | GET | Get speficic person | n/a  | Person | 200/2004         |
| /person | POST | Creates a new entity person in the system. Expects a represention of the person in the body | Person without the Id specifield | Person | 201 / 204 |
| /person/{id} | PUT | Modifies a person resource | Person | n/a | 201 / 204 |
| /person/{id} | DELETE | Deletes a person resource | Person | n/a | 201 / 204 |



<h3> CheckList Resources </h3>

| Uri | Method| Service          | Controller     | URL Example        | Body Example | Return |        
|:----|:------|:-----------------|:---------------|:-------------------|:-------------|:-------|
| /api/v1/beers | GET | [ ] | [ ]  | http://localhost:8080/api/v1/beers |    | [ { "id": 1, "name": "Stella Artois", "brand": "AmBev", "max": 10, "quantity": 2, "type": "LAGER"  } ] |
| /api/v1/beers/{name} | GET | [ ] | [ ]  | http://localhost:8080/api/v1/beers/Stella Artois | | { "id": 1, "name": "Stella Artois", "brand": "AmBev",  "max": 10, "quantity": 2, "type": "LAGER" }|
| /api/v1/beers | POST | [ ] | [ ]  | http://localhost:8080/api/v1/beers |    {  "name": "Stella Artois", "brand": "AmBev", "max": 10, "quantity": 2, "type": "LAGER" } | { "id": 1, "name": "Stella Artois", "brand": "AmBev", "max": 10, "quantity": 2, "type": "LAGER" }  |
| /api/v1/beers/{id} | PUT | [ ] | [ ]  | http:// | | |
| /api/v1/beers/{id} | DELETE | [ ] | [ ]  | http://localhost:8080/api/v1/beers/1 | | |
| /api/v1/beers/1/increment{id} | PATCH | [ ] | [ ]  | http://localhost:8080/api/v1/beers/1/increment | |{ "id": 1, "name": "Stella Artois","brand": "AmBev", "max": 10, "quantity": 2, "type": "LAGER"}|


<h3> Checklist Controller </h3>

| Checked| Uri | Method| BeerController |         
| -------|:----|:------|:---------------|
|[X]  | /api/v1/beers | GET | whenGETListWithBeersIsCalledThenOkStatusIsReturned() |
|[X]  | /api/v1/beers | GET | whenGETListWithoutBeersIsCalledThenOkStatusIsReturned() |
|[X]  | /api/v1/beers/{name} | GET | whenGETIsCalledWithValidNameThenOkStatusIsReturned()  | 
|[X]  | /api/v1/beers/{name} | GET | whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned |
|[X]  | /api/v1/beers | POST | whenPOSTIsCalledThenABeerIsCreated() | 
|[X]  | /api/v1/beers | POST | whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() |
|[X]  | /api/v1/beers/{id} | PUT |   | 
|[X]  | /api/v1/beers/{id} | DELETE | whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() |
|[X]  | /api/v1/beers/{id} | DELETE | whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() |
|[X]  | /api/v1/beers/1/increment{id} | PATCH |  whenPATCHIsCalledToIncrementDiscountThenOKstatusIsReturned() | 


<h3> Checklist Service </h3>

| Checked| Method BeerServices | Test |        
|--------|:--------------------|------|
|[X]     | createBeer(BeerDTO beerDTO) | whenBeerInformedThenItShouldBeCreated() |
|[X]     | createBeer(BeerDTO beerDTO) | whenAlreadyRegisteredBeerInformedThenAnExceptionShouldBeThrown() |
|[X]     | findByName(String name) | whenValidBeerNameIsGivenThenReturnABeer() |
|[X]     | findByName(String name) | whenNotRegisteredBeerNameIsGivenThenThrowAnException() |
|[X]     | listAll() | whenListBeerIsCalledThenReturnAListOfBeers() |
|[X]     | listAll() | whenListBeerIsCalledThenReturnAnEmptyListOfBeers() |
|[X]     | deleteById(Long id) | whenExclusionIsCalledWithValidIdThenABeerShouldBeDeleted() |
|[X]     | increment(Long id, int quantityToIncrement) | whenIncrementIsCalledThenIncrementBeerStock() |
|[X]     | increment(Long id, int quantityToIncrement) | whenIncrementIsGreatherThanMaxThenThrowException() |
|[X]     | increment(Long id, int quantityToIncrement) | whenIncrementAfterSumIsGreatherThanMaxThenThrowException() |
|[X]     | increment(Long id, int quantityToIncrement) |whenIncrementIsCalledWithInvalidIdThenThrowException() |



