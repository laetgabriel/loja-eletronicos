package org.acgprojeto.model.entidades;

import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.model.enums.Estado;

import java.time.LocalDate;
import java.util.Date;

public class Pedido {

    private Integer idPedido;
    private Cliente cliente;
    private Estado estado;
    private LocalDate data;

    public Pedido() {}

    public Pedido(PedidoDTO pedidoDTO) {
        this.idPedido = pedidoDTO.getIdPedido();
        this.cliente = new Cliente(pedidoDTO.getCliente());
        this.estado = pedidoDTO.getEstado();
        this.data = pedidoDTO.getData();
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {this.idPedido = idPedido;}

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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
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
