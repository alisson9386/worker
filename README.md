# Projeto Worker
worker

Tecnologias utilizadas:
* [Spring Boot](https://spring.io/projects/spring-boot) 3.1.1
* Protocolo HTTP Rest

Componentes Utilizados:
* RabbitMQ
* MongoDB
* Spring Data MongoDB
* Redis
* Hibernate


Descrição:

> Projeto worker para consumo de fila RabbitMQ, pesistência em banco MySQL, MongoDB e Redis, e RESTful para consulto de dados completos e filtrados.
> O projeto tem como objetivo entender como funciona o consumo de filas e persistência em diversos tipos de bancos de forma simples, ele recebe dados pré estabelecidos de carros e realiza a persistência nos bancos.
> Conseguimos também através do protocolo REST consultar todos os carros salvos e buscar por chassi.
> Na raiz do projeto possui o arquivo workerDB.sql para criação do banco e o arquivo exemplosFila.json para adicionar dados fictícios na fila RabbitMQ para consumo.
> É necessário ter configurado no ambiente os bancos Redis, MongoDB e MySQL, e o RabbitMQ.

### Instalação

* [JDK 17+](https://www.oracle.com/java/technologies/downloads/)
* [Maven 3.8+](https://maven.apache.org/download.cgi)

Instalando e rodando o projeto:

```
$ git clone git@github.com:alisson9386/worker.git (Com SSH)
	ou
$ git clone https://github.com/alisson9386/worker.git (Com HTTPS)

$ cd worker

$ mvn spring-boot:run

````
