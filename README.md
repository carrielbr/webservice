# webservice

- A Aplicação desenvolvida por em Grails na versão 2.4.4
- O banco utilizado foi o mysql, onde o ter um database chamado webservice.
- O usuario para conexão com o banco é root e senha 011001.
- O Sistema ao ser executado deve criar suas tabelas caso as mesmas não exita e popular conforme o necessario.

- A requisição do WebService pode ser feito pelo navegador ou por exemplo pelo programa SoapUI. É possivel fazer requisição para um resultado tanto em JSON quanto em XML

Segue exemplo do link os parametros:

JSON: http://localhost:8080/WebService/logistica/searchLogist.json?origem=A&destino=D&mapa=SP&valor=2,59&autonomia=10

XML: http://localhost:8080/WebService/logistica/searchLogist.xml?origem=A&destino=E&mapa=SP&valor=2,50&autonomia=10

- Juntamento com o projeto você Encontra tambem o arquivo WAR para fazer o deploy em seu servidor.

- Resultados:

JSON: {"rota": "ABE", "custo": 15,0}
