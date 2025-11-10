# 🔧 GUIA DE CONFIGURAÇÃO - NOVO SISTEMA DE AUTENTICAÇÃO

## 📋 O que foi alterado?

### ✅ Mudanças Principais:
1. **Login agora usa o banco de dados** - Não mais SessionManager local
2. **Relacionamento Pessoa ↔ Acesso** - Acessos vinculados a pessoas
3. **Tabela acessos atualizada** - Novos campos: pessoa_id, nome_completo, role
4. **Endpoint de login** - POST /api/acessos/login

---

## 🚀 PASSO A PASSO PARA CONFIGURAR

### 1️⃣ **Atualizar o Banco de Dados**

Execute os scripts SQL na ordem:

```sql
-- 1. Atualizar estrutura da tabela acessos
\i MIGRACAO_ACESSOS_PESSOA.sql

-- 2. Inserir usuário admin padrão
\i INSERIR_ADMIN_PADRAO.sql
```

**Ou manualmente no pgAdmin 4:**

```sql
-- Adicionar colunas
ALTER TABLE acessos ADD COLUMN IF NOT EXISTS pessoa_id BIGINT;
ALTER TABLE acessos ADD COLUMN IF NOT EXISTS nome_completo VARCHAR(200);
ALTER TABLE acessos ADD COLUMN IF NOT EXISTS role VARCHAR(50) DEFAULT 'FRENTISTA';

-- Adicionar FK
ALTER TABLE acessos 
ADD CONSTRAINT fk_acessos_pessoa 
FOREIGN KEY (pessoa_id) REFERENCES pessoas(id) ON DELETE SET NULL;

-- Tornar usuario único
ALTER TABLE acessos 
ADD CONSTRAINT uk_acessos_usuario UNIQUE (usuario);

-- Inserir admin
INSERT INTO acessos (usuario, senha, role, nome_completo)
VALUES ('admin', 'admin123', 'ADMIN', 'Administrador do Sistema')
ON CONFLICT (usuario) DO NOTHING;
```

---

### 2️⃣ **Iniciar o Backend**

```bash
cd pdv-posto-combustivel
mvn clean install
mvn spring-boot:run
```

**Aguarde a mensagem:**
```
Tomcat started on port(s): 8080 (http)
```

---

### 3️⃣ **Iniciar o Frontend**

```bash
cd JavaPoo-Front-End-main
mvn clean install
mvn exec:java
```

---

## 🔐 FLUXO DE USO

### Para o Professor Testar:

#### **1. Primeiro Acesso (Login como Admin)**
```
Usuário: admin
Senha: admin123
Tipo: Administrador
```

#### **2. Cadastrar um Frentista**

**Opção A: Pelo Gerenciador de Pessoas**
1. Vá em "Gerenciar Pessoas"
2. Clique em "Adicionar Pessoa"
3. Preencha:
   - Nome: João da Silva
   - CPF/CNPJ: 12345678900
   - Data Nascimento: 15/03/1990
   - Tipo: FRENTISTA
4. Salvar

**Opção B: Tela Inicial (se for novo usuário)**
1. Na tela de login, clicar em "Não tenho cadastro"
2. Preencher dados pessoais
3. Sistema registra a pessoa

#### **3. Criar Credenciais de Acesso para o Frentista**
1. No painel admin, clicar em "Gerenciar Acessos"
2. Clicar em "Adicionar Acesso"
3. Preencher:
   - Usuário: joao.silva
   - Senha: senha123
   - Selecionar Pessoa: João da Silva (da lista)
   - Role: FRENTISTA
4. Salvar

#### **4. Testar Login do Frentista**
1. Fazer logout
2. Login:
   ```
   Usuário: joao.silva
   Senha: senha123
   Tipo: Frentista
   ```

---

## 📊 ESTRUTURA DO BANCO DE DADOS

### Tabela: `pessoas`
```sql
id              BIGSERIAL PRIMARY KEY
nome_completo   VARCHAR(200) NOT NULL
cpf_cnpj        VARCHAR(20) UNIQUE NOT NULL
data_nascimento DATE NOT NULL
tipo_pessoa     VARCHAR(15) NOT NULL
role            VARCHAR(50)
ativo           BOOLEAN DEFAULT TRUE
```

### Tabela: `acessos`
```sql
id              BIGSERIAL PRIMARY KEY
usuario         VARCHAR(100) UNIQUE NOT NULL
senha           VARCHAR(255) NOT NULL
pessoa_id       BIGINT → FK para pessoas(id)
nome_completo   VARCHAR(200)
role            VARCHAR(50) DEFAULT 'FRENTISTA'
```

---

## 🔍 VERIFICAÇÕES

