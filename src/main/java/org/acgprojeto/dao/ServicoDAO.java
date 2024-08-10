package org.acgprojeto.dao;

import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.model.entidades.Cliente;

import java.util.List;

public interface ServicoDAO {

    void inserirServico(ServicoDTO cliente);
    void atualizarServico(ServicoDTO cliente);
    void excluirServico(Integer id);
    Cliente buscarServicoPorId(Integer id);
    List<Cliente> listarTodosOsServicos();
}
