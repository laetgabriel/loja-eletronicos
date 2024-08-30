package org.acgprojeto.controller;

import org.acgprojeto.dao.ProdutoDAO;
import org.acgprojeto.dao.impl.ProdutoDAOImpl;
import org.acgprojeto.db.DB;
import org.acgprojeto.dto.ProdutoDTO;
import org.acgprojeto.model.enums.Categoria;

import java.util.List;

public class ProdutoController {

    ProdutoDAO produtoDAO = new ProdutoDAOImpl(DB.getConexao());

    public void inserirProduto(ProdutoDTO produtoDTO) {
        if (isProdutoCadastrado(produtoDTO.getNomeProduto(), produtoDTO.getCategoria())) {
            System.out.println("Produto com o mesmo nome e tipo já cadastrado.");
        } else {
            produtoDAO.inserirProduto(produtoDTO);
        }
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

    public List<String> listarTodasAsCategorias() {return produtoDAO.listarTodasAsCategorias();
    }

    public ProdutoDTO buscarProdutoPorId(Integer id) {
        return produtoDAO.buscarProdutoPorId(id);
    }

    private boolean isProdutoCadastrado(String nome, Categoria tipo) {
        List<ProdutoDTO> produtos = listarTodosOsProdutos();
        for (ProdutoDTO produto : produtos) {
            if (produto.getNomeProduto().equalsIgnoreCase(nome) && produto.getCategoria().equals(tipo)) {
                return true;
            }
        }
        return false;
    }
}
