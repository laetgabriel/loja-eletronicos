# Loja Eletrônicos

Este repositório contém o código-fonte de um sistema de gerenciamento de loja de eletrônicos. 
O sistema permite a gestão de pedidos, produtos e serviços de conserto, compra e venda, além de gerar relatórios em PDF sobre essas atividades.

## Funcionalidades

- **Gestão de Pedidos**: Criação, atualização e acompanhamento de pedidos de compra, venda e conserto de eletrônicos.
- **Cadastro de Produtos**: Possibilidade de cadastrar, editar e excluir produtos do estoque.
- **Serviços**: Adição de serviços atrelados a pedidos (é possível adicionar mais serviços e produtos a um pedido), com geração de relatórios detalhados.
- **Relatórios**: Geração de relatórios em formato PDF com informações de pedidos, clientes, produtos e serviços e seus valores com os gastos do mês, semana, dia atual e todos.
- **Tabelas atualizadas**: Tabelas de pedidos, clientes, serviços totalmente atualizadas, com possibilidade de detalhar profundamente o pedido ou mudar o estado do próprio.

## Padrões de Projeto Utilizados

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

## Tecnologias Utilizadas

- **Java**
- **JavaFX**: Utilizado para a interface gráfica do usuário (GUI).
- **JDBC**: Para a integração com o banco de dados.
- **Maven**: Gerenciador de dependências e automação de build.
- **iText**: Para geração de relatórios em PDF.
- **JavaMail**: Para envio de notificações por email.
- **MySQL**: Como banco de dados.


### Pré-requisitos

- JDK 11 ou superior
- Maven 3.6+
- Banco de dados configurado (MySQL)
