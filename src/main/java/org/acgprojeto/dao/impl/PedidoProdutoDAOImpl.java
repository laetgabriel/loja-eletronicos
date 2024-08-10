package org.acgprojeto.dao.impl;

import org.acgprojeto.dao.PedidoProdutoDAO;
import org.acgprojeto.dto.PedidoProdutoDTO;

import java.sql.Connection;
import java.util.List;

public class PedidoProdutoDAOImpl implements PedidoProdutoDAO {

    private Connection conexao;
    public PedidoProdutoDAOImpl(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void inserirPedidoProduto(PedidoProdutoDTO pedidoProduto) {

    }

    @Override
    public void atualizarPedidoProduto(PedidoProdutoDTO pedidoProduto) {

    }

    @Override
    public void excluirPedidoProduto(Integer id) {

    }

    @Override
    public List<PedidoProdutoDTO> listarPedidoProduto() {
        return List.of();
    }
}
