# 🚀 PDV POSTO DE COMBUSTÍVEL - GUIA COMPLETO DE INSTALAÇÃO

## 📋 SOBRE O PROJETO

Sistema completo de PDV (Ponto de Venda) para Posto de Combustível desenvolvido em **Java Swing** com backend **Spring Boot** e banco de dados **PostgreSQL**.

### ✨ Funcionalidades

- ✅ **Sistema de Login** com autenticação Admin e Frentista
- ✅ **CRUD Completo** para todas as entidades
- ✅ **API REST** com Swagger/OpenAPI documentado
- ✅ **Interface Swing** moderna e responsiva
- ✅ **Banco de Dados PostgreSQL** com relacionamentos
- ✅ **Gerenciamento de:**
  - Pessoas (frentistas/fornecedores)
  - Produtos e Preços
  - Bombas de Combustível
  - Estoque
  - Vendas e Itens de Venda
  - Contatos

---

## 🛠️ PRÉ-REQUISITOS

Certifique-se de ter instalado:

1. **Java JDK 17 ou superior**
   - Download: https://www.oracle.com/java/technologies/downloads/
   - Verificar: `java -version`

2. **Maven 3.6+ ou superior**
   - Download: https://maven.apache.org/download.cgi
   - Verificar: `mvn -version`

3. **PostgreSQL 12+ ou superior**
   - Download: https://www.postgresql.org/download/
   - Incluir pgAdmin 4

4. **Git** (opcional, para clonar)
   - Download: https://git-scm.com/downloads

---

## 📥 PASSO 1: BAIXAR O PROJETO

### Opção A: Download ZIP
1. Acesse o repositório no GitHub
2. Clique em **Code** → **Download ZIP**
3. Extraia o ZIP em uma pasta de sua preferência

### Opção B: Git Clone
```bash
git clone https://github.com/SEU_USUARIO/PDV-BackEnd-JavaPOO.git
git clone https://github.com/SEU_USUARIO/PDV-FrontEnd-JavaPOO.git
```

---

## 🗄️ PASSO 2: CONFIGURAR O BANCO DE DADOS

### 2.1. Criar o Banco no PostgreSQL

1. Abra o **pgAdmin 4**
2. Conecte ao servidor PostgreSQL
3. Clique com botão direito em **Databases** → **Create** → **Database**
4. Configure:
   - **Database name:** `pdvpostocombustivel`
   - **Owner:** `postgres`
   - Clique em **Save**

### 2.2. Estrutura das Tabelas

O Hibernate criará as tabelas automaticamente na primeira execução (`ddl-auto=update`).

As tabelas criadas serão:
- `pessoas` - Dados pessoais (frentistas, fornecedores)
- `acessos` - Credenciais de login
- `produtos` - Produtos do posto
- `precos` - Preços dos produtos
- `bombas` - Bombas de combustível
- `estoques` - Controle de estoque
- `vendas` - Vendas realizadas
- `itens_venda` - Itens de cada venda
- `contatos` - Contatos de pessoas

---

## ⚙️ PASSO 3: CONFIGURAR O BACKEND

### 3.1. Configurar application.properties

1. Navegue até a pasta do backend:
   ```
   pdv-posto-combustivel/src/main/resources/
   ```

2. Edite o arquivo `application.properties`:
   ```properties
   # Linha 9 - Configure sua senha do PostgreSQL
   spring.datasource.password=SUA_SENHA_AQUI
   ```

3. Substitua `SUA_SENHA_AQUI` pela senha que você definiu no PostgreSQL.

### 3.2. Compilar o Backend

Abra o terminal na pasta do backend e execute:

```bash
cd pdv-posto-combustivel
mvn clean install
```

**Aguarde até ver:**
```
[INFO] BUILD SUCCESS
```

### 3.3. Executar o Backend

```bash
mvn spring-boot:run
```

**Aguarde até ver:**
```
Started PdvpostocombustivelApplication in X.XXX seconds
```

O backend estará rodando em: **http://localhost:8080**

### 3.4. Verificar o Swagger (Opcional)

Abra o navegador e acesse:
```
http://localhost:8080/swagger-ui.html
```

Você verá toda a documentação da API REST.

---

## 🖥️ PASSO 4: EXECUTAR O FRONTEND

### 4.1. Compilar o Frontend

Abra **OUTRO terminal** (deixe o backend rodando) e navegue até a pasta do frontend:

```bash
cd JavaPoo-Front-End-main
mvn clean install
```

### 4.2. Executar o Frontend

```bash
mvn exec:java
```

A tela de login do sistema abrirá automaticamente.

---

## 👤 PASSO 5: PRIMEIRO ACESSO

### 5.1. Cadastrar o Administrador

Na primeira execução, o banco estará vazio. Você verá a tela de login com o botão:

**"Cadastrar Admin"**

