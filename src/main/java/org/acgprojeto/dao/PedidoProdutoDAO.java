
package org.acgprojeto.dao;

import org.acgprojeto.dto.PedidoProdutoDTO;

import java.util.List;

public interface PedidoProdutoDAO {

    void inserirPedidoProduto(PedidoProdutoDTO pedidoProduto);
    void atualizarPedidoProduto(PedidoProdutoDTO pedidoProduto);
    void excluirPedidoProduto(Integer id_pedido, Integer id_produto);
    PedidoProdutoDTO buscarPedidoProduto(Integer id_pedido, Integer id_produto);
    List<PedidoProdutoDTO> listarPedidoProduto();
}
