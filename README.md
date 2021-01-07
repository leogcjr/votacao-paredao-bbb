#Paredão BBB

Considerações Gerais

O desafio

Você deve implementar o sistema de votação do Paredão do BBB. No Paredão, dois ou mais participantes se enfrentam em uma votação popular para permanecer na
casa. Ao final da votação, o participante que recebeu mais votos é eliminado do programa.


Esse sistema pode ser dividido em três partes:

- API para registro e consulta dos votos.
- Client que permite aos usuários participarem da votação, escolhendo um participante para ser eliminado.
- Client que permite à produção do programa consultar algumas informações sobre o estado da votação.

As regras de negócio para o desenvolvimento da solução são:

- Cada usuário pode votar quantas vezes quiser com uma taxa máxima de 10 votospor minuto.
- A votação é chamada na TV em horário nobre, com isso, é esperado um enorme volume de votos concentrados em um curto espaço de tempo. A sua solução deve
suportar pelo menos 1000 votos por segundo.

- A produção do programa gostaria de poder consultar: o total geral de votos, o total por participante e o total de votos por hora de cada Paredão.
- Devido ao grande volume de acessos, podemos enfrentar períodos de lentidão ou instabilidade nos serviços. Todo o sistema deve estar preparado para isso.

Como a produção acessará constantemente o estado da votação durante o programa ao vivo, as consultas não podem ser lentas. No entanto, é aceitável que a
resposta apresente dados defasados em momentos de instabilidade.


## Stack utilizada

- Java 8
- Spring
- Maven
- MongoDB
- ModelMapper
- Swagger
- JUnit
- Mockito
- Kafka
- Docker

## Requisitos
- Maven 3.6+
- Java 8
- Docker instalado na máquina

## Rodar a aplicação

O Docker deve estar rodando na maquina.

Clone o projeto e navegue até a pasta root do mesmo por um terminal.

Então, execute os comandos na sequencia abaixo para compilar, rodar os testes unitarios da aplicação e gerar as imagens docker:

- mvn clean
- mvn package
- docker-compose up

Caso deseje rodar apenas os testes:
- mvn test

## Acessar a API

Após subir os containers do docker, a api ficará disponivel em:
- http://localhost:8080/api/v1/

Para visualizar a documentação da api:
- http://localhost:8080/swagger-ui.html

OBS: dependendo de como esta configurado o docker, o localhost poderá ser outro IP, então para acessar a aplicação deverá ir pelo ip do docker.



# Mensageria e filas
- Foi utilizado o Kafka para mensageria:
- O producer ficou responsável por enviar os resultados das votações;
- O consumer ficou responsável por escutar os resultados das votações e apenas logar em console os resultados.

