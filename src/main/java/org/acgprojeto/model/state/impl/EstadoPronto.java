package org.acgprojeto.model.state.impl;

import org.acgprojeto.model.enums.Estado;

public class EstadoPronto extends AbstractEstadoPedido {

    public EstadoPronto() {
    }

    @Override
    public boolean finalizar() {
        pedidoDTO.setEstado(Estado.FINALIZADO);
        pedidoController.atualizarEstadoPedido(pedidoDTO);
        return true;
    }

    @Override
    public String getNomeEstado() {
        return "PRONTO";
    }
}
