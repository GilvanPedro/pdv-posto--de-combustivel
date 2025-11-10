# 📋 ENUMS CRIADOS PARA O SISTEMA PDV

## ✅ TODOS OS ENUMS IMPLEMENTADOS

Seguindo o padrão do `TipoPessoa.java`, foram criados **8 novos enums** para complementar o sistema.

---

## 📂 ESTRUTURA

```
src/main/java/com/br/pdvpostocombustivel/enums/
├── TipoPessoa.java              ← JÁ EXISTIA
├── TipoRole.java                ← NOVO
├── CategoriaProduto.java        ← NOVO
├── TipoContato.java             ← NOVO
├── StatusBomba.java             ← NOVO
├── TipoMovimentacaoEstoque.java ← NOVO
├── FormaPagamento.java          ← NOVO
├── StatusVenda.java             ← NOVO
└── UnidadeMedida.java           ← NOVO
```

---

## 🔍 DETALHAMENTO DOS ENUMS

### **1. TipoPessoa** *(já existia)*
**Uso:** Entidade `Pessoa`  
**Valores:**
- `FISICA` - Pessoa Física
- `JURIDICA` - Pessoa Jurídica

---

### **2. TipoRole** ✨ NOVO
**Uso:** Entidade `Acesso` (roles de usuários)  
**Valores:**
- `ADMIN` - Administrador
- `FRENTISTA` - Frentista

**Exemplo de uso:**
```java
@Enumerated(EnumType.STRING)
private TipoRole role;
```

---

### **3. CategoriaProduto** ✨ NOVO
**Uso:** Entidade `Produto` (categorias de produtos)  
**Valores:**
- `COMBUSTIVEL` - Combustível
- `LUBRIFICANTE` - Lubrificante
- `ADITIVO` - Aditivo
- `LIMPEZA` - Produto de Limpeza
- `ALIMENTACAO` - Alimentação
- `BEBIDA` - Bebida
- `CONVENIENCIA` - Conveniência
- `AUTOMOTIVO` - Automotivo
- `OUTROS` - Outros

**Exemplo de uso:**
```java
@Enumerated(EnumType.STRING)
private CategoriaProduto categoria;
```

---

### **4. TipoContato** ✨ NOVO
**Uso:** Entidade `Contato` (tipos de telefone)  
**Valores:**
- `CELULAR` - Celular
- `RESIDENCIAL` - Telefone Residencial
- `COMERCIAL` - Telefone Comercial
- `WHATSAPP` - WhatsApp
- `RECADO` - Telefone para Recado

**Exemplo de uso:**
```java
@Enumerated(EnumType.STRING)
private TipoContato tipoTelefone;
```

---

### **5. StatusBomba** ✨ NOVO
**Uso:** Entidade `Bomba` (status das bombas)  
**Valores:**
- `DISPONIVEL` - Disponível
- `OCUPADA` - Ocupada
- `MANUTENCAO` - Em Manutenção
- `FORA_DE_SERVICO` - Fora de Serviço

**Exemplo de uso:**
```java
@Enumerated(EnumType.STRING)
private StatusBomba status;
```

---

### **6. TipoMovimentacaoEstoque** ✨ NOVO
**Uso:** Entidade `Estoque` (tipos de movimentação)  
**Valores:**
- `ENTRADA` - Entrada
- `SAIDA` - Saída
- `AJUSTE` - Ajuste de Estoque
- `DEVOLUCAO` - Devolução
- `PERDA` - Perda/Avaria

**Exemplo de uso:**
```java
@Enumerated(EnumType.STRING)
private TipoMovimentacaoEstoque tipoMovimentacao;
```

---

### **7. FormaPagamento** ✨ NOVO
**Uso:** Entidade `Venda` (formas de pagamento)  
**Valores:**
- `DINHEIRO` - Dinheiro
- `CARTAO_CREDITO` - Cartão de Crédito
- `CARTAO_DEBITO` - Cartão de Débito
- `PIX` - PIX
- `CHEQUE` - Cheque
- `VALE_COMBUSTIVEL` - Vale Combustível
- `CORTESIA` - Cortesia

**Exemplo de uso:**
```java
@Enumerated(EnumType.STRING)
private FormaPagamento formaPagamento;
```

---

### **8. StatusVenda** ✨ NOVO
**Uso:** Entidade `Venda` (status da venda)  
**Valores:**
- `PENDENTE` - Pendente
- `CONFIRMADA` - Confirmada
- `CANCELADA` - Cancelada
- `FINALIZADA` - Finalizada

**Exemplo de uso:**
```java
@Enumerated(EnumType.STRING)
private StatusVenda status;
```

---

### **9. UnidadeMedida** ✨ NOVO
**Uso:** Entidade `Produto` (unidades de medida)  
**Valores:**
- `LITRO` - Litro
- `UNIDADE` - Unidade
- `KILOGRAMA` - Kilograma
- `GRAMA` - Grama
- `METRO` - Metro
- `CENTIMETRO` - Centímetro
- `CAIXA` - Caixa
- `PACOTE` - Pacote

