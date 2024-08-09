package org.acgprojeto.model.entidades;

import org.acgprojeto.model.enums.Tipo;

import java.math.BigDecimal;

public class Servico {

    private Integer idServico;
    private Integer idPedido;
    private String descricao;
    private BigDecimal preco;
    private Tipo tipo;

    public Servico() {}

    public Servico(Integer idServico, Integer idPedido, String descricao, BigDecimal preco, Tipo tipo) {
        this.idServico = idServico;
        this.idPedido = idPedido;
        this.descricao = descricao;
        this.preco = preco;
        this.tipo = tipo;
    }

    public Integer getIdServico() {
        return idServico;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setIdServico(Integer idServico) {
        this.idServico = idServico;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
