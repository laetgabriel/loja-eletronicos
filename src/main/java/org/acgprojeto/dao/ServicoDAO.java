package org.acgprojeto.dao;

import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.model.entidades.Cliente;

import java.util.List;

public interface ServicoDAO {

    void inserirServico(ServicoDTO cliente);
    void atualizarServico(ServicoDTO cliente);
    void excluirServico(Integer id);
    ServicoDTO buscarServicoPorId(Integer id);
    List<ServicoDTO> listarTodosOsServicos();
}
