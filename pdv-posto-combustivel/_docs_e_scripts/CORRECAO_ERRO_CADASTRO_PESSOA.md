# 🔧 CORREÇÃO: Erro ao Cadastrar Pessoa

## ❌ Problema
```
HTTP Error: 500 - Erro interno do servidor: could not execute statement 
[ERROR: new row for relation "pessoas" violates check constraint 
"pessoas_tipo_pessoa_check"]
```

**Causa:** A constraint da tabela `pessoas` estava esperando valores diferentes dos que o frontend estava enviando.

---

## ✅ SOLUÇÃO APLICADA

### 1. **Tipos de Pessoa Corrigidos**

#### Antes (ERRADO):
```
FRENTISTA, FUNCIONARIO, FORNECEDOR, CLIENTE
```

#### Depois (CORRETO):
```
FISICA, JURIDICA
```

### 2. **Mudança no Frontend**

**Arquivo:** `CadastroPessoaView.java`

**Linha alterada:**
```java
// ANTES:
cmbTipoPessoa = new JComboBox<>(new String[]{"FRENTISTA", "FUNCIONARIO", "FORNECEDOR", "CLIENTE"});

// DEPOIS:
cmbTipoPessoa = new JComboBox<>(new String[]{"FISICA", "JURIDICA"});
```

---

## 🗄️ ESTRUTURA DO BANCO

### Tabela `pessoas` - Constraint:
```sql
CHECK (tipo_pessoa IN ('FISICA', 'JURIDICA'))
```

### Significado:
- **FISICA**: Pessoa Física (CPF) - Para frentistas, funcionários
- **JURIDICA**: Pessoa Jurídica (CNPJ) - Para fornecedores

---

## 📋 COMO EXECUTAR A CORREÇÃO

### 1. Executar Script SQL no pgAdmin:

```sql
-- Remover constraint antiga
ALTER TABLE pessoas DROP CONSTRAINT IF EXISTS pessoas_tipo_pessoa_check;

-- Adicionar constraint correta
ALTER TABLE pessoas 
ADD CONSTRAINT pessoas_tipo_pessoa_check 
CHECK (tipo_pessoa IN ('FISICA', 'JURIDICA'));

-- Atualizar dados existentes (se houver)
UPDATE pessoas SET tipo_pessoa = 'FISICA' 
WHERE tipo_pessoa IN ('FRENTISTA', 'FUNCIONARIO', 'CLIENTE');

UPDATE pessoas SET tipo_pessoa = 'JURIDICA' 
WHERE tipo_pessoa IN ('FORNECEDOR');
```

### 2. Reiniciar o Frontend:
- Parar a aplicação (se estiver rodando)
- Executar novamente: `mvn exec:java`

---

## 🧪 TESTAR AGORA

### 1. Na tela de Login, clicar em **"Preencher Informações"**

### 2. Preencher o formulário:
```
Nome Completo: João da Silva
CPF/CNPJ: 12345678900
Data de Nascimento: 15/03/1990 (formato: dd/MM/yyyy)
Tipo de Pessoa: FISICA (ou JURIDICA)
```

### 3. Clicar em **"Cadastrar"**

### 4. Verificar no banco:
```sql
SELECT * FROM pessoas ORDER BY id DESC;
```

**Resultado esperado:**
```
id | nome_completo  | cpf_cnpj     | data_nascimento | tipo_pessoa | ativo
17 | João da Silva  | 12345678900  | 1990-03-15     | FISICA     | true
```

---

## 📊 COMPARAÇÃO

| Campo | Antes | Depois |
|-------|-------|--------|
| **Opções** | FRENTISTA, FUNCIONARIO, FORNECEDOR, CLIENTE | FISICA, JURIDICA |
| **Propósito** | Função da pessoa | Tipo jurídico |
| **Constraint** | ❌ Não coincidia | ✅ Correto |

---

## 🎯 POR QUE ESTA MUDANÇA?

### Antes:
- Confundia **função** (frentista, fornecedor) com **tipo jurídico**
- A tabela já tem campo `role` para função
- Constraint no banco esperava FISICA/JURIDICA

### Depois:
- **tipo_pessoa**: Tipo jurídico (FISICA/JURIDICA)
- **role**: Função no sistema (FRENTISTA, ADMIN, USUÁRIO)
- Alinhado com o banco de dados

### Estrutura Correta:
```
PESSOA:
├─ nome_completo: "João da Silva"
├─ cpf_cnpj: "12345678900"
├─ tipo_pessoa: "FISICA" (tipo jurídico)
├─ data_nascimento: "1990-03-15"
└─ role: "FRENTISTA" (definido depois)

ACESSO:
├─ usuario: "joao.silva"
├─ senha: "senha123"
├─ pessoa_id: 17 (vincula à pessoa)
└─ role: "FRENTISTA" (copiado da pessoa)
```

---

## 🔍 VERIFICAÇÃO ADICIONAL

### Ver constraint atual:
```sql
SELECT conname, pg_get_constraintdef(oid) 
FROM pg_constraint 
WHERE conrelid = 'pessoas'::regclass 
AND conname LIKE '%tipo_pessoa%';
```

**Resultado esperado:**
```
conname                      | pg_get_constraintdef
pessoas_tipo_pessoa_check   | CHECK ((tipo_pessoa IN ('FISICA', 'JURIDICA')))
```

---

## 🚨 SE AINDA DER ERRO

### Erro: "violates check constraint"

**Solução 1:** Verificar se o script SQL foi executado
```sql
-- Verificar constraint
\d pessoas
```

**Solução 2:** Remover e recriar constraint
```sql
ALTER TABLE pessoas DROP CONSTRAINT pessoas_tipo_pessoa_check;
ALTER TABLE pessoas 
ADD CONSTRAINT pessoas_tipo_pessoa_check 
CHECK (tipo_pessoa IN ('FISICA', 'JURIDICA'));
```

**Solução 3:** Limpar cache do Maven
```bash
mvn clean install
mvn exec:java
```

---

## 📝 OBSERVAÇÕES

### 1. **Dados Antigos**
Se você tinha pessoas cadastradas com `tipo_pessoa = 'FRENTISTA'`, o script SQL atualiza automaticamente para `'FISICA'`.

### 2. **Campo `role`**
O campo `role` na tabela `pessoas` é diferente de `tipo_pessoa`:
- `tipo_pessoa`: FISICA ou JURIDICA (tipo jurídico)
- `role`: FRENTISTA, ADMIN, USUÁRIO (função no sistema)

### 3. **Sem CLIENTE**
Removido conforme solicitado, pois clientes não usam o sistema.

---

## ✅ RESULTADO FINAL

Agora o cadastro funciona corretamente:

1. ✅ Frontend envia: `tipo_pessoa: "FISICA"` ou `"JURIDICA"`
2. ✅ Backend valida e salva
3. ✅ Banco aceita: constraint satisfeita
4. ✅ Pessoa cadastrada com sucesso!

**Teste novamente e deve funcionar! 🎉**

