package org.acgprojeto.model.entidades;

import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.model.enums.Estado;

import java.util.Date;

public class Pedido {

    private Integer idPedido;
    private Cliente cliente;
    private Estado estado;
    private Date data;
    private Servico servico;

    public Pedido() {
    }

    public Pedido(PedidoDTO pedidoDTO) {
        this.idPedido = pedidoDTO.getIdPedido();
        this.cliente = new Cliente(pedidoDTO.getCliente());
        this.estado = pedidoDTO.getEstado();
        this.data = pedidoDTO.getData();
        this.servico = new Servico();
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", cliente=" + cliente +
                ", estado=" + estado +
                ", data=" + data +
                ", servico=" + servico +
                '}';
    }
}
