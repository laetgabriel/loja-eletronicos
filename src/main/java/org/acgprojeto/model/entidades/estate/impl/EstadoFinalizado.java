package org.acgprojeto.model.entidades.estate.impl;

import org.acgprojeto.dto.PedidoDTO;

public class EstadoFinalizado extends AbstractEstadoPedido {

    public EstadoFinalizado() {
    }

    @Override
    public String getNomeEstado() {
        return "FINALIZADO";
    }

//    @Override
//    public void gerarRelatorio() {
//        super.gerarRelatorio();
//    }
}