1. Clique em **"Cadastrar Admin"**
2. Preencha:
   - **Nome Completo:** (ex: Administrador do Sistema)
   - **Usuário de Acesso:** (ex: admin)
   - **Senha:** (ex: admin123)
   - **Confirmar Senha:** (ex: admin123)
3. Clique em **"Cadastrar Administrador"**

✅ Pronto! O admin foi criado.

### 5.2. Fazer Login como Admin

1. Na tela de login, selecione: **Administrador**
2. Digite:
   - **Usuário:** admin (ou o que você cadastrou)
   - **Senha:** admin123 (ou a que você cadastrou)
3. Clique em **"Entrar"**

🎉 Você entrará no painel administrativo!

---

## 🎯 FUNCIONALIDADES DO SISTEMA

### **Como Administrador:**

Você terá acesso a todos os gerenciadores:

1. **Cadastrar Novo Frentista**
   - Crie credenciais para frentistas
   - Vincule a pessoas cadastradas

2. **Gerenciar Pessoas**
   - Cadastrar frentistas, fornecedores
   - CPF/CNPJ, data de nascimento

3. **Gerenciar Preços**
   - Definir preços dos combustíveis
   - Histórico de preços

4. **Gerenciar Produtos**
   - Cadastrar produtos do posto
   - Tipo, descrição

5. **Gerenciar Bombas**
   - Vincular combustíveis às bombas
   - Controle de disponibilidade

6. **Gerenciar Estoque**
   - Controle de entrada/saída
   - Quantidade disponível

7. **Gerenciar Vendas**
   - Registrar vendas
   - Itens vendidos

8. **Gerenciar Contatos**
   - Contatos de pessoas
   - Telefone, email

### **Como Frentista:**

Após criar um frentista:

1. Frentista cadastra seus dados em **"Preencher Informações"**
2. Admin cria credenciais em **"Cadastrar Novo Frentista"**
3. Frentista faz login
4. Acesso apenas ao **Gerenciar Bombas**

---

## 🧪 TESTANDO O SISTEMA

### Teste 1: Cadastrar um Produto
```
Login Admin → Gerenciar Produtos
→ Adicionar
Nome: Gasolina Comum
Tipo: COMBUSTIVEL
→ Cadastrar
```

### Teste 2: Definir um Preço
```
Gerenciar Preços
→ Adicionar
Produto: Gasolina Comum
Preço: 5.99
→ Cadastrar
```

### Teste 3: Cadastrar uma Bomba
```
Gerenciar Bombas
→ Adicionar
Número: 1
Produto: Gasolina Comum
Status: Disponível
→ Cadastrar
```

### Teste 4: Cadastrar um Frentista

**Passo 1 - Frentista preenche dados:**
```
Logout → Tela Login
→ "Preencher Informações"
Nome: João Silva
CPF: 12345678900
Data: 01/01/1990
Tipo: FISICA
→ Cadastrar
```

**Passo 2 - Admin cria credenciais:**
```
Login Admin
→ "Cadastrar Novo Frentista"
Selecionar Pessoa: João Silva - CPF: 12345678900
Usuário: joao.silva
Senha: senha123
Confirmar: senha123
→ Criar Acesso
```

**Passo 3 - Frentista faz login:**
```
Logout
Tipo: Frentista
Usuário: joao.silva
Senha: senha123
→ Entrar
```

✅ Frentista verá apenas "Gerenciar Bombas"

---

## 📊 ESTRUTURA DO BANCO DE DADOS

### Tabelas Principais:

```
pessoas
├─ id (PK)
├─ nome_completo
├─ cpf_cnpj (UNIQUE)
├─ data_nascimento
├─ tipo_pessoa (FISICA/JURIDICA)
└─ ativo

acessos
├─ id (PK)
├─ usuario (UNIQUE)
├─ senha
├─ nome_completo
├─ role (ADMIN/FRENTISTA)
└─ pessoa_id (FK → pessoas)

produtos
├─ id (PK)
├─ nome
├─ tipo_produto
└─ descricao

precos
├─ id (PK)
├─ produto_id (FK → produtos)
├─ valor
└─ data_vigencia

bombas
├─ id (PK)
├─ numero_bomba (UNIQUE)
├─ produto_id (FK → produtos)
└─ disponivel

E mais: estoques, vendas, itens_venda, contatos
```

---

## 🛠️ COMANDOS ÚTEIS

### Backend:

```bash
# Compilar
mvn clean install

# Executar
mvn spring-boot:run

# Verificar logs
# Os logs aparecerão no terminal
```

### Frontend:

```bash
# Compilar
mvn clean install

# Executar
mvn exec:java

# Recompilar se mudar código
mvn clean install
```

### Banco de Dados:

```sql
-- Ver todas as pessoas
SELECT * FROM pessoas;

-- Ver todos os acessos
SELECT * FROM acessos;

-- Ver vinculação pessoa-acesso
SELECT 
    a.id, a.usuario, a.role, a.nome_completo,
    p.nome_completo as pessoa_nome, p.cpf_cnpj
FROM acessos a
LEFT JOIN pessoas p ON a.pessoa_id = p.id;

-- Limpar tudo (se quiser recomeçar)
DROP DATABASE pdvpostocombustivel;
CREATE DATABASE pdvpostocombustivel;
```

