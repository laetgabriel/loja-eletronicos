
package org.acgprojeto.dto;

import org.acgprojeto.model.entities.Produto;
import org.acgprojeto.model.enums.Categoria;

import java.math.BigDecimal;

public class ProdutoDTO {

    private Integer idProduto;
    private String nomeProduto;
    private Categoria categoria;
    private BigDecimal preco;
    private Integer quantidadeEstoque;

    private ProdutoDTO() {}

    public ProdutoDTO(Integer idProduto, String nomeProduto, Categoria categoria, BigDecimal preco, Integer quantidadeEstoque) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public ProdutoDTO(Integer idProduto, Integer quantidadeEstoque, String nomeProduto, Categoria categoria, BigDecimal preco) {
        this.idProduto = idProduto;
        this.quantidadeEstoque = quantidadeEstoque;
        this.nomeProduto = nomeProduto;
        this.categoria = categoria;
        this.preco = preco;
    }

    public ProdutoDTO(Produto produto){
        this.idProduto = produto.getIdProduto();
        this.quantidadeEstoque = produto.getQuantidadeEstoque();
        this.nomeProduto = produto.getNomeProduto();
        this.categoria = produto.getCategoria();
        this.preco = produto.getPreco();
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
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


    public static final class ProdutoDTOBuilder {
        private Integer idProduto;
        private String nomeProduto;
        private Categoria categoria;
        private BigDecimal preco;
        private Integer quantidadeEstoque;

        private ProdutoDTOBuilder() {
        }

        public static ProdutoDTOBuilder aProdutoDTO() {
            return new ProdutoDTOBuilder();
        }

        public ProdutoDTOBuilder idProduto(Integer idProduto) {
            this.idProduto = idProduto;
            return this;
        }

        public ProdutoDTOBuilder nomeProduto(String nomeProduto) {
            this.nomeProduto = nomeProduto;
            return this;
        }

        public ProdutoDTOBuilder categoria(Categoria categoria) {
            this.categoria = categoria;
            return this;
        }

        public ProdutoDTOBuilder preco(BigDecimal preco) {
            this.preco = preco;
            return this;
        }

        public ProdutoDTOBuilder quantidadeEstoque(Integer quantidadeEstoque) {
            this.quantidadeEstoque = quantidadeEstoque;
            return this;
        }

        public ProdutoDTO build() {
            return new ProdutoDTO(idProduto, nomeProduto, categoria, preco, quantidadeEstoque);
        }
    }
}
