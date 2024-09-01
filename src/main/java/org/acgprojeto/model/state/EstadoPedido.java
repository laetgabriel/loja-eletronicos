package org.acgprojeto.model.state;

import org.acgprojeto.dto.PedidoDTO;

public interface EstadoPedido {
    void finalizar();
    void cancelar();
    void concluir();
    String getNomeEstado();
    void setPedidoDTO(PedidoDTO pedidoDTO);
}
