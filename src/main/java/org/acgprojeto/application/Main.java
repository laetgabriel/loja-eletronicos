package org.acgprojeto.application;

import org.acgprojeto.dao.*;
import org.acgprojeto.dao.impl.PedidoDAOImpl;
import org.acgprojeto.db.DB;
import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.model.entities.Pedido;

public class Main  {

    public static void main(String[] args) {

        AdminDAO adminDAO = DAOFactory.criarAdminDAO();

//        ClienteDAO clienteDAO = new ClienteDAOImpl(connection);
//        ProdutoDAO produtoDAO = new ProdutoDAOImpl(connection);
//        //ClienteDTO clienteDTO = new ClienteDTO(null, "opa", "gaag", "321321");
//        ProdutoDTO produtoDTO = ProdutoDTO.ProdutoDTOBuilder.aProdutoDTO()
//                .nomeProduto("algo")
//                .categoria(Categoria.PECA)
//                .quantidadeEstoque(3)
//                .preco(new BigDecimal(199.99))
//                .build();

        //clienteDAO.inserirCliente(clienteDTO);

        //produtoDAO.inserirProduto(produtoDTO);

//        produtoDAO.excluirProduto(4);
//        DB.fecharConexao();

        PedidoDAO pedidoDAO = new PedidoDAOImpl(DB.getConexao());
        PedidoDTO pedidoDTO = pedidoDAO.buscarPedidoPorId(1);
        pedidoDAO.atualizarEstadoPedido(pedidoDTO);
        Pedido pedido = new Pedido(pedidoDTO);
        pedido.gerarRelatorio();

        DB.fecharConexao();
    }




}