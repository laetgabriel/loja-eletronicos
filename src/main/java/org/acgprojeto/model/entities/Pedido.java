package org.acgprojeto.model.entities;

import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.model.state.EstadoPedido;
import org.acgprojeto.model.state.impl.EstadoAndamento;
import org.acgprojeto.model.state.impl.EstadoCancelado;
import org.acgprojeto.model.state.impl.EstadoFinalizado;
import org.acgprojeto.model.state.impl.EstadoPronto;
import org.acgprojeto.model.enums.Estado;

import java.time.LocalDate;

public class Pedido {

    private Integer idPedido;
    private Cliente cliente;
    private EstadoPedido estado ;
    private LocalDate data;

    public Pedido(Integer idPedido, Cliente cliente, LocalDate data) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.data = data;
        this.estado = new EstadoAndamento();
        estado.setPedidoDTO(this.toDTO());
    }

    public Pedido(PedidoDTO pedidoDTO) {
        this.estado = createEstado(pedidoDTO.getEstado());
        this.idPedido = pedidoDTO.getIdPedido();
        this.data = pedidoDTO.getData();
        this.cliente = new Cliente(pedidoDTO.getCliente());
        estado.setPedidoDTO(this.toDTO());
    }

    public Pedido() {

    }

    private EstadoPedido createEstado(Estado estadoEnum) {
        switch (estadoEnum) {
            case ANDAMENTO:
                return new EstadoAndamento();
            case PRONTO:
                return new EstadoPronto();
            case CANCELADO:
                return new EstadoCancelado();
            case FINALIZADO:
                return new EstadoFinalizado();
            default:
                throw new IllegalArgumentException("Estado inválido");
        }
    }

    public void concluir() {
        estado.concluir();
    }

    public void finalizar() {
        estado.finalizar();
    }

    public void cancelar() {
        estado.cancelar();
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public PedidoDTO toDTO() {
        return new PedidoDTO(this);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", cliente=" + cliente +
                ", data=" + data +
                '}';
    }
}
