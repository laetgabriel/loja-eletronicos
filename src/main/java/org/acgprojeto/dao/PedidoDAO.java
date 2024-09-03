package org.acgprojeto.dao;

import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.dto.TabelaPedidoDTO;

import java.util.List;

public interface PedidoDAO {

    void inserirPedido(PedidoDTO pedido);
    void atualizarPedido(PedidoDTO pedido);
    void atualizarEstadoPedido(PedidoDTO pedido);
    void excluirPedido(Integer id);
    PedidoDTO buscarPedidoPorId(Integer id);
    List<TabelaPedidoDTO> buscarTabelaPedidoPorId(Integer id);
    List<PedidoDTO> buscarPedidos();
    List<TabelaPedidoDTO> buscarPedidosParaTabelaPedidos();
    List<TabelaPedidoDTO> buscarPedidosParaTabelaRelPedidos();

}