package org.acgprojeto.controller;

import org.acgprojeto.dao.ServicoDAO;
import org.acgprojeto.dao.impl.ServicoDAOImpl;
import org.acgprojeto.db.DB;
import org.acgprojeto.dto.ServicoDTO;

import java.util.List;

public class ServicoController {

    ServicoDAO servicoDAO = new ServicoDAOImpl(DB.getConexao());

    public void inserirServico(ServicoDTO servicoDTO) {
        servicoDAO.inserirServico(servicoDTO);
    }

    public void atualizarServico(ServicoDTO servicoDTO) {
        servicoDAO.atualizarServico(servicoDTO);
    }

    public void excluirServico(Integer id) {
        servicoDAO.excluirServico(id);
    }

    public List<ServicoDTO> listarTodosOsServicos() {
        return servicoDAO.listarTodosOsServicos();
    }

    public ServicoDTO buscarServicoPorId(Integer id) {
        return servicoDAO.buscarServicoPorId(id);
    }
}
