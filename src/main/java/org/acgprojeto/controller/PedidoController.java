package org.acgprojeto.controller;

import org.acgprojeto.dao.PedidoDAO;
import org.acgprojeto.dao.impl.PedidoDAOImpl;
import org.acgprojeto.db.DB;
import org.acgprojeto.dto.PedidoDTO;

import java.util.List;

public class PedidoController {

    PedidoDAO pedidoDAO = new PedidoDAOImpl(DB.getConexao());

    public void inserirPedido(PedidoDTO pedidoDTO) {
        pedidoDAO.inserirPedido(pedidoDTO);
    }

    public void atualizarPedido(PedidoDTO pedidoDTO) {
        pedidoDAO.atualizarPedido(pedidoDTO);
    }

    public void atualizarEstadoPedido(PedidoDTO pedidoDTO) {
        pedidoDAO.atualizarEstadoPedido(pedidoDTO);
    }

    public void excluirPedido(Integer id) {
        pedidoDAO.excluirPedido(id);
    }

    public List<PedidoDTO> buscarPedidos() {
        return pedidoDAO.buscarPedidos();
    }

    public PedidoDTO buscarPedidoPorId(Integer id) {
        return pedidoDAO.buscarPedidoPorId(id);
    }
}
