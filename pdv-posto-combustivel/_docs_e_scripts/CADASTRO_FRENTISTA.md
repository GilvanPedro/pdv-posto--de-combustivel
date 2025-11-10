# Cadastro de Frentistas

## Descrição
Este módulo permite que o **Administrador** cadastre novos frentistas no sistema, criando contas de acesso para que possam operar as bombas de combustível.

## Funcionalidades

### ✅ Cadastro Completo
- **Nome Completo**: Identificação do frentista
- **Usuário**: Login único para acesso ao sistema
- **Senha**: Credencial de acesso (mínimo 4 caracteres)
- **Confirmação de Senha**: Validação de segurança

### ✅ Validações Implementadas
1. **Campos Obrigatórios**: Todos os campos devem ser preenchidos
2. **Tamanho Mínimo do Usuário**: Mínimo de 3 caracteres
3. **Tamanho Mínimo da Senha**: Mínimo de 4 caracteres
4. **Confirmação de Senha**: As senhas devem ser idênticas
5. **Usuário Único**: Verifica se o usuário já existe no banco de dados

### ✅ Integração com Backend
- Conecta-se à API REST do backend
- Salva os dados no banco de dados PostgreSQL
- Validação em tempo real de usuários duplicados

## Como Acessar

### Para Administradores
1. Faça login como **Administrador**
2. No painel principal, clique em **"Cadastrar Novo Frentista"** (botão roxo)
3. Preencha todos os campos do formulário
4. Clique em **"Cadastrar Frentista"**

### Permissões
- ✅ **Administrador**: Pode cadastrar novos frentistas
- ❌ **Frentista**: Não tem permissão para cadastrar usuários

## Interface

### Cores e Design
- **Cor Principal**: Azul (#34495e)
- **Botão Cadastrar**: Verde (#2ecc71)
- **Botão Cancelar**: Laranja (#e67e22)
- **Interface Responsiva**: Janela de 450x500 pixels

### Campos do Formulário
```
┌─────────────────────────────────────┐
│    Cadastrar Frentista              │
│  Adicione um novo frentista ao     │
│           sistema                   │
│                                     │
│  Nome Completo:                     │
│  [________________________]         │
│                                     │
│  Usuário de Acesso:                 │
│  [________________________]         │
│                                     │
│  Senha:                             │
│  [________________________]         │
│                                     │
│  Confirmar Senha:                   │
│  [________________________]         │
│                                     │
│  [Cadastrar]    [Cancelar]         │
└─────────────────────────────────────┘
```

## Fluxo de Cadastro

```
┌─────────────────────────────────────────────┐
│ 1. Administrador clica em                   │
│    "Cadastrar Novo Frentista"               │
└────────────┬────────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────────┐
│ 2. Preenche o formulário                    │
│    - Nome Completo                          │
│    - Usuário                                │
│    - Senha                                  │
│    - Confirmação                            │
└────────────┬────────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────────┐
│ 3. Sistema valida os dados                  │
│    - Campos obrigatórios                    │
│    - Tamanho mínimo                         │
│    - Senhas conferem                        │
│    - Usuário único                          │
└────────────┬────────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────────┐
│ 4. Dados enviados para o backend            │
│    POST /acessos                            │
└────────────┬────────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────────┐
│ 5. Backend salva no PostgreSQL              │
│    Tabela: acessos                          │
└────────────┬────────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────────┐
│ 6. Mensagem de sucesso                      │
│    "Frentista cadastrado com sucesso!"      │
└─────────────────────────────────────────────┘
```

## Mensagens do Sistema

### ✅ Sucesso
```
Frentista cadastrado com sucesso!

Nome: João Silva
Usuário: joao.silva

O frentista já pode fazer login no sistema.
```

### ❌ Erros Comuns

**Campos Vazios**
```
Por favor, preencha todos os campos!
```

**Usuário Muito Curto**
```
O usuário deve ter no mínimo 3 caracteres!
```

**Senha Muito Curta**
```
A senha deve ter no mínimo 4 caracteres!
```

**Senhas Não Conferem**
```
As senhas não conferem!
```

**Usuário Duplicado**
```
Este usuário já está cadastrado no sistema!
```

## Segurança

### 🔒 Medidas Implementadas
1. **Senhas Mascaradas**: Campos de senha usam `JPasswordField`
2. **Validação de Duplicidade**: Verifica usuários existentes antes de cadastrar
3. **Confirmação de Senha**: Dupla verificação da senha
4. **Acesso Restrito**: Apenas administradores podem cadastrar

### 🔐 Recomendações
- Use senhas fortes (mínimo 8 caracteres recomendado)
- Não compartilhe credenciais entre usuários
- Altere senhas periodicamente

## Tecnologias Utilizadas

### Frontend
- **Java Swing**: Interface gráfica
- **HTTP Client**: Comunicação com backend
- **DTO Pattern**: Transferência de dados

### Backend
- **Spring Boot**: Framework REST API
- **PostgreSQL**: Banco de dados
- **JPA/Hibernate**: ORM

## Estrutura de Arquivos

```
src/main/java/br/com/PdvFrontEnd/
├── view/
│   ├── RegisterView.java       ← Tela de cadastro
│   └── MainApp.java            ← Botão de acesso
├── service/
│   └── AcessoService.java      ← Lógica de negócio
├── model/
│   └── Acesso.java             ← Modelo de dados
└── dto/
    ├── AcessoRequest.java      ← Request DTO
    └── AcessoResponse.java     ← Response DTO
```

## Banco de Dados

### Tabela: `acessos`
```sql
CREATE TABLE acessos (
    id SERIAL PRIMARY KEY,
    usuario VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(50) DEFAULT 'FRENTISTA',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## Testes

### Cenários de Teste
1. ✅ Cadastrar frentista com dados válidos
2. ✅ Tentar cadastrar com campos vazios
3. ✅ Tentar cadastrar usuário duplicado
4. ✅ Tentar cadastrar com senhas diferentes
5. ✅ Validar tamanho mínimo de usuário e senha
6. ✅ Cancelar cadastro

## Manutenção

### Como Alterar Validações
Edite o arquivo `RegisterView.java`, método `handleRegister()`:

```java
if (username.length() < 3) {
    // Altere o número 3 para o tamanho mínimo desejado
}

if (password.length() < 4) {
    // Altere o número 4 para o tamanho mínimo desejado
}
```

### Como Adicionar Novos Campos
1. Adicione o campo visual no método `initComponents()`
2. Capture o valor no método `handleRegister()`
3. Adicione validação se necessário
4. Atualize o DTO `AcessoRequest`
5. Atualize o backend

## Próximas Melhorias

### 🚀 Sugestões
- [ ] Adicionar campo de email
- [ ] Validação de força de senha
- [ ] Geração automática de senha temporária
- [ ] Envio de email com credenciais
- [ ] Log de auditoria de cadastros
- [ ] Edição de dados do frentista
- [ ] Desativação de conta (soft delete)

## Suporte

### Dúvidas ou Problemas?
- Verifique se o backend está rodando (porta 8080)
- Verifique a conexão com o PostgreSQL
- Consulte os logs do sistema
- Entre em contato com o desenvolvedor

---

**Desenvolvido para o Sistema PDV - Posto de Combustível**

