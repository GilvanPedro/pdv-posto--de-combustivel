-- Script de migração para adicionar relacionamento entre Acessos e Pessoas
-- Execute este script no PostgreSQL

-- 1. Adicionar colunas na tabela acessos
ALTER TABLE acessos ADD COLUMN IF NOT EXISTS pessoa_id BIGINT;
ALTER TABLE acessos ADD COLUMN IF NOT EXISTS nome_completo VARCHAR(200);
ALTER TABLE acessos ADD COLUMN IF NOT EXISTS role VARCHAR(50) DEFAULT 'FRENTISTA';

-- 2. Adicionar constraint de chave estrangeira
ALTER TABLE acessos
ADD CONSTRAINT fk_acessos_pessoa
FOREIGN KEY (pessoa_id) REFERENCES pessoas(id) ON DELETE SET NULL;

-- 3. Tornar o campo usuario único
ALTER TABLE acessos
ADD CONSTRAINT uk_acessos_usuario UNIQUE (usuario);

-- 4. Atualizar role existentes para ADMIN (se houver usuário admin)
UPDATE acessos SET role = 'ADMIN' WHERE usuario = 'admin';

-- 5. Verificar estrutura
SELECT column_name, data_type, is_nullable, column_default
FROM information_schema.columns
WHERE table_name = 'acessos'
ORDER BY ordinal_position;

-- 6. Verificar dados
SELECT * FROM acessos;

