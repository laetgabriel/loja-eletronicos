package org.acgprojeto.model.entities.estate.impl;

import org.acgprojeto.controller.PedidoController;
import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.model.entities.estate.EstadoPedido;

public abstract class AbstractEstadoPedido implements EstadoPedido {

    protected PedidoDTO pedidoDTO = null;
    protected final PedidoController pedidoController = new PedidoController();

    public AbstractEstadoPedido() {
    }

    @Override
    public void finalizar(){
        throw new UnsupportedOperationException("Operação não permitida no estado atual.");
    }

    @Override
    public void cancelar() {
        throw new UnsupportedOperationException("Operação não permitida no estado atual.");
    }

    @Override
    public void concluir() {
        throw new UnsupportedOperationException("Operação não permitida no estado atual.");
    }

    public abstract String getNomeEstado();

    public void setPedidoDTO(PedidoDTO pedidoDTO){
        this.pedidoDTO = pedidoDTO;
    }
}

