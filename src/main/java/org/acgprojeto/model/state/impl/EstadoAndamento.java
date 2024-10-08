package org.acgprojeto.model.state.impl;

import org.acgprojeto.model.enums.Estado;


public class EstadoAndamento extends AbstractEstadoPedido {
    public EstadoAndamento() {

    }

    @Override
    public boolean finalizar() {
        pedidoDTO.setEstado(Estado.FINALIZADO);
        pedidoController.atualizarEstadoPedido(pedidoDTO);
        return true;
    }

    @Override
    public boolean cancelar() {
        pedidoDTO.setEstado(Estado.CANCELADO);
        pedidoController.atualizarEstadoPedido(pedidoDTO);
        return true;
    }

    @Override
    public boolean concluir() {
        pedidoDTO.setEstado(Estado.PRONTO);
        pedidoController.atualizarEstadoPedido(pedidoDTO);
        return  true;
    }

    @Override
    public String getNomeEstado() {
        return "ANDAMENTO";
    }
}
