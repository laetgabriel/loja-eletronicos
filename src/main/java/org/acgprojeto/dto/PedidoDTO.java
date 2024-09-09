package org.acgprojeto.dto;

import org.acgprojeto.model.entities.Pedido;
import org.acgprojeto.model.enums.Estado;
import java.time.LocalDate;

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
        this.idPedido = pedido.getIdPedido();
        this.cliente = new ClienteDTO(pedido.getCliente());
        this.estado = Estado.valueOf(pedido.getEstado().getNomeEstado());// Se estado for uma instância de EstadoPedido
        this.data = pedido.getData();
    }

    public PedidoDTO() {

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

    public String getNomeCliente(){
        return cliente.getNome();
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
        return "PedidoDTO{" +
                "idPedido=" + idPedido +
                ", cliente=" + cliente +
                ", estado=" + estado +
                ", data=" + data +
                '}';
    }
}
