package org.acgprojeto.model.state.impl;

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
