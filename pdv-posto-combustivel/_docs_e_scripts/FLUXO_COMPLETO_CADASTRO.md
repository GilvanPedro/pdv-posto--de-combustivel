# 🎯 NOVO FLUXO DO SISTEMA - DOCUMENTAÇÃO

## 📋 VISÃO GERAL

O sistema agora tem um fluxo completo para cadastro de frentistas em duas etapas:

1. **Frentista preenche dados pessoais** (na tela de login)
2. **Admin cria credenciais de acesso** (vinculadas à pessoa)

---

## 🔄 FLUXO COMPLETO

### 1️⃣ TELA DE LOGIN - Botões Disponíveis

#### **Entrar**
- Login normal para admin ou frentista

#### **Preencher Informações** (VERDE)
- Novo botão para frentistas
- Abre a tela de cadastro de dados pessoais
- Salva na tabela `pessoas`

#### **Cadastrar Frentista** (LARANJA)
- Aparece se não existir frentista
- Para compatibilidade com testes

#### **Cadastrar Admin** (ROXO)
- Aparece se não existir admin
- Para o professor criar o primeiro admin

---

## 👤 PROCESSO PARA NOVO FRENTISTA

### Passo 1: Frentista Preenche Informações
```
Tela de Login
    ↓
Clica em "Preencher Informações"
    ↓
Tela de Cadastro de Pessoa
    ↓
Preenche:
- Nome Completo: João da Silva
- CPF/CNPJ: 12345678900
- Data Nascimento: 15/03/1990
- Tipo: FRENTISTA
    ↓
Clica em "Cadastrar"
    ↓
Dados salvos na tabela PESSOAS
    ↓
Mensagem: "Aguarde o administrador criar suas credenciais"
```

### Passo 2: Admin Cria Credenciais
```
Admin faz login
    ↓
No painel, clica em "Cadastrar Novo Frentista"
    ↓
Tela de Criar Credenciais
    ↓
Seleciona: João da Silva - CPF: 12345678900
    ↓
Preenche:
- Usuário: joao.silva
- Senha: senha123
- Confirmar Senha: senha123
    ↓
Clica em "Criar Acesso"
    ↓
Acesso salvo na tabela ACESSOS
(vinculado à pessoa pelo ID)
    ↓
Credenciais prontas!
```

### Passo 3: Frentista Faz Login
```
Tela de Login
    ↓
Tipo: Frentista
Usuário: joao.silva
Senha: senha123
    ↓
Login com sucesso!
```

---

## 🗄️ ESTRUTURA NO BANCO DE DADOS

### Tabela `pessoas`
```sql
id              | nome_completo     | cpf_cnpj     | data_nascimento | tipo_pessoa | role
5               | João da Silva     | 12345678900  | 2003-08-10     | FRENTISTA  | [null]
```

### Tabela `acessos` (após admin criar credenciais)
```sql
id | senha   | usuario      | nome_completo   | role      | pessoa_id
9  | senha123| joao.silva   | João da Silva   | FRENTISTA | 5
```

**Relacionamento:** `acessos.pessoa_id` → `pessoas.id`

---

## 🎨 INTERFACES

### LoginView - Botões:
1. ✅ **Entrar** (Azul) - Login normal
2. ✅ **Preencher Informações** (Verde) - Cadastro de dados pessoais
3. ✅ **Cadastrar Frentista** (Laranja) - Se não existir frentista
4. ✅ **Cadastrar Admin** (Roxo) - Se não existir admin

### CadastroPessoaView - Campos:
- Nome Completo
- CPF/CNPJ
- Data de Nascimento (dd/MM/yyyy)
- Tipo de Pessoa (FRENTISTA, FUNCIONARIO, FORNECEDOR, CLIENTE)

### RegisterView - Campos:
- Selecionar Pessoa (dropdown com pessoas cadastradas)
- Usuário de Acesso
- Senha
- Confirmar Senha

---

## ✅ VALIDAÇÕES

### CadastroPessoaView:
- ✅ Todos os campos obrigatórios
- ✅ Data no formato dd/MM/yyyy
- ✅ Validação de maior idade (opcional)
- ✅ CPF/CNPJ único no banco

### RegisterView:
- ✅ Pessoa deve estar selecionada
- ✅ Usuário mínimo 3 caracteres
- ✅ Senha mínimo 4 caracteres
- ✅ Senhas devem conferir
- ✅ Usuário único no banco

---

## 🧪 TESTE COMPLETO

### 1. Cadastrar Dados Pessoais (Frentista)
```
1. Abrir app
2. Na tela de login, clicar em "Preencher Informações"
3. Preencher:
   - Nome: João da Silva
   - CPF: 12345678900
   - Data: 15/03/1990
   - Tipo: FRENTISTA
4. Clicar em "Cadastrar"
5. Mensagem de sucesso aparece
```

