package org.acgprojeto.dao.impl;

import org.acgprojeto.dao.ServicoDAO;
import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.model.entidades.Cliente;

import java.sql.Connection;
import java.util.List;

public class ServicoDAOImpl implements ServicoDAO {

    private Connection conexao;
    public ServicoDAOImpl(Connection conexao) {
    }

    @Override
    public void inserirServico(ServicoDTO cliente) {

    }

    @Override
    public void atualizarServico(ServicoDTO cliente) {

    }

    @Override
    public void excluirServico(Integer id) {

    }

    @Override
    public Cliente buscarServicoPorId(Integer id) {
        return null;
    }

    @Override
    public List<Cliente> listarTodosOsServicos() {
        return List.of();
    }
}
