
package org.acgprojeto.dto;

import org.acgprojeto.model.entidades.Pedido;
import org.acgprojeto.model.enums.Estado;

import java.time.LocalDate;
import java.util.Date;


public class PedidoDTO {

    private Integer idPedido;
    private ClienteDTO cliente;
    private Estado estado;
    private LocalDate data;

    public PedidoDTO(Integer idPedido, ClienteDTO cliente, Estado estado, LocalDate data) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.estado = estado;
        this.data = data;
    }

    public PedidoDTO(Pedido pedido) {
        idPedido = pedido.getIdPedido();
        cliente = new ClienteDTO(pedido.getCliente());
        estado = pedido.getEstado();
        data = pedido.getData();
    }

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
