package org.acgprojeto.model.state.impl;

import org.acgprojeto.model.enums.Estado;

public class EstadoPronto extends AbstractEstadoPedido {

    public EstadoPronto() {
    }

    @Override
    public void finalizar() {
        System.out.println("Pedido pronto finalizado.");
        pedidoDTO.setEstado(Estado.FINALIZADO);
        pedidoController.atualizarEstadoPedido(pedidoDTO);

    }

    @Override
    public void gerarRelatorio() {
        super.gerarRelatorio();
    }

    @Override
    public String getNomeEstado() {
        return "PRONTO";
    }
}