### 2. Criar Credenciais (Admin)
```
1. Login como admin (admin / admin123)
2. No painel, clicar em "Cadastrar Novo Frentista"
3. Selecionar: João da Silva - CPF: 12345678900
4. Preencher:
   - Usuário: joao.silva
   - Senha: senha123
   - Confirmar: senha123
5. Clicar em "Criar Acesso"
6. Mensagem: "Credenciais criadas com sucesso!"
```

### 3. Login do Frentista
```
1. Fazer logout do admin
2. Na tela de login:
   - Tipo: Frentista
   - Usuário: joao.silva
   - Senha: senha123
3. Clicar em "Entrar"
4. Login com sucesso!
5. Painel do frentista abre
```

---

## 📊 VANTAGENS DESTE FLUXO

### ✅ Segurança:
- Frentista não cria própria senha
- Admin controla quem tem acesso
- Dados pessoais separados das credenciais

### ✅ Rastreabilidade:
- Cada acesso vinculado a uma pessoa real
- Histórico completo no banco
- CPF/CNPJ registrado

### ✅ Flexibilidade:
- Pessoa pode existir sem acesso
- Admin pode criar/remover acessos
- Reutilização de dados pessoais

### ✅ Organização:
- Separação clara: Pessoa ↔ Acesso
- Tabelas normalizadas
- Fácil manutenção

---

## 🔍 VERIFICAÇÕES NO BANCO

### Verificar pessoas cadastradas:
```sql
SELECT id, nome_completo, cpf_cnpj, tipo_pessoa 
FROM pessoas 
ORDER BY id DESC;
```

### Verificar acessos criados:
```sql
SELECT a.id, a.usuario, a.nome_completo, a.role, a.pessoa_id,
       p.nome_completo as pessoa_nome, p.cpf_cnpj
FROM acessos a
LEFT JOIN pessoas p ON a.pessoa_id = p.id
ORDER BY a.id DESC;
```

### Ver relacionamento completo:
```sql
SELECT 
    p.id as pessoa_id,
    p.nome_completo,
    p.cpf_cnpj,
    p.tipo_pessoa,
    a.id as acesso_id,
    a.usuario,
    a.role
FROM pessoas p
LEFT JOIN acessos a ON a.pessoa_id = p.id
ORDER BY p.id DESC;
```

---

## 📝 OBSERVAÇÕES IMPORTANTES

### Para o Professor:

1. **Primeiro Admin:**
   - Na primeira execução, o botão "Cadastrar Admin" aparece
   - Use este botão para criar o primeiro admin
   - Depois disso, o botão desaparece

2. **Fluxo Real:**
   - Frentistas usam "Preencher Informações"
   - Admin cria credenciais depois
   - Login normal para ambos

3. **Testes:**
   - Pode criar múltiplas pessoas
   - Pode criar múltiplos acessos
   - Cada acesso vinculado a uma pessoa

---

## 🎯 COMANDOS SQL ÚTEIS

### Limpar dados de teste:
```sql
-- Cuidado: Apaga todos os acessos e pessoas
DELETE FROM acessos WHERE role = 'FRENTISTA';
DELETE FROM pessoas WHERE tipo_pessoa = 'FRENTISTA';
```

### Criar admin manualmente:
```sql
INSERT INTO acessos (usuario, senha, role, nome_completo)
VALUES ('admin', 'admin123', 'ADMIN', 'Administrador do Sistema');
```

### Ver estatísticas:
```sql
-- Pessoas cadastradas
SELECT tipo_pessoa, COUNT(*) FROM pessoas GROUP BY tipo_pessoa;

-- Acessos por role
SELECT role, COUNT(*) FROM acessos GROUP BY role;

-- Pessoas com acesso
SELECT 
    COUNT(DISTINCT p.id) as pessoas_com_acesso,
    (SELECT COUNT(*) FROM pessoas) as total_pessoas
FROM pessoas p
INNER JOIN acessos a ON a.pessoa_id = p.id;
```

---

## 🚀 PRÓXIMOS PASSOS

### Melhorias Futuras:
1. [ ] Editar dados pessoais
2. [ ] Desativar/reativar acesso
3. [ ] Histórico de acessos
4. [ ] Foto do frentista
5. [ ] Documentos anexados
6. [ ] Envio de credenciais por email
7. [ ] Reset de senha
8. [ ] Auditoria de ações

---

**Sistema pronto para demonstração ao professor! 🎉**

**Fluxo completo implementado:**
- ✅ Cadastro de dados pessoais
- ✅ Criação de credenciais pelo admin
- ✅ Login com validações
- ✅ Relacionamento no banco
- ✅ Interface intuitiva

