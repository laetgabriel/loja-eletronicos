package org.acgprojeto.dto;

import org.acgprojeto.model.entidades.Pedido;
import org.acgprojeto.model.entidades.Servico;
import org.acgprojeto.model.enums.Tipo;

import java.math.BigDecimal;

public class ServicoDTO {

    private Integer idServico;
    private PedidoDTO pedido;
    private String descricao;
    private BigDecimal preco;
    private Tipo tipo;

    public ServicoDTO() {}

    public ServicoDTO(Integer idServico, PedidoDTO pedido, String descricao, BigDecimal preco, Tipo tipo) {
        this.idServico = idServico;
        this.pedido = pedido;
        this.descricao = descricao;
        this.preco = preco;
        this.tipo = tipo;
    }
    public ServicoDTO(Servico servico) {
        this.idServico = servico.getIdServico();
        this.pedido = new PedidoDTO(servico.getPedido());
        this.descricao = servico.getDescricao();
        this.preco = servico.getPreco();
        this.tipo = servico.getTipo();
    }

    public Integer getIdServico() {
        return idServico;
    }

    public PedidoDTO getPedido() {
        return pedido;
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

    public void setPedido(PedidoDTO pedido) {
        this.pedido = pedido;
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
    public String toString() {
        return "Serviço{" +
                "Id_Pedido=" + pedido.getIdPedido() +
                ", Descrição='" + descricao + '\'' +
                ", preco=" + preco +
                ", tipo=" + tipo.toString() +
                '}';
    }
}
