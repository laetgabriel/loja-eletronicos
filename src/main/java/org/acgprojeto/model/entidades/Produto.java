package org.acgprojeto.model.entidades;

import org.acgprojeto.model.enums.Categoria;

import java.math.BigDecimal;
import java.util.Objects;

public class Produto {

    private int idProduto;
    private String nomeProduto;
    private Categoria categoria;
    private BigDecimal preco;
    private Integer quantidadeEstoque;


    public int getIdProduto() {
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
        return idProduto == produto.idProduto && Objects.equals(nomeProduto, produto.nomeProduto) && categoria == produto.categoria && Objects.equals(preco, produto.preco) && Objects.equals(quantidadeEstoque, produto.quantidadeEstoque);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduto, nomeProduto, categoria, preco, quantidadeEstoque);
    }
}
