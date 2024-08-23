package org.acgprojeto.application;

import org.acgprojeto.dao.*;
import org.acgprojeto.dao.impl.ClienteDAOImpl;
import org.acgprojeto.dao.impl.ProdutoDAOImpl;
import org.acgprojeto.db.DB;
import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.dto.ProdutoDTO;
import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.model.entidades.Pedido;
import org.acgprojeto.model.enums.Categoria;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public class Main  {

    public static void main(String[] args) {
//        Connection connection = DB.getConexao();
//
//        ClienteDAO clienteDAO = new ClienteDAOImpl(connection);
//        ProdutoDAO produtoDAO = new ProdutoDAOImpl(connection);
//        //ClienteDTO clienteDTO = new ClienteDTO(null, "opa", "gaag", "321321");
//        ProdutoDTO produtoDTO = ProdutoDTO.ProdutoDTOBuilder.aProdutoDTO()
//                .nomeProduto("algo")
//                .categoria(Categoria.PECA)
//                .quantidadeEstoque(3)
//                .preco(new BigDecimal(199.99))
//                .build();
//
//        //clienteDAO.inserirCliente(clienteDTO);
//
//        produtoDAO.inserirProduto(produtoDTO);
        ServicoDAO servicoDAO = DAOFactory.criarServicoDAO();
        PedidoDAO pedidoDAO = DAOFactory.criarPedidoDAO();
        PedidoDTO pedidoDTO = pedidoDAO.buscarPedidoPorId(1);
        Pedido pedido = new Pedido(pedidoDTO);
        pedido.getEstado().setPedidoDTO(pedido.toDTO());
        pedido.gerarRelatorio();
//        List<ServicoDTO> servicos = servicoDAO.listarServicosPorPedido(pedidoDTO);
//
//        for (ServicoDTO servico : servicos) {
//            System.out.println(servico);
//        }

        DB.fecharConexao();
    }




}