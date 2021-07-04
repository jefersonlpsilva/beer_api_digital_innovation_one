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


<h3> BeerStock API Specification 0.0.1 </h3>

<h2> Minhas melhorias </h2>
<h3> CheckList Resources </h3>
Verbs Http:

| Uri                       | Method| Description      | Request Stream | Response Steam | Status Code Return |    
|:------------------------- |:------|:-----------------|:---------------|:---------------|:-------------------|
| /api/v1/beers/{id}        | PUT | Modifies a beer resource | Beer | n/a | 201 / 204 |
| /api/v1/foodpairing       | GET | all food pairing in the system | n/a  | Food pairing Collection| 200/204 |
| /api/v1/foodpairing/{id}  | GET | Get speficic food pairing | n/a  | Food pairing | 200/2004         |
| /api/v1/foodpairing       | POST | Creates a new entity food pairing in the system. Expects a represention of the food pairing in the body | Beer without the Id specifield | Beer | 201 / 204 |
| /api/v1/foodpairing/{id}  | DELETE | Deletes a person resource | Person | n/a | 201 / 204 |


| Checked| Uri                | Method Verb      | URL Example                          | Body Example | Return       |
|:------:| :------------------|:-----------------|:-------------------------------------|:-------------|:-------------|
| [X]    | /api/v1/beers/{id}                         | PUT  | http://localhost:8080/api/v1/beers/1     |  { "id": 1, "name": "Stella Artois", "brand": "AmBev", "max": 10, "quantity": 2, "type": "LAGER"  }     | { "id": 1, "name": "Stella Artois", "brand": "AmBev", "max": 10, "quantity": 2, "type": "LAGER"  }     | { "id": 1, "name": "Stella Artois", "brand": "AmBev", "max": 10, "quantity": 2, "type": "LAGER"  }     | { "id": 1, "name": "Stella Artois", "brand": "AmBev", "max": 10, "quantity": 2, "type": "LAGER"  } |
| [X]    | /api/v1/foodpairing                        | POST | http://localhost:8080/api/v1/foodpairing |  { "dishName": "Frites and Fried Foods", "recipe": "Even if the Belgians ...", "temperature": "HOT" }           | { "id": 1,  "dishName": "Frites and Fried Foods", "recipe": "Even ",  "temperature": "HOT"}    |
| [X]    | /api/v1/foodpairing/Frites and Fried Foods | GET  | http://localhost:8080/api/v1/foodpairing/Frites and Fried Foods |    | { "id": 1,  "dishName": "Frites and Fried Foods",  "recipe": "Even if the Belgians ..",   "temperature": "HOT" } |    
| [X]    | /api/v1/foodpairing                        | GET  | http://localhost:8080/api/v1/foodpairing                        |    | { "dishName": "Frites and Fried Foods", "recipe": "Even if the Belgians ...", "temperature": "HOT" } | { "id": 1,  "dishName": "Frites and Fried Foods", "recipe": "Even ...",  "temperature": "HOT"} |    
| [X]    | /api/v1/foodpairing/{id}                   | PUT  | http://localhost:8080/api/v1/foodpairing/1                      |    | { "dishName": "Frites and Fried Foods", "recipe": "Even if the Belgians ...", "temperature": "HOT" } | { "id": 1,  "dishName": "Frites and Fried Foods", "recipe": "Even ...",  "temperature": "HOT"} |
| [X]    | /api/v1/foodpairing/{id}                   | DELTE | http://localhost:8080/api/v1/foodpairing/1                     |    |                                                                                                      |                                                                                                |

<h3> Checklist BeerController </h3>

