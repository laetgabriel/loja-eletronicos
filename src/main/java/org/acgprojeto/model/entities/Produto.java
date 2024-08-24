package org.acgprojeto.model.entities;

import org.acgprojeto.dto.ProdutoDTO;
import org.acgprojeto.model.enums.Categoria;

import java.math.BigDecimal;
import java.util.Objects;

public class Produto {

    private Integer idProduto;
    private String nomeProduto;
    private Categoria categoria;
    private BigDecimal preco;
    private Integer quantidadeEstoque;

    private Produto() {}

    public Produto(ProdutoDTO produtoDTO) {
        idProduto = produtoDTO.getIdProduto();
        nomeProduto = produtoDTO.getNomeProduto();
        categoria = produtoDTO.getCategoria();
        preco = produtoDTO.getPreco();
        quantidadeEstoque = produtoDTO.getQuantidadeEstoque();
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "idProduto=" + idProduto +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", categoria=" + categoria +
                ", preco=" + preco +
                ", quantidadeEstoque=" + quantidadeEstoque +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(idProduto, produto.idProduto) &&
                Objects.equals(nomeProduto, produto.nomeProduto) &&
                categoria == produto.categoria &&
                Objects.equals(preco, produto.preco) &&
                Objects.equals(quantidadeEstoque, produto.quantidadeEstoque);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduto, nomeProduto, categoria, preco, quantidadeEstoque);
    }

    public static final class ProdutoBuilder {
        private Integer idProduto;
        private String nomeProduto;
        private Categoria categoria;
        private BigDecimal preco;
        private Integer quantidadeEstoque;

        private ProdutoBuilder() {
        }

        public static ProdutoBuilder aProduto() {
            return new ProdutoBuilder();
        }

        public ProdutoBuilder idProduto(Integer idProduto) {
            this.idProduto = idProduto;
            return this;
        }

        public ProdutoBuilder nomeProduto(String nomeProduto) {
            this.nomeProduto = nomeProduto;
            return this;
        }

        public ProdutoBuilder categoria(Categoria categoria) {
            this.categoria = categoria;
            return this;
        }

        public ProdutoBuilder preco(BigDecimal preco) {
            this.preco = preco;
            return this;
        }

        public ProdutoBuilder quantidadeEstoque(Integer quantidadeEstoque) {
            this.quantidadeEstoque = quantidadeEstoque;
            return this;
        }

        public Produto build() {
            if (nomeProduto == null || categoria == null || preco == null || quantidadeEstoque == null) {
                throw new IllegalStateException("Todos os campos devem ser fornecidos para construir um Produto");
            }
            Produto produto = new Produto();
            produto.setIdProduto(this.idProduto);
            produto.setNomeProduto(this.nomeProduto);
            produto.setCategoria(this.categoria);
            produto.setPreco(this.preco);
            produto.setQuantidadeEstoque(this.quantidadeEstoque);
            return produto;
        }
    }
}