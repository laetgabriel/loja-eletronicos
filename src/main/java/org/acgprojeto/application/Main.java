package org.acgprojeto.application;


import org.acgprojeto.dao.ClienteDAO;
import org.acgprojeto.dao.DAOFactory;
import org.acgprojeto.dao.ProdutoDAO;
import org.acgprojeto.dao.impl.ProdutoDAOImpl;
import org.acgprojeto.db.DB;
import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.dto.ProdutoDTO;
import org.acgprojeto.model.entidades.Cliente;
import org.acgprojeto.model.entidades.Produto;
import org.acgprojeto.model.enums.Categoria;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        Connection conexao = DB.getConexao();
        ProdutoDAO produtoDAO = new ProdutoDAOImpl(conexao);
        Categoria categoria = Categoria.CELULAR;
        BigDecimal preco = new BigDecimal(80);
        ProdutoDTO produtoDTO = new ProdutoDTO(null, 9, "cocaina", categoria, preco );
        List<Produto> produtos = produtoDAO.listarTodosOsProdutos();
        for(Produto produto : produtos){
            System.out.println(produto);
        }

        DB.fecharConexao();

    }
}