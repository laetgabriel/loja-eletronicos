package org.acgprojeto.model.entidades.estate.impl;

import org.acgprojeto.dto.PedidoDTO;

public class EstadoCancelado extends AbstractEstadoPedido {

    public EstadoCancelado() {
    }

    @Override
    public String getNomeEstado() {
        return "CANCELADO";
    }

    @Override
    public void gerarRelatorio() {
        super.gerarRelatorio();
    }
}
