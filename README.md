# Loja Eletrônicos

Bem-vindo ao sistema de gerenciamento de loja de eletrônicos, o sistema foi criado com o intuito
de facilitar e automatizar a rotina de trabalho de pessoas que atuam nessa área.
O sistema permite a gestão de pedidos, produtos e serviços de conserto, compra e venda, além de gerar relatórios em PDF sobre essas atividades.

## ⚙️ Como Instalar

### 📋 Pré-requisitos

- JDK 22 [Download](https://www.oracle.com/br/java/technologies/downloads/)
- JavaFX 22 Versão SDK [Download](https://gluonhq.com/products/javafx/)
- Maven 3.6+ (Maven usado integrado ao Intellj ou instale) [Download](https://maven.apache.org/download.cgi)
- Banco de dados configurado (MySQL) [Download](https://dev.mysql.com/downloads/installer/)
- Git [Download](https://git-scm.com/downloads)
## 🖥️ Instalação
### 1 - Clone o repositório na pasta de sua preferência:
```bash
git clone https://github.com/laetgabriel/loja-eletronicos.git
cd loja-eletronicos
```
### 2 - Com o MySQL Workbench instalado, crie o banco de dados (você pode use qualquer nome): 

```sql
CREATE DATABASE lojaeletronicos;
```

### 3 - Crie as tabelas uma por uma, não faça de uma vez

````sql
CREATE TABLE admin
(
    Id_Admin INT PRIMARY KEY AUTO_INCREMENT,
    Nome     VARCHAR(100) NOT NULL,
    Email    VARCHAR(100) NOT NULL UNIQUE,
    Senha    VARCHAR(255) NOT NULL
);
````

````sql
CREATE TABLE Cliente
(
    Id_Cliente SMALLINT AUTO_INCREMENT PRIMARY KEY,
    Nome       VARCHAR(100) NOT NULL,
    Email      VARCHAR(50),
    Telefone   VARCHAR(10)
);
````

````sql
CREATE TABLE Pedido
(
    Id_Pedido  SMALLINT AUTO_INCREMENT PRIMARY KEY,
    Id_Cliente SMALLINT,
    Estado     VARCHAR(20) NOT NULL,
    Data       DATE        NOT NULL,
    CONSTRAINT fk_Pedido_Cliente FOREIGN KEY (Id_Cliente) REFERENCES Cliente (Id_Cliente)
);

````

````sql
CREATE TABLE Servico
(
    Id_Servico SMALLINT AUTO_INCREMENT PRIMARY KEY,
    Id_Pedido  SMALLINT       NOT NULL,
    Descricao  VARCHAR(255),
    Preco      DECIMAL(10, 2) NOT NULL,
    Tipo       VARCHAR(20)    NOT NULL,
    CONSTRAINT fk_Pedido_Servico FOREIGN KEY (Id_Pedido) REFERENCES Pedido (Id_Pedido)
);
````

````sql
CREATE TABLE Produto
(
    Id_Produto    SMALLINT AUTO_INCREMENT PRIMARY KEY,
    Nome          VARCHAR(100)   NOT NULL,
    Categoria     VARCHAR(50)    NOT NULL,
    Preco         DECIMAL(10, 2) NOT NULL,
    Quant_Estoque SMALLINT       NOT NULL
);
````

````sql
CREATE TABLE Pedido_Possui_Produto
(
    Id_Pedido  SMALLINT       NOT NULL,
    Id_Produto SMALLINT       NOT NULL,
    Preco      DECIMAL(10, 2) NOT NULL,
    Quant      SMALLINT       NOT NULL,
    PRIMARY KEY (Id_Pedido, Id_Produto),
    CONSTRAINT fk_Pedido_Produto FOREIGN KEY (Id_Pedido) REFERENCES Pedido (Id_Pedido) ON DELETE CASCADE,
    CONSTRAINT fk_Produto_Pedido FOREIGN KEY (Id_Produto) REFERENCES Produto (Id_Produto) ON DELETE CASCADE
);
````

### 4 - Instale as depedências:
````bash
mvn clean install
````

### 5 - Compile para gerar o jar:
````bash
mvn clean package
````
### 6 - Volte uma pasta anterior e perceberá que surgiu a pasta target, ao entrar, você vai deparar com o arquivo JAR.
###### Copie ele para outro local em outra pasta, nessa nova pasta, crie um novo arquivo chamado db.properties, o conteúdo dele deve ser esse:
````bash
user=seu_usuario
password=sua_senha
dburl=jdbc:mysql://localhost:3306/nome_do_seu_bd_criado
````

### 7 - Extraia o arquivo rar do JavaFx para algum local, acessa-a, depois abra a pasta lib e copie o caminho.

### 8 - Para facilitar seu trabalho para lidar bem menos com o terminal, vá em sua nova pasta com o arquivo jar e o db.properties
####  - Crie um novo arquivo, mas dessa vez .bat, e coloque isto:
````
java --module-path seu\caminho\para\javafx\lib --add-modules javafx.controls,javafx.fxml -jar loja-eletronicos-1.0-SNAPSHOT.jar
````
### Se estiver enfrentando algum problema com o Java, cheque suas variáveis de ambite.
#### Pesquise por variaveis de ambiente no menu pesquisar do seu SO > Variaveis de Ambiente
#### Procure por JAVA_HOME em Variáveis do sistema, se não encontrar, crie clicando em Novo, insirindo o nome como
#### JAVA_HOME e o valor da variável é o caminho para a seu Java, estamos usando o JDK e normalmente fica em C:\Program Files\Java\jdk-22
#### Recomendo que verifique antes de inserir.
#### Após isso, vá na variável Path > Editar > Novo e insira %JAVA_HOME%\bin > OK > OK > OK

### Se preferir, você pode criar uma variável do sistema denominada como %PATH_TO_FX%, inserir o seu\caminho\para\javafx\lib
#### e fazer a troca em seu arquivo bat, agora ficaria:
````sql
java --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml -jar loja-eletronicos-1.0-SNAPSHOT.jar
````

## Caso não funcione, verifique sua versão Java

````sql
java -version
````

## Caso for uma versão antiga, reinicie seu computador.

## ⚙️ Funcionalidades

- **Gestão de Pedidos**: Criação, atualização e acompanhamento de pedidos de compra, venda e conserto de eletrônicos.
- **Cadastro de Produtos**: Possibilidade de cadastrar, editar e excluir produtos do estoque.
- **Serviços**: Adição de serviços atrelados a pedidos (é possível adicionar mais serviços e produtos a um pedido), com geração de relatórios detalhados.
- **Relatórios**: Geração de relatórios em formato PDF com informações de pedidos, clientes, produtos e serviços e seus valores com os gastos do mês, semana, dia atual e todos.
- **Tabelas atualizadas**: Tabelas de pedidos, clientes, serviços totalmente atualizadas, com possibilidade de detalhar profundamente o pedido ou mudar o estado do próprio.

## 🔄 Padrões de Projeto Utilizados

O projeto utiliza diversos padrões de projeto, tais como:
- **MVC (Model-View-Controller)**: Para separar as responsabilidades entre a camada de dados, a lógica de controle e a interface de usuário.
- **DAO (Data Access Object)**: Padrão utilizado para abstração do acesso ao banco de dados.
- **DTO (Data Transfer Object)**: Padrão utilizado para transferência de dados entre camadas do sistema.
- **Singleton**: Garantia de uma única instância da classe de banco de dados.
- **Observer**: Para monitorar mudanças de estados dentro do sistema.
- **State**: Implementação de diferentes estados de um pedido (em andamento, finalizado, cancelado, etc).
- **Chain of Responsibility**: Para processamento de pedidos e validações complexas.
- **Factory Method**: Utilizada para a criação e instaciação da DAO.
- **Builder**: Construção de objetos complexos.

## 🛠️ Tecnologias Utilizadas

- **Java**
- **JavaFX**: Utilizado para a interface gráfica do usuário (GUI).
- **JDBC**: Para a integração com o banco de dados.
- **Maven**: Gerenciador de dependências e automação de build.
- **iText**: Para geração de relatórios em PDF.
- **JavaMail**: Para envio de notificações por email.
- **MySQL**: Como banco de dados.

