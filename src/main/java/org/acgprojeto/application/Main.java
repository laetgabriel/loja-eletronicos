package org.acgprojeto.application;

import org.acgprojeto.dao.ClienteDAO;
import org.acgprojeto.dao.ProdutoDAO;
import org.acgprojeto.dao.impl.ClienteDAOImpl;
import org.acgprojeto.dao.impl.ProdutoDAOImpl;
import org.acgprojeto.db.DB;
import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.dto.ProdutoDTO;
import org.acgprojeto.model.enums.Categoria;

import java.math.BigDecimal;
import java.sql.Connection;

public class Main  {

    public static void main(String[] args) {
        Connection connection = DB.getConexao();

        ClienteDAO clienteDAO = new ClienteDAOImpl(connection);
        ProdutoDAO produtoDAO = new ProdutoDAOImpl(connection);
        //ClienteDTO clienteDTO = new ClienteDTO(null, "opa", "gaag", "321321");
        ProdutoDTO produtoDTO = ProdutoDTO.ProdutoDTOBuilder.aProdutoDTO()
                .nomeProduto("algo")
                .categoria(Categoria.PECA)
                .quantidadeEstoque(3)
                .preco(new BigDecimal(199.99))
                .build();

        //clienteDAO.inserirCliente(clienteDTO);

        //produtoDAO.inserirProduto(produtoDTO);

        produtoDAO.excluirProduto(4);
        DB.fecharConexao();
    }




}