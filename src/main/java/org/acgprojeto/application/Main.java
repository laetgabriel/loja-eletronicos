package org.acgprojeto.application;

import org.acgprojeto.dao.*;
import org.acgprojeto.db.DB;
import org.acgprojeto.dto.AdminDTO;


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

        //AdminDTO adminDTO = new AdminDTO(null, "Gabriel", "gabriellaetfm12@gmail.com", "senha123");

        AdminDTO admin = adminDAO.buscarAdminPorId(4);
        admin.setNome("Gagael");
        adminDAO.atualizarAdmin(admin);
        DB.fecharConexao();
    }




}