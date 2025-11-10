-- Script para remover a coluna role da tabela pessoas
-- A coluna role não é mais necessária pois está na tabela acessos

-- 1. Ver estrutura atual da tabela
\d pessoas

-- 2. Verificar se a coluna existe
SELECT column_name, data_type
FROM information_schema.columns
WHERE table_name = 'pessoas' AND column_name = 'role';

-- 3. Remover a coluna role
ALTER TABLE pessoas DROP COLUMN IF EXISTS role;

-- 4. Verificar a estrutura após remoção
\d pessoas

-- 5. Ver dados para confirmar
SELECT id, nome_completo, cpf_cnpj, data_nascimento, tipo_pessoa, ativo
FROM pessoas
ORDER BY id DESC
LIMIT 10;

-- OBSERVAÇÃO:
-- A coluna role foi removida de pessoas porque:
-- 1. A função (FRENTISTA, ADMIN, etc) está na tabela acessos
-- 2. Uma pessoa pode existir sem ter acesso ao sistema
-- 3. A role é uma característica do acesso, não da pessoa
-- 4. Simplifica o gerenciamento de pessoas

