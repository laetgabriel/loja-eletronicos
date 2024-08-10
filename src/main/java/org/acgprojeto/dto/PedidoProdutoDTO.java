package org.acgprojeto.dto;

import org.acgprojeto.model.entidades.Pedido;
import org.acgprojeto.model.entidades.Produto;

import java.math.BigDecimal;

public class PedidoProdutoDTO {

    private PedidoDTO pedido;
    private ProdutoDTO produto;
    private BigDecimal preco;
    private Integer quantidade;

    public PedidoDTO getPedido() {
        return pedido;
    }

    public void setPedido(PedidoDTO pedido) {
        this.pedido = pedido;
    }

    public ProdutoDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoDTO produto) {
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

}
