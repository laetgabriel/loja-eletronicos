package org.acgprojeto.model.state.impl;

public class EstadoFinalizado extends AbstractEstadoPedido {

    public EstadoFinalizado() {
    }

    @Override
    public String getNomeEstado() {
        return "FINALIZADO";
    }

    @Override
    public void gerarRelatorio() {
        super.gerarRelatorio();
    }
}
