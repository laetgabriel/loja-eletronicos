package org.acgprojeto.application;
import org.acgprojeto.dao.ClienteDAO;
import org.acgprojeto.dao.PedidoDAO;
import org.acgprojeto.dao.PedidoProdutoDAO;
import org.acgprojeto.dao.ProdutoDAO;
import org.acgprojeto.dao.impl.ClienteDAOImpl;
import org.acgprojeto.dao.impl.PedidoDAOImpl;
import org.acgprojeto.dao.impl.PedidoProdutoDAOImpl;
import org.acgprojeto.dao.impl.ProdutoDAOImpl;
import org.acgprojeto.db.DB;
import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.dto.PedidoProdutoDTO;
import org.acgprojeto.dto.ProdutoDTO;
import org.acgprojeto.model.enums.Categoria;
import org.acgprojeto.model.enums.Estado;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Date;
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

        DB.fecharConexao();

    }
}