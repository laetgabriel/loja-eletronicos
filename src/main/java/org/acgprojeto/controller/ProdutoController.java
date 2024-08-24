package org.acgprojeto.controller;

import org.acgprojeto.dao.ProdutoDAO;
import org.acgprojeto.dao.impl.ProdutoDAOImpl;
import org.acgprojeto.db.DB;
import org.acgprojeto.dto.ProdutoDTO;

import java.util.List;

public class ProdutoController {

    ProdutoDAO produtoDAO = new ProdutoDAOImpl(DB.getConexao());

    public void inserirProduto(ProdutoDTO produtoDTO) {
        produtoDAO.inserirProduto(produtoDTO);
    }

    public void atualizarProduto(ProdutoDTO produtoDTO) {
        produtoDAO.atualizarProduto(produtoDTO);
    }

    public void excluirProduto(Integer id) {
        produtoDAO.excluirProduto(id);
    }

    public List<ProdutoDTO> listarTodosOsProdutos() {
        return produtoDAO.listarTodosOsProdutos();
    }

    public ProdutoDTO buscarProdutoPorId(Integer id) {
        return produtoDAO.buscarProdutoPorId(id);
    }
}
