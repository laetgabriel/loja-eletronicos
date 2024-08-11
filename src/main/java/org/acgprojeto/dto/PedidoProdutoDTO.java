
package org.acgprojeto.dto;

import org.acgprojeto.model.entidades.Pedido;
import org.acgprojeto.model.entidades.PedidoProduto;
import org.acgprojeto.model.entidades.Produto;

import java.math.BigDecimal;

public class PedidoProdutoDTO {

    private PedidoDTO pedido;
    private ProdutoDTO produto;
    private BigDecimal preco;
    private Integer quantidade;

    public PedidoProdutoDTO(PedidoDTO pedido, ProdutoDTO produto, BigDecimal preco, Integer quantidade) {
        this.pedido = pedido;
        this.produto = produto;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public PedidoProdutoDTO(PedidoProduto pedidoProduto) {
        this.pedido = new PedidoDTO(pedidoProduto.getPedido());
        this.produto = new ProdutoDTO(pedidoProduto.getProduto());
        this.preco = pedidoProduto.getPreco();
        this.quantidade = pedidoProduto.getQuantidade();
    }

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