---

## ❌ PROBLEMAS COMUNS

### Erro: "Connection refused"
**Causa:** Backend não está rodando
**Solução:** Execute `mvn spring-boot:run` no backend

### Erro: "FATAL: password authentication failed"
**Causa:** Senha incorreta no application.properties
**Solução:** Corrija a senha em `src/main/resources/application.properties`

### Erro: "database does not exist"
**Causa:** Banco não foi criado
**Solução:** Crie o banco `pdvpostocombustivel` no pgAdmin

### Frontend não abre
**Causa:** Backend não está rodando ou porta 8080 ocupada
**Solução:** Verifique se o backend está rodando em http://localhost:8080

### Botão "Cadastrar Admin" não aparece
**Causa:** Já existe um admin no banco
**Solução:** Para recriar, delete o banco e crie novamente

---

## 📦 TECNOLOGIAS UTILIZADAS

### Backend:
- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Data JPA**
- **Spring Web**
- **PostgreSQL** (JDBC Driver)
- **Hibernate** (ORM)
- **Swagger/OpenAPI 3** (Documentação API)
- **HikariCP** (Pool de Conexões)
- **Maven** (Gerenciamento de Dependências)

### Frontend:
- **Java Swing** (Interface Gráfica)
- **Jackson** (JSON Processing)
- **Apache HttpClient** (Requisições HTTP)
- **Maven** (Gerenciamento de Dependências)

### Banco de Dados:
- **PostgreSQL 12+**

---

## 📝 ESTRUTURA DO PROJETO

```
PDV-BackEnd-JavaPOO/
├── src/main/java/com/br/pdvpostocombustivel/
│   ├── api/           # Controllers REST
│   │   ├── acesso/
│   │   ├── bomba/
│   │   ├── contato/
│   │   ├── estoque/
│   │   ├── pessoa/
│   │   ├── preco/
│   │   ├── produto/
│   │   └── venda/
│   ├── domain/        # Entities
│   │   └── entity/
│   └── config/        # Configurações (Swagger, CORS)
├── src/main/resources/
│   └── application.properties
└── pom.xml

PDV-FrontEnd-JavaPOO/
├── src/main/java/br/com/PdvFrontEnd/
│   ├── view/          # Telas Swing
│   ├── service/       # Serviços HTTP
│   ├── model/         # Modelos
│   ├── dto/           # Data Transfer Objects
│   └── util/          # Utilitários (HttpClient)
└── pom.xml
```

---

## 🎓 PARA O PROFESSOR

Este projeto demonstra:

1. ✅ **Programação Orientada a Objetos (POO)**
   - Encapsulamento, Herança, Polimorfismo
   - Classes, Objetos, Métodos

2. ✅ **Arquitetura em Camadas**
   - Controller → Service → Repository → Entity
   - Separação de responsabilidades

3. ✅ **API REST**
   - CRUD completo para todas as entidades
   - Documentação Swagger

4. ✅ **Persistência de Dados**
   - JPA/Hibernate
   - Relacionamentos (OneToMany, ManyToOne)
   - Constraints e validações

5. ✅ **Interface Gráfica**
   - Java Swing moderno
   - Padrão MVC
   - Comunicação HTTP com backend

6. ✅ **Segurança e Validação**
   - Autenticação de usuários
   - Roles (ADMIN/FRENTISTA)
   - Validações de dados

7. ✅ **Boas Práticas**
   - Código organizado e comentado
   - Tratamento de exceções
   - DTOs para transferência de dados
   - Repository Pattern

---

## 📞 SUPORTE

Se tiver problemas:

1. Verifique se todos os pré-requisitos estão instalados
2. Confira se o banco foi criado corretamente
3. Verifique se a senha está correta no application.properties
4. Certifique-se de que o backend está rodando antes do frontend
5. Consulte a seção "Problemas Comuns" acima

---

## 📄 LICENÇA

Este projeto é de uso educacional.

---

## ✅ CHECKLIST DE INSTALAÇÃO

- [ ] Java JDK 17+ instalado
- [ ] Maven instalado
- [ ] PostgreSQL instalado
- [ ] Banco `pdvpostocombustivel` criado
- [ ] Senha configurada em `application.properties`
- [ ] Backend compilado com `mvn clean install`
- [ ] Backend rodando com `mvn spring-boot:run`
- [ ] Frontend compilado com `mvn clean install`
- [ ] Frontend executado com `mvn exec:java`
- [ ] Admin cadastrado
- [ ] Login realizado com sucesso

---

**🎉 PROJETO PRONTO PARA USO! 🚀**

**Desenvolvido com ❤️ usando Java, Spring Boot e PostgreSQL**

