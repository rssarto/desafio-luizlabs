# desafio-luizlabs

# Pré requisitos
<ul>
<li>Java 8</li>
<li>Maven</li>
</ul>

# Build da aplicacao
<p>Após clonar o repositório acesse o diretório da aplicação e execute o comando abaixo:</p>

-> mvn clean package

# Executando a aplicação
<p>Após realizar o build da aplicação execute o comando abaixo para iniciar a aplicação:</p>

-> java -jar ./target/desafio-luizalabs-0.0.1-SNAPSHOT.jar

# Arquitetura e Design
<p>A aplicação foi construída usando Spring Boot por motivo de afinidade e conhecimento do framework. Tiramos proveito da injeção de dependência para abstrairmos toda a lógica de criação dos objetos.</p>

<p>A Leitura do Arquivo de log é realizada através da classe GameFileParser que por sua vez
chama a camada de persistência localizada na classe GameServiceImpl.</p>

<p>A camada de persistência foi abstraída com o uso de Interface, sendo assim, podemos substituí-la facilmente caso seja necessário.</p>

<p>A API REST foi exposta através da classe GamesController que por sua vez oferece as funcionalidades de consultar a lista completa de games ou mesmo consultar um game por Id.<p>

<p>Foi aplicado o conceito HATEOAS (Hypermedia as the engine of application state) para facilitar
o acesso aos recursos oferecidos na API.</p>

# Documentação da API com Swagger
<p>Uma vez que a aplicação estiver rodando é possível acessar a documentação da API REST através do link abaixo:</p>

<a href="http://localhost:8080/swagger-ui.html">http://localhost:8080/swagger-ui.html</a>
