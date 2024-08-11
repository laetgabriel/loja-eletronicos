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
        DB db = new DB();
        DB db2 = new DB();
        String s = new String();
        /*
        ClienteDAO clienteDAO = new ClienteDAOImpl(conexao);
        clienteDAO.inserirCliente(new ClienteDTO(null,"Brunno","cicerobrnn111@gmail.com", null));
        clienteDAO.inserirCliente(new ClienteDTO(null,"Gabriel","gabriel@gmail.com", null));
        List<ClienteDTO> clientes = clienteDAO.listarTodosOsClientes();
        for(ClienteDTO cliente : clientes){
            System.out.println(cliente);
        }


        ClienteDAO clienteDAO = new ClienteDAOImpl(conexao);
        ClienteDTO clienteDTO = clienteDAO.buscarClientePorId(1);
        PedidoDAO pedidoDAO = new PedidoDAOImpl(conexao);
        PedidoDTO pedidoDTO = new PedidoDTO(null, clienteDTO, Estado.PAGO, LocalDate.parse("2024-08-10"));
        //pedidoDAO.inserirPedido(pedidoDTO);

        ProdutoDAO produtoDAO = new ProdutoDAOImpl(conexao);
        Categoria categoria = Categoria.CELULAR;
        BigDecimal preco = new BigDecimal(20);
        ProdutoDTO produtoDTO = new ProdutoDTO(null, 9, "Capinha", categoria, preco );
        ProdutoDTO produtoDTO2 = new ProdutoDTO(null, 2, "Bateria", categoria, preco.add(new BigDecimal(100)) );
        //produtoDAO.inserirProduto(produtoDTO);
        //produtoDAO.inserirProduto(produtoDTO2);

        PedidoDTO pedidoDTO = new PedidoDAOImpl(conexao).buscarPedidoPorId(1);
        ProdutoDTO produtoDTO = new ProdutoDAOImpl(conexao).buscarProdutoPorId(1);

        PedidoProdutoDAO pedidoProdutoDAO = new PedidoProdutoDAOImpl(conexao);
        BigDecimal quant = new BigDecimal(2);
        PedidoProdutoDTO pedidoProdutoDTO = new PedidoProdutoDTO(pedidoDTO, produtoDTO, new BigDecimal(String.valueOf(produtoDTO.getPreco().multiply(quant))), quant.intValue());
        pedidoProdutoDAO.inserirPedidoProduto(pedidoProdutoDTO);*/

        PedidoProdutoDTO pedidoProdutoDTO = new PedidoProdutoDAOImpl(conexao).buscarPedidoProduto(1,1);
        System.out.println(pedidoProdutoDTO.getPreco());

        DB.fecharConexao();

    }
}