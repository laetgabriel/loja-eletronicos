package org.acgprojeto.dao;

import org.acgprojeto.dao.impl.*;
import org.acgprojeto.db.DB;

public class DAOFactory {

    public static ClienteDAO criarClienteDAO() {
        return new ClienteDAOImpl(DB.getConexao());
    }

    public static ProdutoDAO criarProdutoDAO() {
        return new ProdutoDAOImpl(DB.getConexao());
    }

    public static PedidoDAO criarPedidoDAO() {
        return new PedidoDAOImpl(DB.getConexao());
    }

    public static PedidoProdutoDAO criarPedidoProdutoDAO() {
        return new PedidoProdutoDAOImpl(DB.getConexao());
    }

    public static ServicoDAO criarServicoDAO() {
        return new ServicoDAOImpl(DB.getConexao());
    }

    public static AdminDAO criarAdminDAO() {
        return new AdminDAOImpl(DB.getConexao());
    }
}
