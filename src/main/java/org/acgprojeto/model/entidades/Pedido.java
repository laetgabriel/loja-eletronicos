package org.acgprojeto.model.entidades;

import org.acgprojeto.model.enums.Estado;

import java.util.Date;
import java.util.Objects;

public class Pedido {

    private Pedido pedido;
    private Cliente cliente;
    private Estado estado;
    private Date data;

    public Pedido(){}

    public Pedido(Pedido pedido, Cliente cliente, Estado estado, Date data) {
        this.pedido = pedido;
        this.cliente = cliente;
        this.estado = estado;
        this.data = data;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
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

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + pedido +
                ", cliente=" + cliente +
                ", estado=" + estado +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido1 = (Pedido) o;
        return Objects.equals(pedido, pedido1.pedido) && Objects.equals(cliente, pedido1.cliente) && estado == pedido1.estado && Objects.equals(data, pedido1.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pedido, cliente, estado, data);
    }
}
