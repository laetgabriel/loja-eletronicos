
package org.acgprojeto.dto;

import org.acgprojeto.model.entidades.Produto;
import org.acgprojeto.model.enums.Categoria;

import java.math.BigDecimal;

public class ProdutoDTO {

    private Integer idProduto;
    private String nomeProduto;
    private Categoria categoria;
    private BigDecimal preco;
    private Integer quantidadeEstoque;

    public ProdutoDTO() {}

    public ProdutoDTO(Integer IdProduto, Integer quantidadeEstoque, String nomeProduto, Categoria categoria, BigDecimal preco) {
        this.idProduto = IdProduto;
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

}