| Checked| Uri | Verb | Test Controller |         
|:------:|:----|:------|:---------------|
|[X]     | /api/v1/beers/{id}        | PUT | whenPUTIsCalledThenABeerIsShoudBeSave() |
|[X]     | /api/v1/beers/{id}        | PUT | whenPUTIsCalledWithInvalidIdThenNotFoundStatusIsReturned() |
|[X]     | /api/v1/foodpairing       | POST| whenPostIsCalledThenAFoodPairingIsCreated() | 
|[X]     | /api/v1/foodpairing       | POST| whenPostIsCalledWithoutRequiredFieldThenAnErrorIsReturned() |
|[X]     | /api/v1/foodpairing       | GET | whenGetIsCalledWithRegisteredDishNameThenFoundStatusIsReturned() |
|[X]     | /api/v1/foodpairing       | GET | whenGetIsCalledWithoutRegisteredDishNameThenNotFoundStatusIsReturned() |
|[X]     | /api/v1/foodpairing/{}    | GET | whenGetListWithFoodPairingIsCalldedThenOkStatusIsReturn() |
|[X]     | /api/v1/foodpairing/{}    | GET | whenGetListWithoutFoodPairingsIsCalledThenOkStatusIsReturned() |
|[X]     | /api/v1/foodpairing/{id}  | DELETE | whenDeleteIsCalledWithValidIdThenNoContentStatusIsReturned() |
|[X]     | /api/v1/foodpairing/{id}  | DELETE | whenDeleteIsCalledWithInvalidIdThenNotFoundStatusIsReturned() |
|[X]     | /api/v1/foodpairing/{id}  | PUT    | whenPUTIsCalledThenAFoodPairingIsShoudBeSave() |
|[X]     | /api/v1/foodpairing/{id}  | PUT    | whenPUTIsCalledWithInvalidIdThenNotFoundStatusIsReturned() |

<h3> Checklist Service </h3>

| Checked| Method BeerServices | Test |        
|:------:|:--------------------|------|
|[X]     | updateBeer(@PathVariable Long id, @RequestBody @Valid BeerDTO beerDTO) | whenBeerInformedThenItShouldBeCreated() |        
|[X]     | updateBeer(@PathVariable Long id, @RequestBody @Valid BeerDTO beerDTO) | whenPUTIsCalledWithInvalidIdThenNotFoundStatusIsReturned() |
|[X]     | createFoodPairing(FoodPairingDTO foodPairingDTO) | whenFoodPairingInformedThenItShouldBeCreated() |
|[X]     | createFoodPairing(FoodPairingDTO foodPairingDTO) | whenAlreadyRegisteredFoodPairingInformedThenAnExceptionShouldBeThrown() |
|[X]     | findByDishName(String dishName)  | whenValidFoodPairingNameIsGivenThenReturnAFoodPairing() |
|[X]     | findByDishName(String dishName)  | whenNotRegisteredFoodPairingNameIsGivenThenThrowAnException() |
|[X]     | foodPairingService.listAll() | whenListFoodPairingIsCalledThenReturnAListOfFoodPairings() |
|[X]     | foodPairingService.listAll() | whenListFoodPairingIsCalledThenReturnAnEmptyListOfFoodPairings() |
|[X]     | updateById(Long id, FoodPairingDTO foodPairingDTO) | whenUpdatedFoodPairingByIdThenItShouldBeSave() |        
|[X]     | updateById(Long id, FoodPairingDTO foodPairingDTO) | whenUpdateFoodPairingThenAnExceptionShouldBeThrown() |              
|[X]     | deleteById(Long id) | whenExclusionIsCalledWithValidIdThenAFoodPairingShouldBeDeleted() |

<h2> Versão  inicial realizada pelo instrutor </h2>
<h3> BeerStock API Specification v1.0 </h3>
Verbs Http:

| Uri | Method| Description      | Request Stream | Response Steam | Status Code Return |    
|:----|:------|:-----------------|:---------------|:---------------|:-------------------|
| /api/v1/beers | GET | all beers in the system | n/a  | Beers Collection| 200/204 |
| /api/v1/beers/{id} | GET | Get speficic beer | n/a  | Beers | 200/2004         |
| /api/v1/beers | POST | Creates a new entity beer in the system. Expects a represention of the beer in the body | Beer without the Id specifield | Beer | 201 / 204 |
| /api/v1/beers/{id} | DELETE | Deletes a beer resource | Beer | n/a | 201 / 204 |
| /api/v1/beers/1/increment|PATCH | Modifies a person resource with increment the Quantity of Beers | Info quantity | Beer | 200 / 400  | 
| /api/v1/beers/1/increment|PATCH | Modifies a person resource with decrement the Quantity of Beers | Info quantity | Beer | 200 / 400  |