**Exemplo de uso:**
```java
@Enumerated(EnumType.STRING)
private UnidadeMedida unidadeMedida;
```

---

## 🎯 PADRÃO UTILIZADO

Todos os enums seguem o mesmo padrão do `TipoPessoa`:

```java
package com.br.pdvpostocombustivel.enums;

public enum NomeDoEnum {

    VALOR1("Descrição 1"),
    VALOR2("Descrição 2"),
    VALOR3("Descrição 3");

    private final String descricao;

    private NomeDoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
```

---

## 💡 COMO USAR NAS ENTIDADES

### **Exemplo 1: Adicionar enum em Produto**

```java
import com.br.pdvpostocombustivel.enums.CategoriaProduto;
import com.br.pdvpostocombustivel.enums.UnidadeMedida;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
public class Produto {
    // ...campos existentes...
    
    @Enumerated(EnumType.STRING)
    private CategoriaProduto categoria;
    
    @Enumerated(EnumType.STRING)
    private UnidadeMedida unidadeMedida;
    
    // ...getters e setters...
}
```

### **Exemplo 2: Adicionar enum em Acesso**

```java
import com.br.pdvpostocombustivel.enums.TipoRole;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
public class Acesso {
    // ...campos existentes...
    
    @Enumerated(EnumType.STRING)
    private TipoRole role = TipoRole.FRENTISTA;
    
    // ...getters e setters...
}
```

### **Exemplo 3: Adicionar enum em Contato**

```java
import com.br.pdvpostocombustivel.enums.TipoContato;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
public class Contato {
    // ...campos existentes...
    
    @Enumerated(EnumType.STRING)
    private TipoContato tipoTelefone = TipoContato.CELULAR;
    
    // ...getters e setters...
}
```

---

## ✅ VANTAGENS DOS ENUMS

### **1. Segurança de Tipo**
```java
// ❌ Sem enum (propenso a erros)
produto.setCategoria("combustivl"); // Typo!

// ✅ Com enum (erro em tempo de compilação)
produto.setCategoria(CategoriaProduto.COMBUSTIVEL);
```

### **2. Validação Automática**
- O banco de dados só aceita valores válidos
- Evita dados inconsistentes
- Facilita consultas

### **3. Autocompletar IDE**
- IDE sugere valores possíveis
- Reduz erros de digitação
- Melhora produtividade

### **4. Documentação Integrada**
- Descrições amigáveis via `getDescricao()`
- Código autodocumentado
- Facilita manutenção

---

## 📊 RESUMO

| Enum | Entidade | Quantidade de Valores | Uso Principal |
|------|----------|----------------------|---------------|
| **TipoPessoa** | Pessoa | 2 | Física ou Jurídica |
| **TipoRole** | Acesso | 2 | Admin ou Frentista |
| **CategoriaProduto** | Produto | 9 | Categoria do produto |
| **TipoContato** | Contato | 5 | Tipo de telefone |
| **StatusBomba** | Bomba | 4 | Status da bomba |
| **TipoMovimentacaoEstoque** | Estoque | 5 | Entrada/Saída |
| **FormaPagamento** | Venda | 7 | Forma de pagamento |
| **StatusVenda** | Venda | 4 | Status da venda |
| **UnidadeMedida** | Produto | 8 | Unidade de medida |

**Total:** 9 enums | 46 valores possíveis

---

## 🔧 OBSERVAÇÕES

### **Não altera o projeto atual:**
- ✅ Enums criados como **extensões opcionais**
- ✅ Podem ser usados quando necessário
- ✅ Não quebra código existente
- ✅ Backward compatible

### **Para usar:**
1. Importe o enum na entidade
2. Adicione a anotação `@Enumerated(EnumType.STRING)`
3. Use o enum no lugar de String

### **Tipo de armazenamento:**
```java
@Enumerated(EnumType.STRING)  // Recomendado! Salva como texto
// vs
@Enumerated(EnumType.ORDINAL) // Não recomendado! Salva como número
```

**Motivo:** `STRING` é mais legível no banco e resistente a mudanças na ordem dos enums.

---

## 📂 LOCALIZAÇÃO DOS ARQUIVOS

Todos os enums estão em:
```
pdv-posto-combustivel/src/main/java/com/br/pdvpostocombustivel/enums/
```

---

## ✅ CONCLUSÃO

✅ **8 novos enums criados** seguindo o padrão `TipoPessoa`  
✅ **Código limpo e profissional**  
✅ **Pronto para uso quando necessário**  
✅ **Não altera funcionalidade existente**  
✅ **Facilita futuras expansões**

**Sistema está ainda mais robusto e preparado para crescimento! 🚀**

