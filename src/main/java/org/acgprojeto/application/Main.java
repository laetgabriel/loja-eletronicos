package org.acgprojeto.application;


import org.acgprojeto.dao.ClienteDAO;
import org.acgprojeto.dao.DAOFactory;
import org.acgprojeto.db.DB;
import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.model.entidades.Cliente;

import java.sql.Connection;


public class Main {
    public static void main(String[] args) {

        Connection conexao = DB.getConexao();
        ClienteDAO clienteDAO = DAOFactory.criarClienteDAO();

        clienteDAO.excluirCliente(6);
        DB.fecharConexao();

    }
}