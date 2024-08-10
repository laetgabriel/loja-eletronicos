package org.acgprojeto.dao;

import org.acgprojeto.dto.PedidoProdutoDTO;

import java.util.List;

public interface PedidoProdutoDAO {

    void inserirPedidoProduto(PedidoProdutoDTO pedidoProduto);
    void atualizarPedidoProduto(PedidoProdutoDTO pedidoProduto);
    void excluirPedidoProduto(PedidoProdutoDTO pedidoProduto);
    List<PedidoProdutoDTO> listarPedidoProduto();
}
