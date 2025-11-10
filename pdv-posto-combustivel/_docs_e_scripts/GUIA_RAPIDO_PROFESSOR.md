# 🎯 GUIA RÁPIDO PARA O PROFESSOR

## ⚡ CONFIGURAÇÃO EM 3 PASSOS

### PASSO 1: Atualizar o Banco de Dados (2 minutos)

1. Abrir **pgAdmin 4**
2. Conectar no banco `pdvpostocombustivel`
3. Abrir **Query Tool**
4. Executar este script:

```sql
-- Adicionar novas colunas
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

-- Verificar
SELECT * FROM acessos WHERE usuario = 'admin';
```

✅ **Resultado esperado:** Uma linha com admin aparece na consulta.

---

### PASSO 2: Iniciar o Backend (1 minuto)

1. Abrir terminal
2. Navegar até a pasta do backend:
```bash
cd C:\Users\sidney\Documents\Projetos\pdv-posto-combustivel-main\pdv-posto-combustivel-main\pdv-posto-combustivel
```

3. Executar:
```bash
mvn spring-boot:run
```

4. **Aguardar a mensagem:**
```
Tomcat started on port(s): 8080 (http)
```

✅ **Backend rodando!**

---

### PASSO 3: Iniciar o Frontend (1 minuto)

1. Abrir **OUTRO** terminal
2. Navegar até a pasta do frontend:
```bash
cd C:\Users\sidney\Documents\Projetos\pdv-posto-combustivel-main\pdv-posto-combustivel-main\JavaPoo-Front-End-main
```

3. Executar:
```bash
mvn exec:java
```

4. **Aguardar a janela de login aparecer**

✅ **Frontend rodando!**

---

## 🔐 TESTE 1: Login como Admin

1. Na tela de login:
   - **Tipo:** Selecione "Administrador"
   - **Usuário:** `admin`
   - **Senha:** `admin123`
   - Clique em **Entrar**

✅ **Resultado:** Mensagem "Login realizado com sucesso! Bem-vindo(a), Administrador do Sistema!"

✅ **Painel principal aparece** com todos os gerenciadores

---

## 👤 TESTE 2: Cadastrar um Frentista

### 2.1. Cadastrar Pessoa
1. No painel principal (como admin), clique em **"Gerenciar Pessoas"**
2. Clique em **"Adicionar Pessoa"**
3. Preencha:
   - **Nome Completo:** João da Silva
   - **CPF/CNPJ:** 12345678900
   - **Data Nascimento:** 15/03/1990
   - **Tipo Pessoa:** FRENTISTA
4. Clique em **"Salvar"**

✅ **Pessoa cadastrada!**

### 2.2. Criar Credenciais de Acesso
1. Volte ao painel principal
2. Clique em **"Gerenciar Acessos"**
3. Clique em **"Adicionar Acesso"** (ou similar)
4. Preencha:
   - **Usuário:** joao.silva
   - **Senha:** senha123
   - **Selecionar Pessoa:** João da Silva (selecione da lista)
   - **Role:** FRENTISTA (ou deixe padrão)
5. Clique em **"Salvar"**

✅ **Acesso criado!**

---

## 🧪 TESTE 3: Login como Frentista

1. No painel admin, clique em **"Sair"**
2. Na tela de login:
   - **Tipo:** Selecione "Frentista"
   - **Usuário:** `joao.silva`
   - **Senha:** `senha123`
   - Clique em **Entrar**

✅ **Resultado:** Mensagem "Login realizado com sucesso! Bem-vindo(a), João da Silva!"

✅ **Painel principal aparece** mas mostra **APENAS**:
   - ✅ Gerenciar Bombas (botão verde)
   - ❌ Outros gerenciadores aparecem mas estão DESABILITADOS

---

## ✅ CHECKLIST DE VALIDAÇÃO

Para garantir que tudo está funcionando:

