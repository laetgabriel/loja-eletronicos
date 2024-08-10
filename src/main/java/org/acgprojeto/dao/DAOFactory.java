package org.acgprojeto.dao;

import org.acgprojeto.dao.impl.*;

public class DAOFactory {

    public static ClienteDAO criarClienteDAO() {
        return new ClienteDAOImpl();
    }

    public static ProdutoDAO criarProdutoDAO() {
        return new ProdutoDAOImpl();
    }

    public static PedidoDAO criarPedidoDAO() {
        return new PedidoDAOImpl();
    }

    public static PedidoProdutoDAO criarPedidoProdutoDAO() {
        return new PedidoProdutoDAOImpl();
    }

    public static ServicoDAO criarServicoDAO() {
        return new ServicoDAOImpl();
    }
}
