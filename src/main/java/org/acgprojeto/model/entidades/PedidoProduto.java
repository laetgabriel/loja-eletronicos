package org.acgprojeto.model.entidades;

import org.acgprojeto.dto.PedidoProdutoDTO;

import java.math.BigDecimal;

public class PedidoProduto {

    private Pedido pedido;
    private Produto produto;
    private BigDecimal preco;
    private Integer quantidade;

    public PedidoProduto() {}

    public PedidoProduto(PedidoProdutoDTO pedidoProduto) {
        this.pedido = new Pedido(pedidoProduto.getPedido());
        this.produto = new Produto(pedidoProduto.getProduto());
        this.preco = pedidoProduto.getPreco();
        this.quantidade = pedidoProduto.getQuantidade();
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "PedidoProduto{" +
                "pedido=" + pedido +
                ", produto=" + produto +
                ", preco=" + preco +
                ", quantidade=" + quantidade +
                '}';
    }
}