<h3> CheckList Resources </h3>

| Checked| Uri                          | Verb                 | URL Example                                    | Body Example       | Return       |        
| :-----:|:----------------------------:|:---------------------|:----------------------------------------------:|:-------------------|:------------:|
| [X]    | /api/v1/beers                | GET                  | http://localhost:8080/api/v1/beers               |                  | [ { "id": 1, "name": "Stella Artois", "brand": "AmBev", "max": 10, "quantity": 2, "type": "LAGER"  } ] |
| [X]    | /api/v1/beers/{name}         | GET                  | http://localhost:8080/api/v1/beers/Stella Artois |                  | { "id": 1, "name": "Stella Artois", "brand": "AmBev",  "max": 10, "quantity": 2, "type": "LAGER" }
| [X]    | /api/v1/beers                | POST                 |  http://localhost:8080/api/v1/beers              | {  "name": "Stella Artois", "brand": "AmBev", "max": 10, "quantity": 2, "type": "LAGER" } | { "id": 1, "name": "Stella Artois", "brand": "AmBev", "max": 10, "quantity": 2, "type": "LAGER" }  |
| [X]    | /api/v1/beers/{id}           | DELETE               | http://localhost:8080/api/v1/beers/1             |                  |                                                                                                     |
| [X]    | /api/v1/beers/1/increment{id}| PATCH                | http://localhost:8080/api/v1/beers/1/increment   | { "quantity": 2 } | { "id": 1, "name": "Stella Artois", "brand": "AmBev", "max": 10, "quantity": 4, "type": "LAGER"  } |
| [X]    | /api/v1/beers/1/decrement{id}| PATCH                | http://localhost:8080/api/v1/beers/1/decrement   | { "quantity": 2 } | { "id": 1, "name": "Stella Artois", "brand": "AmBev", "max": 10, "quantity": 0, "type": "LAGER"  } |

<h3> Checklist BeerController </h3>

| Checked| Uri | Verb | Test |         
| -------|:----|:------|:---------------|
|[X]  | /api/v1/beers | GET | whenGETListWithBeersIsCalledThenOkStatusIsReturned() |
|[X]  | /api/v1/beers | GET | whenGETListWithoutBeersIsCalledThenOkStatusIsReturned() |
|[X]  | /api/v1/beers/{name} | GET | whenGETIsCalledWithValidNameThenOkStatusIsReturned()  | 
|[X]  | /api/v1/beers/{name} | GET | whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned |
|[X]  | /api/v1/beers | POST | whenPOSTIsCalledThenABeerIsCreated() | 
|[X]  | /api/v1/beers | POST | whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() | 
|[X]  | /api/v1/beers/{id} | DELETE | whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() |
|[X]  | /api/v1/beers/{id} | DELETE | whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() |
|[X]  | /api/v1/beers/1/increment{id} | PATCH |  whenPATCHIsCalledToIncrementDiscountThenOKstatusIsReturned() | 
|[X]  | /api/v1/beers/1/dement{id} | PATCH |  whenPATCHIsCalledWithInvalidBeerIdToDecrementThenNotFoundStatusIsReturned() |

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
|[X]     | increment(Long id, int quantityToIncrement) | whenIncrementIsCalledWithInvalidIdThenThrowException() |
|[X]     | decrement(Long id, int quantityToDecrement) | whenDecrementIsCalledThenDecrementBeerStock()() |
|[X]     | decrement(Long id, int quantityToDecrement) | whenDecrementIsCalledToEmptyStockThenEmptyBeerStock() |
|[X]     | decrement(Long id, int quantityToDecrement) | whenDecrementIsLowerThanZeroThenThrowException |
|[X]     | decrement(Long id, int quantityToDecrement) | whenDecrementIsCalledWithInvalidIdThenThrowException() |