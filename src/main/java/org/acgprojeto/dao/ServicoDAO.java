package org.acgprojeto.dao;

import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.dto.ServicoDTO;

import java.util.List;

public interface ServicoDAO {

    void inserirServico(ServicoDTO cliente);
    void atualizarServico(ServicoDTO cliente);
    void excluirServico(Integer id);
    ServicoDTO buscarServicoPorId(Integer id);
    List<ServicoDTO> listarTodosOsServicos();
    List<ServicoDTO> listarServicosPorPedido(PedidoDTO pedido);
}
