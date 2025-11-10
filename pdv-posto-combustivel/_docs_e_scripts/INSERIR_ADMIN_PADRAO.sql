-- Script para criar usuário ADMIN padrão
-- Execute este script após executar MIGRACAO_ACESSOS_PESSOA.sql

-- 1. Inserir usuário admin (se não existir)
INSERT INTO acessos (usuario, senha, role, nome_completo)
VALUES ('admin', 'admin123', 'ADMIN', 'Administrador do Sistema')
ON CONFLICT (usuario) DO NOTHING;

-- 2. Verificar se foi inserido
SELECT * FROM acessos WHERE usuario = 'admin';

-- Nota: Este é um usuário padrão para testes
-- RECOMENDA-SE ALTERAR A SENHA EM PRODUÇÃO!

