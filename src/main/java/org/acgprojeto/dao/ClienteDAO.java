
package org.acgprojeto.dao;

import org.acgprojeto.dto.ClienteDTO;

import java.util.List;

public interface ClienteDAO {

    void inserirCliente(ClienteDTO cliente);
    void atualizarCliente(ClienteDTO cliente);
    void excluirCliente(Integer id);
    ClienteDTO buscarClientePorId(Integer id);
    List<ClienteDTO> listarTodosOsClientes();
    ClienteDTO obterUltimoCliente();
}
