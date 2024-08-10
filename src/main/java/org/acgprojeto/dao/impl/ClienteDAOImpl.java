package org.acgprojeto.dao.impl;

import org.acgprojeto.dao.ClienteDAO;
import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.model.entidades.Cliente;

import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public void inserirCliente(ClienteDTO cliente) {

    }

    @Override
    public void atualizarCliente(ClienteDTO cliente) {

    }

    @Override
    public void excluirCliente(ClienteDTO cliente) {

    }

    @Override
    public Cliente buscarClientePorId(Integer id) {
        return null;
    }

    @Override
    public List<Cliente> listarTodosOsClientes() {
        return List.of();
    }
}
