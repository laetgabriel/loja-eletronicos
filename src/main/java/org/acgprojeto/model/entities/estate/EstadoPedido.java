package org.acgprojeto.model.entities.estate;

import org.acgprojeto.dto.PedidoDTO;

public interface EstadoPedido {
    void finalizar();
    void cancelar();
    void concluir();
    String getNomeEstado();
    void gerarRelatorio();
    void setPedidoDTO(PedidoDTO pedidoDTO);
}
