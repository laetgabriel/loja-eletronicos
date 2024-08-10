package org.acgprojeto.dao;

import org.acgprojeto.dao.impl.*;
import org.acgprojeto.db.DB;

public class DAOFactory {

    public static ClienteDAO criarClienteDAO() {
        return new ClienteDAOImpl(DB.getConexao());
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
