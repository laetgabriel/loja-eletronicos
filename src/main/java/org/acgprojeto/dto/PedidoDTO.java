package org.acgprojeto.dto;

import org.acgprojeto.model.enums.Estado;

import java.util.Date;


public class PedidoDTO {

    private Integer idPedido;
    private ClienteDTO cliente;
    private Estado estado;
    private Date data;

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
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

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", cliente=" + cliente +
                ", estado=" + estado +
                ", data=" + data +
                '}';
    }

}
