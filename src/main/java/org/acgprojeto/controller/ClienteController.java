package org.acgprojeto.controller;

import org.acgprojeto.dao.ClienteDAO;
import org.acgprojeto.dao.impl.ClienteDAOImpl;
import org.acgprojeto.db.DB;
import org.acgprojeto.dto.ClienteDTO;

import java.util.List;

public class ClienteController {

    ClienteDAO clienteDAO = new ClienteDAOImpl(DB.getConexao());

    public void inserirCliente(ClienteDTO clienteDTO) {
        clienteDAO.inserirCliente(clienteDTO);
    }

    public void atualizarCliente(ClienteDTO clienteDTO) {
        clienteDAO.atualizarCliente(clienteDTO);
    }

    public void excluirCliente(Integer id) {
        clienteDAO.excluirCliente(id);
    }

    public ClienteDTO buscarClientePorId(Integer id) {
        return clienteDAO.buscarClientePorId(id);
    }

    public List<ClienteDTO> listarTodosOsClientes() {
        return clienteDAO.listarTodosOsClientes();
    }

}
