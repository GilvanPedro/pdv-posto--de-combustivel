# ✅ REMOÇÃO DA COLUNA `role` DA TABELA `pessoas` - CONCLUÍDA

## 🎯 OBJETIVO

Remover a coluna `role` da tabela `pessoas` no banco de dados, pois essa informação agora está apenas na tabela `acessos`.

---

## 📋 ALTERAÇÕES REALIZADAS

### **Backend - Arquivos Modificados:**

1. ✅ **Pessoa.java** (Entity)
   - Removido campo `private String role`
   - Removido métodos `getRole()` e `setRole()`

2. ✅ **PessoaRequest.java** (DTO)
   - Removido campo `@JsonProperty("role")`
   - Removido métodos `getRole()` e `setRole()`

3. ✅ **PessoaResponse.java** (DTO)
   - Removido campo `@JsonProperty("role")`
   - Removido métodos `getRole()` e `setRole()`

4. ✅ **PessoaService.java**
   - Removido `p.setRole(req.getRole())` do método `update()`
   - Removido `if (req.getRole() != null) p.setRole(req.getRole())` do método `patch()`
   - Removido `pessoa.setRole(req.getRole())` do método `toEntity()`
   - Removido `response.setRole(p.getRole())` do método `toResponse()`

### **Frontend - Arquivos Modificados:**

5. ✅ **Pessoa.java** (Model)
   - Removido campo `private String role`
   - Removido métodos `getRole()`, `setRole()` e `isAdmin()`
   - Construtores ajustados (sem role)

6. ✅ **PessoaResponse.java** (DTO)
   - Removido campo `@JsonProperty("role")`
   - Removido métodos `getRole()` e `setRole()`

7. ✅ **PessoaService.java**
   - Removido `pessoa.setRole(...)` do método `listPessoas()`
   - Removido `request.setRole(pessoa.getRole())` do método `updatePessoa()`

8. ✅ **PessoaList.java**
   - Removido `String role = "FRENTISTA"` do método `adicionarPessoa()`
   - Removido `pessoa.setRole(role)` do método `editarPessoa()`
   - Construtores de Pessoa ajustados (sem role)

---

## 🗄️ BANCO DE DADOS - SCRIPT SQL

### **Execute este script no pgAdmin 4:**

```sql
-- ========================================
-- REMOVER COLUNA ROLE DA TABELA PESSOAS
-- ========================================

-- 1. Ver estrutura atual
SELECT column_name, data_type, is_nullable
FROM information_schema.columns
WHERE table_name = 'pessoas'
ORDER BY ordinal_position;

-- 2. Remover a coluna role
ALTER TABLE pessoas DROP COLUMN IF EXISTS role;

-- 3. Verificar estrutura após remoção
SELECT column_name, data_type, is_nullable
FROM information_schema.columns
WHERE table_name = 'pessoas'
ORDER BY ordinal_position;

-- 4. Ver dados das pessoas (sem coluna role)
SELECT id, nome_completo, cpf_cnpj, data_nascimento, tipo_pessoa, ativo
FROM pessoas
ORDER BY id DESC
LIMIT 10;

-- 5. Verificar que a role ainda existe em acessos
SELECT a.id, a.usuario, a.nome_completo, a.role, a.pessoa_id,
       p.nome_completo as pessoa_nome
FROM acessos a
LEFT JOIN pessoas p ON a.pessoa_id = p.id
ORDER BY a.id DESC
LIMIT 10;
```

---

## 🧪 COMO TESTAR

### **1. Executar o Script SQL**
```
1. Abrir pgAdmin 4
2. Conectar no banco pdvpostocombustivel
3. Abrir Query Tool (Ctrl + Shift + Q)
4. Colar e executar o script acima
5. Verificar que a coluna role foi removida
```

### **2. Reiniciar o Backend**
```bash
cd pdv-posto-combustivel
mvn clean install
mvn spring-boot:run
```

### **3. Reiniciar o Frontend**
```bash
cd JavaPoo-Front-End-main
mvn clean install
mvn exec:java
```

### **4. Testar Funcionalidades**

