package org.acgprojeto.model.state;

import org.acgprojeto.dto.PedidoDTO;

public interface EstadoPedido {
    boolean finalizar();
    boolean cancelar();
    boolean concluir();
    String getNomeEstado();
    void setPedidoDTO(PedidoDTO pedidoDTO);
}
