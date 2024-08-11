package org.acgprojeto.application;
import org.acgprojeto.dao.*;
import org.acgprojeto.dao.impl.*;
import org.acgprojeto.db.DB;
import org.acgprojeto.dto.*;
import org.acgprojeto.model.enums.Categoria;
import org.acgprojeto.model.enums.Estado;
import org.acgprojeto.model.enums.Tipo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        Connection conexao = DB.getConexao();


        ClienteDAO clienteDAO = new ClienteDAOImpl(conexao);
        ClienteDTO clienteDTO = clienteDAO.buscarClientePorId(9);
        PedidoDAO pedidoDAO = new PedidoDAOImpl(conexao);
        PedidoDTO pedidoDTO = new PedidoDTO(null, clienteDTO, Estado.PAGO, LocalDate.parse("2024-08-10"));
        pedidoDAO.inserirPedido(pedidoDTO);

        ProdutoDAO produtoDAO = new ProdutoDAOImpl(conexao);
        Categoria categoria = Categoria.CELULAR;
        BigDecimal preco = new BigDecimal(20);
        ProdutoDTO produtoDTO = new ProdutoDTO(null, 9, "Capinha", categoria, preco );
        ProdutoDTO produtoDTO2 = new ProdutoDTO(null, 2, "Bateria", categoria, preco.add(new BigDecimal(100)) );
        ServicoDAO servicoDAO = new ServicoDAOImpl(conexao);
        produtoDAO.inserirProduto(produtoDTO);
        produtoDAO.inserirProduto(produtoDTO2);

        PedidoDTO pedidoDTO1 = new PedidoDAOImpl(conexao).buscarPedidoPorId(3);
        ProdutoDTO produtoDTO1 = new ProdutoDAOImpl(conexao).buscarProdutoPorId(5);

        ServicoDTO servicoDTO = new ServicoDTO(null, pedidoDTO, "um projetinho meu!" , new BigDecimal(50), Tipo.CONSERTO);
        servicoDAO.inserirServico(servicoDTO);
        PedidoProdutoDAO pedidoProdutoDAO = new PedidoProdutoDAOImpl(conexao);
        BigDecimal quant = new BigDecimal(2);
        PedidoProdutoDTO pedidoProdutoDTO = new PedidoProdutoDTO(pedidoDTO1, produtoDTO1, new BigDecimal(String.valueOf(produtoDTO1.getPreco().multiply(quant))), quant.intValue());
        pedidoProdutoDAO.inserirPedidoProduto(pedidoProdutoDTO);

        PedidoProdutoDTO pedidoProdutoDTO1 = new PedidoProdutoDAOImpl(conexao).buscarPedidoProduto(3,5);
        System.out.println(pedidoProdutoDTO1.getPreco());

        DB.fecharConexao();
       // ola amigos
    }
}

