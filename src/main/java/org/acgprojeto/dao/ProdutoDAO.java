package org.acgprojeto.dao;

import org.acgprojeto.dto.ProdutoDTO;

import java.util.List;

public interface ProdutoDAO {

    void inserirProduto(ProdutoDTO produto);
    void alterarProduto(ProdutoDTO produto);
    void excluirProduto(ProdutoDTO produto);
    ProdutoDTO listarProdutoPorId(Integer produto);
    List<ProdutoDTO> listarTodosOsProdutos();
}
