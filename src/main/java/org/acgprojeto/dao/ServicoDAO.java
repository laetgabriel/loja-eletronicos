package org.acgprojeto.dao;

import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.dto.TabelaPedidoDTO;
import org.acgprojeto.model.enums.Tipo;

import java.util.List;

public interface ServicoDAO {

    void inserirServico(ServicoDTO cliente);
    void atualizarServico(ServicoDTO cliente);
    void excluirServico(Integer id);
    ServicoDTO buscarServicoPorId(Integer id);
    List<ServicoDTO> listarTodosOsServicos();
    List<ServicoDTO> listarServicosPorPedido(TabelaPedidoDTO pedido);
    List<ServicoDTO> listarServicosPorPedido(PedidoDTO pedido);
    List<Tipo> listarTodosOsTiposDeServicos();
}
