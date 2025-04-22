# 🎬 Golden Raspberry Awards API

API RESTful para leitura da lista de indicados e vencedores da categoria **Pior Filme** do Golden Raspberry Awards.

---

## ⚙️ Tecnologias Utilizadas

- ☕ Java 17  
- 🔧 Spring Boot
- 🚀 Servidor Tomcat embutido
- 🧬 Spring Data JPA
- 🗃️ H2 Database (banco de dados embarcado e in-memory)
- 📦 Maven
- 🧪 JUnit 5
- 📘 Swagger UI
- 📄 OpenCSV

## 🧠 Conceitos Aplicados

- 📶 Nível 2 de maturidade de Richardson
- 🧪 Test-Driven Development
- 🏗️ Arquitetura Em Camadas

---

## 🚀 Como Executar

- Clonar o repositório com o comando e acessar a pasta do projeto:
  ```bash
    git clone https://github.com/gabrielddnascimento/worst-movie-api.git
    cd worst-movie-api
  ```
- Escolher como irá rodar:

### Via Docker - **recomendado**
- Para rodar o sistema via docker, a porta 8080 deve estar liberada. Após isso, será necessário rodar o seguinte comando:
  ```bash
    docker compose up
  ```
- Caso queira rodar o container detached, também é possível adicionar "-d" ao final do comando

### Via Maven (mvn)
- Para rodar o sistema via maven, a porta 8080 deve estar liberada. Após isso, será necessário rodar o seguinte comando:
```bash
  mvn clean package -DskipTests
  mvn spring-boot:run
```
### Via IDE
- Importar o projeto na IDE;
- Pressionar o botão direito na pasta fonte do projeto;
- Passar o cursor sobre Run As;
- Selecionar Java Application;

**Pode variar de IDE para IDE**

> Ao iniciar, a aplicação irá:
> - 🌐 Deixar a API acessível em `http://localhost:8080`;
> - 📄 Ler o arquivo `movielist.csv` da pasta `resources`;
> - 🗄️ Popular o banco H2 em memória com os dados do CSV;
> - 📘 Disponibilizar uma interface do swagger ao acessar a URL do servidor em algum navegador;


## 📝 Observações

- 📁 O arquivo `movielist.csv` deve estar localizado em `src/main/resources`;
- 🚀 Os dados são carregados automaticamente ao subir a aplicação;
- ✅ Nenhuma configuração adicional é necessária para executar a aplicação;
- 🧪 Os testes devem ser ignorados ao realizar o mvn clean install pois por serem testes de integração, eles não funcionam sem o servidor estar de pé;

## 🧪 Executando Testes de Integração

Este projeto possui apenas **testes de integração**, ou seja, só irão retornar com sucesso caso o servidor esteja rodando.

Ainda dentro da pasta do projeto será necessário rodar o seguinte comando para realizar os testes:
```bash
mvn test
```

Também é possível rodar via IDE:
 - Pressionar o botão direito na pasta fonte do projeto
 - Passar o cursor sobre Run As
 - Pressionar Junit Test

**Pode variar de IDE para IDE**

## 💻 Testes Manuais
Para executar testes manuais, será possível acessar o swagger na URL `http:localhost:8080`.
Nessa URL já estará disponível todas as requisições assim como os dados que a API recebe no body/pathing

---

## 📂 Endpoint Principal

### 🔎 Análise de Intervalos

**GET** `/producers/intervals-analysis`  
Retorna os produtores com o **menor** e **maior** intervalo entre vitórias consecutivas.

#### 🧾 Exemplo de resposta:

```json
{
  "min": [
    {
      "producer": "Producer 1",
      "interval": 1,
      "previousWin": 2008,
      "followingWin": 2009
    }
  ],
  "max": [
    {
      "producer": "Producer 2",
      "interval": 99,
      "previousWin": 1900,
      "followingWin": 1999
    }
  ]
}
```

Para uma lista completa com os endpoints, será necessário acessar a URL do servidor, que por padrão é: `localhost:8080`
