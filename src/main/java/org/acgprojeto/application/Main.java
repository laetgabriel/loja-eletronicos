package org.acgprojeto.application;

import org.acgprojeto.service.MensageiroService;

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

        MensageiroService mensageiroService = new MensageiroService();
        mensageiroService.enviarEmail("gabriellaetfm12@gmail.com", "teste", "teste");

    }




}