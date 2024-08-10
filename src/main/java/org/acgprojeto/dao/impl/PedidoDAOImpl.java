package org.acgprojeto.dao.impl;

import org.acgprojeto.dao.PedidoDAO;
import org.acgprojeto.dto.PedidoDTO;

import java.sql.Connection;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {
    private Connection conexao;
    public PedidoDAOImpl(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void inserirPedido(PedidoDTO pedido) {

    }

    @Override
    public void atualizarPedido(PedidoDTO pedido) {

    }

    @Override
    public void excluirPedido(Integer id) {

    }

    @Override
    public PedidoDTO buscarPedidoPorId(Integer id) {
        return null;
    }

    @Override
    public List<PedidoDTO> buscarPedidos() {
        return List.of();
    }
}