#### **a) Cadastrar Nova Pessoa:**
```
Tela Login → "Preencher Informações"
Preencher: Nome, CPF, Data, Tipo
Verificar que foi salvo sem erro
```

#### **b) Gerenciar Pessoas:**
```
Login admin → "Gerenciar Pessoas"
Adicionar/Editar/Remover pessoa
Verificar que funciona sem campo role
```

#### **c) Criar Credenciais:**
```
Admin → "Cadastrar Novo Frentista"
Selecionar pessoa
Criar usuário/senha
Verificar na tabela acessos que role está preenchida
```

### **5. Verificar no Banco:**

```sql
-- Ver estrutura de pessoas (SEM role)
\d pessoas

-- Ver dados de pessoas (SEM role)
SELECT * FROM pessoas ORDER BY id DESC LIMIT 5;

-- Ver que role está em acessos
SELECT * FROM acessos ORDER BY id DESC LIMIT 5;
```

---

## 📊 RESULTADO ESPERADO

### **Tabela `pessoas` - APÓS remoção:**
```
Colunas:
- id (bigint)
- nome_completo (varchar 200)
- cpf_cnpj (varchar 20)
- data_nascimento (date)
- tipo_pessoa (varchar 15)
- ativo (boolean)

❌ role (REMOVIDA)
```

### **Tabela `acessos` - mantém role:**
```
Colunas:
- id (bigint)
- senha (varchar 255)
- usuario (varchar 255)
- nome_completo (varchar 255)
- role (varchar 50) ← AQUI ESTÁ A ROLE
- pessoa_id (bigint)
```

---

## 🎯 VANTAGENS DA MUDANÇA

### **Antes:**
```
❌ Campo role duplicado (pessoas E acessos)
❌ Confusão: pessoa pode ter role sem ter acesso
❌ Necessário sincronizar role em duas tabelas
```

### **Depois:**
```
✅ Campo role apenas em acessos
✅ Pessoa é apenas dados pessoais
✅ Role é característica do acesso, não da pessoa
✅ Mais coerente e organizado
```

---

## 📁 RESUMO DE ARQUIVOS

### **Backend (8 alterações):**
1. ✅ Pessoa.java
2. ✅ PessoaRequest.java
3. ✅ PessoaResponse.java
4. ✅ PessoaService.java (4 métodos)

### **Frontend (8 alterações):**
5. ✅ Pessoa.java (model)
6. ✅ PessoaResponse.java (dto)
7. ✅ PessoaService.java (2 métodos)
8. ✅ PessoaList.java (2 métodos)

### **Banco de Dados:**
9. ✅ Script SQL criado: `REMOVER_COLUNA_ROLE_PESSOAS.sql`

---

## ⚠️ OBSERVAÇÕES IMPORTANTES

1. **Backup:** Faça backup do banco antes de executar o script SQL
2. **Role em acessos:** A coluna role permanece na tabela acessos (correto)
3. **Dados existentes:** A remoção da coluna não afeta os dados de pessoas
4. **Sem impacto:** Acessos já criados mantêm a role
5. **Clean install:** Execute `mvn clean install` para recompilar

---

## ✅ CHECKLIST FINAL

- [ ] Script SQL executado no pgAdmin
- [ ] Coluna role removida da tabela pessoas
- [ ] Backend recompilado (mvn clean install)
- [ ] Backend reiniciado (mvn spring-boot:run)
- [ ] Frontend recompilado (mvn clean install)
- [ ] Frontend reiniciado (mvn exec:java)
- [ ] Cadastro de pessoa testado
- [ ] Gerenciador de pessoas testado
- [ ] Criação de credenciais testada
- [ ] Banco verificado (sem coluna role em pessoas)

---

## 🚀 PRÓXIMOS PASSOS

1. ✅ Executar script SQL no pgAdmin
2. ✅ Reiniciar backend
3. ✅ Reiniciar frontend
4. ✅ Testar todas as funcionalidades
5. ✅ Verificar que tudo funciona sem a coluna role

**Após executar o script SQL, o sistema estará limpo e organizado! 🎉**