### Verificar se o admin foi criado:
```sql
SELECT * FROM acessos WHERE usuario = 'admin';
```

**Resultado esperado:**
```
id | usuario | senha     | pessoa_id | nome_completo              | role
1  | admin   | admin123  | NULL      | Administrador do Sistema  | ADMIN
```

### Verificar estrutura da tabela:
```sql
\d acessos
```

### Listar todos os acessos:
```sql
SELECT id, usuario, nome_completo, role FROM acessos;
```

---

## ❌ RESOLUÇÃO DE PROBLEMAS

### Problema: "Usuário ou senha incorretos!"

**Solução 1: Verificar se existe no banco**
```sql
SELECT * FROM acessos WHERE usuario = 'admin';
```

**Solução 2: Recriar o admin**
```sql
DELETE FROM acessos WHERE usuario = 'admin';
INSERT INTO acessos (usuario, senha, role, nome_completo)
VALUES ('admin', 'admin123', 'ADMIN', 'Administrador do Sistema');
```

**Solução 3: Verificar se o backend está rodando**
- Acesse: http://localhost:8080/swagger-ui.html
- Teste o endpoint: POST /api/acessos/login
- Body:
```json
{
  "usuario": "admin",
  "senha": "admin123"
}
```

---

### Problema: "Connection refused" no frontend

**Causa:** Backend não está rodando

**Solução:**
```bash
cd pdv-posto-combustivel
mvn spring-boot:run
```

---

### Problema: Tabela acessos não tem as colunas novas

**Solução:** Execute o script de migração:
```sql
ALTER TABLE acessos ADD COLUMN IF NOT EXISTS pessoa_id BIGINT;
ALTER TABLE acessos ADD COLUMN IF NOT EXISTS nome_completo VARCHAR(200);
ALTER TABLE acessos ADD COLUMN IF NOT EXISTS role VARCHAR(50) DEFAULT 'FRENTISTA';
```

---

## 📝 ENDPOINTS DA API

### Login
```http
POST /api/acessos/login
Content-Type: application/json

{
  "usuario": "admin",
  "senha": "admin123"
}
```

**Response:**
```json
{
  "id": 1,
  "usuario": "admin",
  "senha": "admin123",
  "nomeCompleto": "Administrador do Sistema",
  "role": "ADMIN",
  "pessoaId": null
}
```

### Criar Acesso
```http
POST /api/acessos
Content-Type: application/json

{
  "usuario": "joao.silva",
  "senha": "senha123",
  "pessoaId": 1,
  "role": "FRENTISTA"
}
```

### Listar Acessos
```http
GET /api/acessos
```

---

## 🎯 TESTES PARA O PROFESSOR

### ✅ Checklist de Testes:

- [ ] 1. Executar scripts SQL
- [ ] 2. Iniciar backend (mvn spring-boot:run)
- [ ] 3. Iniciar frontend (mvn exec:java)
- [ ] 4. Login como admin (admin / admin123)
- [ ] 5. Cadastrar uma pessoa via "Gerenciar Pessoas"
- [ ] 6. Criar acesso para essa pessoa via "Gerenciar Acessos"
- [ ] 7. Fazer logout
- [ ] 8. Login com o novo usuário criado
- [ ] 9. Verificar que frentista só vê "Gerenciar Bombas"
- [ ] 10. Login novamente como admin
- [ ] 11. Verificar que admin vê todos os gerenciadores

---

## 🔒 SEGURANÇA

### Senhas Padrão (ALTERAR EM PRODUÇÃO):
- **Admin:** admin / admin123

### Recomendações:
1. Alterar senha do admin após primeiro acesso
2. Usar senhas fortes para frentistas
3. Não compartilhar credenciais
4. Fazer backup regular do banco de dados

---

## 📞 SUPORTE

Se algo não funcionar:

1. **Verificar logs do backend:**
   - Procurar por erros relacionados a "acessos" ou "login"
   
2. **Verificar logs do frontend:**
   - Procurar por "Connection refused" ou "404"

3. **Testar endpoint manualmente:**
   - Usar Postman ou navegador
   - http://localhost:8080/api/acessos/login

4. **Verificar banco de dados:**
   - Conectar no pgAdmin
   - Verificar se tabelas existem
   - Verificar se admin está cadastrado

---

## 🎉 CONCLUSÃO

Agora o sistema:
- ✅ Usa banco de dados para autenticação
- ✅ Vincula acessos a pessoas
- ✅ Diferencia ADMIN de FRENTISTA
- ✅ Tem endpoint de login dedicado
- ✅ Mostra nome completo no login
- ✅ Está pronto para demonstração!

**Bons testes! 🚀**

