package org.acgprojeto.dao;

import org.acgprojeto.dto.PedidoDTO;

import java.util.List;

public interface PedidoDAO {

    void inserirPedido(PedidoDTO pedido);
    void atualizarPedido(PedidoDTO pedido);
    void atualizarEstadoPedido(PedidoDTO pedido);
    void excluirPedido(Integer id);
    PedidoDTO buscarPedidoPorId(Integer id);
    List<PedidoDTO> buscarPedidos();
}