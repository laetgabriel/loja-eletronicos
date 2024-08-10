package org.acgprojeto.dao;

import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.model.entidades.Cliente;

import java.util.List;

public interface ClienteDAO {

    void inserirCliente(ClienteDTO cliente);
    void atualizarCliente(ClienteDTO cliente);
    void excluirCliente(Integer id);
    Cliente buscarClientePorId(Integer id);
    List<Cliente> listarTodosOsClientes();
}
