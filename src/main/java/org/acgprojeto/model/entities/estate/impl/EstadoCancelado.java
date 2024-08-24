package org.acgprojeto.model.entities.estate.impl;

public class EstadoCancelado extends AbstractEstadoPedido {

    public EstadoCancelado() {
    }

    @Override
    public String getNomeEstado() {
        return "CANCELADO";
    }

//    @Override
//    public void gerarRelatorio() {
//        super.gerarRelatorio();
//    }
}
