package org.acgprojeto.application;
import org.acgprojeto.dao.ClienteDAO;
import org.acgprojeto.dao.impl.ClienteDAOImpl;
import org.acgprojeto.db.DB;
import org.acgprojeto.dto.ClienteDTO;

import java.sql.Connection;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        Connection conexao = DB.getConexao();
        ClienteDAO clienteDAO = new ClienteDAOImpl(conexao);
        clienteDAO.inserirCliente(new ClienteDTO(null,"Brunno","cicerobrnn111@gmail.com", null));
        clienteDAO.inserirCliente(new ClienteDTO(null,"Gabriel","gabriel@gmail.com", null));
        List<ClienteDTO> clientes = clienteDAO.listarTodosOsClientes();
        for(ClienteDTO cliente : clientes){
            System.out.println(cliente);
        }

        /*
        ProdutoDAO produtoDAO = new ProdutoDAOImpl(conexao);
        Categoria categoria = Categoria.CELULAR;
        BigDecimal preco = new BigDecimal(80);
        ProdutoDTO produtoDTO = new ProdutoDTO(null, 9, "cocaina", categoria, preco );
        List<Produto> produtos = produtoDAO.listarTodosOsProdutos();
        for(Produto produto : produtos){
            System.out.println(produto);
        }
         */


        DB.fecharConexao();

    }
}