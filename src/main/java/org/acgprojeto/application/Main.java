package org.acgprojeto.application;

import org.acgprojeto.dao.AdminDAO;
import org.acgprojeto.dao.PedidoDAO;
import org.acgprojeto.dao.ServicoDAO;
import org.acgprojeto.dao.impl.AdminDAOImpl;
import org.acgprojeto.dao.impl.PedidoDAOImpl;
import org.acgprojeto.dao.impl.ServicoDAOImpl;
import org.acgprojeto.db.DB;
import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.model.entities.Pedido;
import org.acgprojeto.model.enums.Tipo;
import org.acgprojeto.service.MensageiroService;

import java.math.BigDecimal;
import java.util.List;

public class Main  {

    public static void main(String[] args) {


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

        PedidoDTO pedido = new PedidoDAOImpl(DB.getConexao()).buscarPedidoPorId(1);

        ServicoDTO servico = new ServicoDTO(1,pedido, "Venda Capinha", new BigDecimal(80), Tipo.VENDA);
        ServicoDAO servicoDAO = new ServicoDAOImpl(DB.getConexao());
        servicoDAO.atualizarServico(servico);

    }
}