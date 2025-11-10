-- Script para ajustar a tabela pessoas
-- Remove dados antigos e ajusta constraints

-- 1. Ver dados atuais
SELECT * FROM pessoas ORDER BY id;

-- 2. Limpar dados antigos de teste (OPCIONAL - cuidado!)
-- Descomente as linhas abaixo se quiser limpar:
-- DELETE FROM acessos WHERE pessoa_id IS NOT NULL;
-- DELETE FROM pessoas WHERE id > 0;

-- 3. Verificar constraint atual
SELECT conname, pg_get_constraintdef(oid)
FROM pg_constraint
WHERE conrelid = 'pessoas'::regclass
AND conname LIKE '%tipo_pessoa%';

-- 4. Remover constraint antiga se existir
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'pessoas_tipo_pessoa_check') THEN
        ALTER TABLE pessoas DROP CONSTRAINT pessoas_tipo_pessoa_check;
    END IF;
END $$;

-- 5. Adicionar nova constraint com valores corretos
ALTER TABLE pessoas
ADD CONSTRAINT pessoas_tipo_pessoa_check
CHECK (tipo_pessoa IN ('FISICA', 'JURIDICA'));

-- 6. Atualizar dados existentes (se houver)
-- Ajustar registros antigos para o novo formato:
UPDATE pessoas SET tipo_pessoa = 'FISICA'
WHERE tipo_pessoa IN ('FRENTISTA', 'FUNCIONARIO', 'CLIENTE');

UPDATE pessoas SET tipo_pessoa = 'JURIDICA'
WHERE tipo_pessoa IN ('FORNECEDOR');

-- 7. Verificar resultado
SELECT id, nome_completo, cpf_cnpj, tipo_pessoa, data_nascimento
FROM pessoas
ORDER BY id DESC;

-- 8. Teste de inserção
-- Tente inserir uma pessoa para testar:
/*
INSERT INTO pessoas (nome_completo, cpf_cnpj, data_nascimento, tipo_pessoa, ativo)
VALUES ('Teste Silva', '12345678900', '1990-01-01', 'FISICA', true);
*/

