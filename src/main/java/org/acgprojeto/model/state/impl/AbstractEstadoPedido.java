package org.acgprojeto.model.state.impl;

import org.acgprojeto.controller.PedidoController;
import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.model.state.EstadoPedido;

public abstract class AbstractEstadoPedido implements EstadoPedido {

    protected PedidoDTO pedidoDTO = null;
    protected final PedidoController pedidoController = new PedidoController();

    public AbstractEstadoPedido() {
    }

    @Override
    public boolean finalizar(){
        return false;
    }

    @Override
    public boolean cancelar() {
        return false;
    }

    @Override
    public boolean concluir() {
        return false;
    }

    public abstract String getNomeEstado();

    public void setPedidoDTO(PedidoDTO pedidoDTO){
        this.pedidoDTO = pedidoDTO;
    }
}

