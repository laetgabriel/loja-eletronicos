package org.acgprojeto.controller;

import org.acgprojeto.dao.DAOFactory;
import org.acgprojeto.dao.PedidoProdutoDAO;
import org.acgprojeto.dto.PedidoProdutoDTO;

import java.util.List;

public class PedidoProdutoController {

    PedidoProdutoDAO pedidoProdutoDAO = DAOFactory.criarPedidoProdutoDAO();

    public void inserirPedidoProduto(PedidoProdutoDTO pedidoProdutoDTO) {
        pedidoProdutoDAO.inserirPedidoProduto(pedidoProdutoDTO);
    }

    public void atualizarPedidoProduto(PedidoProdutoDTO pedidoProdutoDTO) {
        pedidoProdutoDAO.atualizarPedidoProduto(pedidoProdutoDTO);
    }

    public void excluirPedidoProduto(Integer idProduto, Integer idPedido) {
        pedidoProdutoDAO.excluirPedidoProduto(idProduto, idPedido);
    }

    public List<PedidoProdutoDTO> listarPedidoProduto() {
        return pedidoProdutoDAO.listarPedidoProduto();
    }

    public PedidoProdutoDTO pesquisarPedidoProduto(Integer idPedido, Integer idProduto) {
        return pedidoProdutoDAO.buscarPedidoProduto(idPedido, idProduto);
    }
}
