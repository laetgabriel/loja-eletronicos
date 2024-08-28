package org.acgprojeto.dao;

import org.acgprojeto.dto.ProdutoDTO;
import org.acgprojeto.model.enums.Categoria;

import java.util.List;

public interface ProdutoDAO {

    void inserirProduto(ProdutoDTO produto);
    void atualizarProduto(ProdutoDTO produto);
    void excluirProduto(Integer id);
    ProdutoDTO buscarProdutoPorId(Integer produto);
    List<ProdutoDTO> listarTodosOsProdutos();
    List<String> listarTodasAsCategorias();
}
