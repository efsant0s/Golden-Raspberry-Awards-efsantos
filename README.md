# GoldenRaspberryAwardsefsantos
API RESTful para possibilitar a leitura da lista de indicados e vencedores da categoria Pior Filme do Golden Raspberry Awards.

# Utilização:

Execute o GoldenRaspberryAwards0.0.1SNAPSHOT.jar, caso queira manter os registros originais do movielist.csv, não passe nenhum argumento.
Caso queira usar um arquivo de filmes diferente, coloque o novo arquivo com o mesmo nome (movielist.csv) na pasta onde o jar está presente, e passe o argumento "local" na execução.
>" java -jar GoldenRaspberryAwards-0.0.1-SNAPSHOT.jar local"

Abra um aplicativo como SOAPUI e ou Postman para enviar as seguintes requisições:

Para receber intervalos:
enviar requisição GET
http://localhost:8080/movie-award/get-intervals


Para trazer todos os registros:
enviar requisição GET
http://localhost:8080/movie-award/


Para inserir registros::
enviar requisição POST para
http://localhost:8080/movie-award/create
Exemplo Json para insert:

> {"year" : "2022",
"title" : "Space Jam  A New Legacy ",
"studios" : "Warner Bros. Pictures",
"producers" : "Maverick Carter, Ryan Coogler, Duncan Henderson, and LeBron James",
"winner" : "true" }"

Para excluir registros:
enviar requisição DELETE para:
http://localhost:8080/movie-award/delete/{id}
Substituindo o ID pelo código do filme.


Para buscar registros:
Requisição GET para:
http://localhost:8080/movie-award/{id}
Substituindo o ID pelo código do filme.


Para editar registros:
enviar requisição PUT para:
http://localhost:8080/movie-award/edit
Exemplo de Json para edição:
>{"id" : "2",
"year" : "2022",
"title" : "Space Jam  A New Legacy ",
"studios" : "Warner Bros. Pictures",
"producers" : "Maverick Carter, Ryan Coogler, Duncan Henderson, and LeBron James",
"winner" : "true" }