- [ ] Backend iniciou sem erros
- [ ] Frontend abriu a tela de login
- [ ] Login admin funcionou
- [ ] Admin vê TODOS os gerenciadores
- [ ] Conseguiu cadastrar pessoa
- [ ] Conseguiu criar acesso para a pessoa
- [ ] Logout admin funcionou
- [ ] Login frentista funcionou
- [ ] Frentista vê APENAS "Gerenciar Bombas"
- [ ] Outros botões do frentista estão desabilitados

---

## 🐛 SE ALGO DER ERRADO

### Problema: "Usuário ou senha incorretos" para admin

**Solução rápida:**
```sql
-- No pgAdmin, executar:
DELETE FROM acessos WHERE usuario = 'admin';
INSERT INTO acessos (usuario, senha, role, nome_completo)
VALUES ('admin', 'admin123', 'ADMIN', 'Administrador do Sistema');
```

---

### Problema: "Connection refused"

**Causa:** Backend não está rodando

**Solução:** Abrir terminal e executar:
```bash
cd pdv-posto-combustivel
mvn spring-boot:run
```

---

### Problema: Erro ao iniciar backend

**Possíveis causas:**
1. PostgreSQL não está rodando
2. Senha do banco está errada
3. Banco `pdvpostocombustivel` não existe

**Verificar:**
- PostgreSQL está ativo? (Services → PostgreSQL)
- Banco existe? (pgAdmin → Databases → pdvpostocombustivel)
- Senha correta em `application.properties`?

---

## 📊 VERIFICAR NO BANCO

Para confirmar que o admin foi criado:

```sql
-- Verificar admin
SELECT * FROM acessos WHERE usuario = 'admin';

-- Verificar estrutura
\d acessos

-- Listar todos os acessos
SELECT id, usuario, nome_completo, role FROM acessos;

-- Verificar pessoas cadastradas
SELECT id, nome_completo, cpf_cnpj, tipo_pessoa FROM pessoas;
```

---

## 🎓 PONTOS PARA APRESENTAÇÃO

### Funcionalidades Implementadas:
1. ✅ Sistema de login com banco de dados
2. ✅ Diferenciação Admin vs Frentista
3. ✅ Cadastro de pessoas com validações
4. ✅ Cadastro de acessos vinculados a pessoas
5. ✅ Gerenciamento de bombas (ambos)
6. ✅ Gerenciadores administrativos (só admin)
7. ✅ Restrição de acesso por role
8. ✅ API REST completa
9. ✅ Relacionamentos no banco de dados
10. ✅ Interface Swing moderna

### Tecnologias Utilizadas:
- **Backend:** Spring Boot, JPA/Hibernate, PostgreSQL
- **Frontend:** Java Swing, HTTP Client
- **Banco:** PostgreSQL com constraints e FKs
- **Arquitetura:** MVC, REST API, DTOs
- **Padrões:** Service Layer, Repository Pattern

---

## 📞 COMANDOS ÚTEIS

### Ver logs do backend:
Os logs aparecem no terminal onde executou `mvn spring-boot:run`

### Testar API manualmente:
```bash
# Windows (PowerShell)
Invoke-WebRequest -Uri http://localhost:8080/api/acessos/login `
  -Method POST `
  -ContentType "application/json" `
  -Body '{"usuario":"admin","senha":"admin123"}'
```

### Parar o backend:
Apertar `Ctrl+C` no terminal

### Parar o frontend:
Fechar a janela do app

---

## ⏱️ TEMPO ESTIMADO

- Configuração inicial: **5 minutos**
- Testes básicos: **5 minutos**
- Demonstração completa: **10 minutos**

---

## 🎯 RESULTADO ESPERADO

Ao final, você terá demonstrado:

1. ✅ Sistema completo de autenticação
2. ✅ Controle de acesso por perfil
3. ✅ CRUD de pessoas e acessos
4. ✅ Integração frontend-backend
5. ✅ Persistência no banco de dados
6. ✅ Interface gráfica funcional
7. ✅ Validações e segurança

---

**🚀 Boa apresentação!**

**💡 Dica:** Se o professor quiser ver o código, mostre:
- `Acesso.java` (entidade com relacionamento)
- `AcessoController.java` (endpoint de login)
- `LoginView.java` (integração frontend-backend)
- Scripts SQL (estrutura do banco)

