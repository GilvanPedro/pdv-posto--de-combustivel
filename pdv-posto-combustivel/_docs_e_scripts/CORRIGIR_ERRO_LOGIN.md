# 🔧 CORREÇÃO DO ERRO DE LOGIN

## ❌ Problema: "Usuário ou senha incorretos" para admin

### Causa:
O usuário admin não existe no banco de dados ou a senha está diferente.

---

## ✅ SOLUÇÃO RÁPIDA

### 1. Abrir pgAdmin 4
### 2. Conectar no banco `pdvpostocombustivel`
### 3. Abrir Query Tool (Ctrl + Shift + Q)
### 4. Executar este script:

```sql
-- Ver se o admin existe
SELECT * FROM acessos WHERE usuario = 'admin';

-- Se não aparecer nada, executar:
-- Primeiro, verificar se a tabela tem as colunas novas
ALTER TABLE acessos ADD COLUMN IF NOT EXISTS pessoa_id BIGINT;
ALTER TABLE acessos ADD COLUMN IF NOT EXISTS nome_completo VARCHAR(200);
ALTER TABLE acessos ADD COLUMN IF NOT EXISTS role VARCHAR(50) DEFAULT 'FRENTISTA';

-- Adicionar constraint FK (se não existir)
DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'fk_acessos_pessoa') THEN
        ALTER TABLE acessos 
        ADD CONSTRAINT fk_acessos_pessoa 
        FOREIGN KEY (pessoa_id) REFERENCES pessoas(id) ON DELETE SET NULL;
    END IF;
END $$;

-- Adicionar constraint UNIQUE (se não existir)
DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'uk_acessos_usuario') THEN
        ALTER TABLE acessos 
        ADD CONSTRAINT uk_acessos_usuario UNIQUE (usuario);
    END IF;
END $$;

-- Deletar admin se existir (para recriar)
DELETE FROM acessos WHERE usuario = 'admin';

-- Inserir admin novamente
INSERT INTO acessos (usuario, senha, role, nome_completo)
VALUES ('admin', 'admin123', 'ADMIN', 'Administrador do Sistema');

-- Verificar se foi inserido
SELECT id, usuario, senha, role, nome_completo FROM acessos WHERE usuario = 'admin';
```

---

## 🎯 RESULTADO ESPERADO

Você deve ver algo assim:

```
id | usuario | senha     | role  | nome_completo
1  | admin   | admin123  | ADMIN | Administrador do Sistema
```

---

## 🔍 VERIFICAR SE O BACKEND ESTÁ RODANDO

1. Abrir terminal
2. Navegar até a pasta do backend:
```bash
cd C:\Users\sidney\Documents\Projetos\pdv-posto-combustivel-main\pdv-posto-combustivel-main\pdv-posto-combustivel
```

3. Executar:
```bash
mvn spring-boot:run
```

4. Aguardar a mensagem:
```
Tomcat started on port(s): 8080 (http)
```

---

## 🧪 TESTAR O ENDPOINT DE LOGIN

Abra o navegador e acesse:
```
http://localhost:8080/swagger-ui.html
```

Ou teste com curl:
```bash
curl -X POST http://localhost:8080/api/acessos/login \
  -H "Content-Type: application/json" \
  -d "{\"usuario\":\"admin\",\"senha\":\"admin123\"}"
```

**Resposta esperada:**
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

---

## 📋 CHECKLIST

- [ ] PostgreSQL está rodando
- [ ] Banco `pdvpostocombustivel` existe
- [ ] Tabela `acessos` tem as colunas: pessoa_id, nome_completo, role
- [ ] Usuário admin existe na tabela
- [ ] Backend está rodando na porta 8080
- [ ] Endpoint `/api/acessos/login` existe
- [ ] Frontend consegue se conectar ao backend

---

## 🐛 SE AINDA DER ERRO

### Verificar logs do backend:
No terminal onde está rodando o backend, procurar por:
- `EntityNotFoundException`
- `Usuário ou senha incorretos`
- Erros de conexão com banco

### Verificar se a senha está correta:
```sql
SELECT usuario, senha, role FROM acessos WHERE usuario = 'admin';
```

A senha deve ser exatamente: **admin123**

### Recriar o admin do zero:
```sql
-- Limpar tudo
DELETE FROM acessos;

-- Inserir novamente
INSERT INTO acessos (usuario, senha, role, nome_completo)
VALUES ('admin', 'admin123', 'ADMIN', 'Administrador do Sistema');

-- Confirmar
SELECT * FROM acessos;
```

---

## 💡 DICA EXTRA

Se você quiser testar com outro usuário temporário:

```sql
INSERT INTO acessos (usuario, senha, role, nome_completo)
VALUES ('teste', '1234', 'ADMIN', 'Usuário de Teste');
```

Depois faça login com:
- **Usuário:** teste
- **Senha:** 1234
- **Tipo:** Administrador

---

**Depois de executar esses comandos SQL, tente fazer login novamente!**

