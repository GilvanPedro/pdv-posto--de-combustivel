# 🚀 Guia de Configuração e Teste do Projeto PDV Posto de Combustível

Este guia detalha os passos necessários para configurar, executar e testar o sistema PDV Posto de Combustível em seu ambiente local.

## 📋 Sumário Executivo

| Etapa | Ação Principal | Status |
| :--- | :--- | :--- |
| **1. Pré-requisitos** | Instalar Java 17+, Maven, PostgreSQL e uma IDE (IntelliJ recomendado). | ✅ |
| **2. Banco de Dados** | Criar o banco de dados `pdvpostocombustivel` no PostgreSQL. | 🗄️ |
| **3. Configuração** | Configurar a senha do PostgreSQL no arquivo `application.properties`. | ⚙️ |
| **4. Execução** | Iniciar o Backend (Spring Boot) e, em seguida, o Frontend (Java Swing). | 🚀 |
| **5. Teste** | Verificar a criação das tabelas, acessar o Swagger UI e testar o CRUD no Frontend. | 🔍 |

---

## 1. Preparação do Ambiente

### 1.1. Pré-requisitos de Software

Certifique-se de que os seguintes softwares estão instalados e configurados corretamente em sua máquina:

| Software | Versão Mínima | Comando de Verificação | Link para Download |
| :--- | :--- | :--- | :--- |
| **Java JDK** | 17+ | `java -version` | [Oracle Java Downloads](https://www.oracle.com/java/technologies/downloads/) |
| **Apache Maven** | 3.8+ | `mvn -version` | [Apache Maven Download](https://maven.apache.org/download.cgi) |
| **PostgreSQL** | 15+ | `psql --version` | [PostgreSQL Downloads](https://www.postgresql.org/download/) |
| **IDE** | N/A | N/A | [IntelliJ IDEA Download](https://www.jetbrains.com/idea/download/) (Recomendado) |

### 1.2. Obtenção do Código-Fonte

1.  No repositório GitHub, clique em **"Code"** e depois em **"Download ZIP"** ou clone o repositório.
2.  Extraia o conteúdo para um diretório de sua preferência (ex: `C:\projetos\pdv-posto-combustivel`).

---

## 2. Configuração do Banco de Dados

**⚠️ IMPORTANTE:** Este passo deve ser concluído **antes** de executar o Backend.

### 2.1. Criação do Banco de Dados

Crie um novo banco de dados no PostgreSQL com o nome exato: `pdvpostocombustivel`.

**Opção A: Via pgAdmin (Interface Gráfica)**

1.  Abra o **pgAdmin 4** e conecte-se ao servidor.
2.  Clique com o botão direito em **"Databases"** e selecione **"Create" → "Database..."**.
3.  Defina o nome como `pdvpostocombustivel` e clique em **"Save"**.

**Opção B: Via SQL (Terminal/Query Tool)**

```sql
CREATE DATABASE pdvpostocombustivel;
```

### 2.2. Configuração das Credenciais

O projeto utiliza o arquivo `application.properties` para as configurações do banco de dados.

1.  Navegue até o diretório de recursos do Backend:
    ```
    pdv-posto-combustivel/pdv-posto-combustivel/src/main/resources/
    ```
2.  **Copie** o arquivo `application.properties.example` e **renomeie** a cópia para `application.properties`.
3.  Edite o novo arquivo `application.properties` e insira a senha do seu usuário PostgreSQL:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/pdvpostocombustivel
    spring.datasource.username=postgres
    spring.datasource.password=SUA_SENHA_AQUI  # <-- Substitua por sua senha
    ```

---

## 3. Execução do Sistema

### 3.1. Iniciar o Backend (API REST)

O Backend será executado na porta `8082`.

**Opção A: Via Maven (Terminal)**

```bash
cd pdv-posto-combustivel
mvn spring-boot:run
```

**Opção B: Via IDE (IntelliJ IDEA)**

1.  Abra o projeto `pdv-posto-combustivel` na IDE.
2.  Localize e execute a classe principal: `com.br.pdvpostocombustivel.PdvpostocombustivelApplication.java`.

**Verificação:** O log de console deve indicar o início bem-sucedido, como:
```
✅ Started PdvpostocombustivelApplication in X.X seconds
```
A API estará acessível em: `http://localhost:8082`

### 3.2. Iniciar o Frontend (Java Swing)

O Frontend deve ser iniciado **após** a confirmação de que o Backend está ativo.

**Via IDE (IntelliJ IDEA)**

1.  Abra o projeto `JavaPoo-Front-End-main` na IDE.
2.  Localize e execute a classe principal: `br.com.PdvFrontEnd.view.MainApp.java`.

**Comportamento de Acesso:**
*   **Primeiro Acesso:** O sistema abrirá a tela de **CADASTRO** para o usuário inicial.
*   **Acessos Subsequentes:** O sistema abrirá a tela de **LOGIN**.

---

## 4. Verificação e Testes

Após a execução, realize os seguintes testes para garantir o funcionamento completo do sistema.

### 4.1. Teste de Persistência (Banco de Dados)

Verifique se o Hibernate criou as tabelas automaticamente no banco `pdvpostocombustivel`.

**Tabelas Esperadas:**
*   `acesso`
*   `contato`
*   `custo`
*   `estoque`
*   `pessoa`
*   `preco`
*   `produto`

**Verificação via SQL:**
```sql
-- Conectar ao banco
\c pdvpostocombustivel

-- Listar todas as tabelas
\dt
```

### 4.2. Teste da API (Swagger UI)

Acesse a documentação interativa da API para verificar os *endpoints*.

**URL do Swagger UI:** `http://localhost:8082/swagger-ui.html`

**Teste Exemplo:**
1.  Expanda o **"pessoa-controller"**.
2.  Clique em **"GET /api/pessoas"**.
3.  Clique em **"Try it out"** e depois em **"Execute"**.
4.  A resposta esperada é `[]` (um array vazio), indicando que a comunicação com o banco está funcionando.

### 4.3. Teste Funcional (Frontend)

1.  Na janela do aplicativo (Frontend), realize o **LOGIN** (ou **CADASTRO** no primeiro acesso).
2.  Acesse o módulo **"Pessoas"**.
3.  Crie um novo registro de pessoa.
4.  **Verificação:** Volte ao pgAdmin e execute `SELECT * FROM pessoa;`. O registro criado deve aparecer.

---

## ❌ Solução de Problemas Comuns

| Erro | Causa Mais Comum | Solução Recomendada |
| :--- | :--- | :--- |
| **Connection refused to database** | PostgreSQL não está ativo ou senha incorreta. | 1. Verifique se o serviço PostgreSQL está rodando (`services.msc` no Windows). 2. Confirme a senha no `application.properties`. |
| **Port 8082 already in use** | Outro processo está usando a porta 8082. | Identifique e encerre o processo usando a porta (via `netstat -ano` e `taskkill /F /PID`). |
| **Failed to execute goal** | Dependências Maven desatualizadas ou corrompidas. | Execute `mvn clean install -U` no diretório do Backend para forçar a atualização das dependências. |

---

## 🎯 Checklist de Sucesso

Use este checklist para confirmar que todas as etapas foram concluídas com êxito:

- [ ] Banco `pdvpostocombustivel` criado no PostgreSQL.
- [ ] Arquivo `application.properties` configurado com a senha correta.
- [ ] Backend (Spring Boot) iniciado sem erros.
- [ ] Tabelas criadas automaticamente no banco de dados.
- [ ] Swagger UI acessível em `http://localhost:8082/swagger-ui.html`.
- [ ] Frontend (Java Swing) abre a janela gráfica.
- [ ] Cadastro/Login no Frontend realizado com sucesso.
- [ ] Operação de criação (CRUD) testada e validada no banco de dados.

**Se todos os itens estiverem marcados, o projeto está 100% funcional em seu ambiente local.**
