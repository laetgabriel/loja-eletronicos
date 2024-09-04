package org.acgprojeto.dao.impl;

import org.acgprojeto.dao.ServicoDAO;
import org.acgprojeto.db.exceptions.DBException;
import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.dto.TabelaPedidoDTO;
import org.acgprojeto.model.entities.Pedido;
import org.acgprojeto.model.entities.Servico;
import org.acgprojeto.model.enums.Tipo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAOImpl implements ServicoDAO {

    private final Connection conexao;

    public  ServicoDAOImpl(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void inserirServico(ServicoDTO servicoDTO) {
        Servico servico = new Servico(servicoDTO);
        String sql = "INSERT INTO servico(Id_Pedido, Descricao, Preco, Tipo) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, servicoDTO.getPedido().getIdPedido());
            stmt.setString(2, servicoDTO.getDescricao());
            stmt.setBigDecimal(3, servicoDTO.getPreco());
            stmt.setString(4, servicoDTO.getTipo().toString());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        servico.setIdServico(id);
                    }
                }
            } else {
                throw new DBException("Erro ao inserir linha");
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao inserir Serviço: ");
        }
    }

    @Override
    public void atualizarServico(ServicoDTO servicoDTO) {
        String sql = "UPDATE servico SET Id_Pedido = ?, Descricao = ?, Preco = ?, Tipo = ? WHERE Id_Servico = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, servicoDTO.getPedido().getIdPedido());
            stmt.setString(2, servicoDTO.getDescricao());
            stmt.setBigDecimal(3, servicoDTO.getPreco());
            stmt.setString(4, servicoDTO.getTipo().toString());
            stmt.setInt(5, servicoDTO.getIdServico());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao atualizar Serviço: ");
        }
    }

    @Override
    public void excluirServico(Integer id) {
        String sql = "DELETE FROM servico WHERE Id_Servico = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao excluir Serviço de ID = " + id);
        }
    }

    @Override
    public ServicoDTO buscarServicoPorId(Integer id) {
        String sql = "SELECT * FROM servico WHERE Id_Servico = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return instanciarServico(rs);
                }
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao buscar Serviço de ID = " + id);
        }
        return null;
    }

    @Override
    public List<ServicoDTO> listarTodosOsServicos() {
        String sql = "SELECT * FROM servico";
        List<ServicoDTO> servicos = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                servicos.add(instanciarServico(rs));
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao listar Serviços: ");
        }
        return servicos;
    }

    @Override
    public List<ServicoDTO> listarServicosPorPedido(PedidoDTO pedido) {
        String sql = "select * from servico where Id_Pedido = ?";
        List<ServicoDTO> servicos = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getIdPedido());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    servicos.add(instanciarServico(rs));
                }
            } catch (SQLException e) {
                throw new DBException("Erro ao processar o ResultSet: ");
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao listar serviços: ");
        }

        return servicos;
    }

    @Override
    public List<ServicoDTO> listarServicosPorPedido(TabelaPedidoDTO pedido) {
        String sql = "select * from servico where Id_Pedido = ?";
        List<ServicoDTO> servicos = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getPedidoDTO().getIdPedido());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    servicos.add(instanciarServico(rs));
                }
            } catch (SQLException e) {
                throw new DBException("Erro ao processar o ResultSet: ");
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao listar serviços: ");
        }

        return servicos;
    }

    @Override
    public List<Tipo> listarTodosOsTiposDeServicos() {
        String sql = "SELECT DISTINCT tipo FROM servico s ";
        List<Tipo> tipoServicos = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tipoServicos.add(Tipo.valueOf(rs.getString("tipo")));
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao listar tipos de Serviços: ");
        }
        return tipoServicos;
    }



    private ServicoDTO instanciarServico(ResultSet rs) throws SQLException {
        Servico servico = new Servico();
        servico.setIdServico(rs.getInt("Id_Servico"));
        servico.setPedido(new Pedido(new PedidoDAOImpl(conexao).buscarPedidoPorId(rs.getInt("Id_Pedido"))));
        servico.setDescricao(rs.getString("Descricao"));
        servico.setPreco(rs.getBigDecimal("Preco"));
        servico.setTipo(Tipo.valueOf(rs.getString("Tipo")));
        return new ServicoDTO(servico);
    }
}
