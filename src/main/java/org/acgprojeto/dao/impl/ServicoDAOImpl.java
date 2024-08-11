package org.acgprojeto.dao.impl;

import org.acgprojeto.dao.ServicoDAO;
import org.acgprojeto.db.DB;
import org.acgprojeto.db.exceptions.DBException;
import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.dto.ProdutoDTO;
import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.model.entidades.Pedido;
import org.acgprojeto.model.entidades.Servico;
import org.acgprojeto.model.enums.Tipo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAOImpl implements ServicoDAO {

    private Connection conexao;
    public ServicoDAOImpl(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void inserirServico(ServicoDTO servicoDTO) {
        Servico servico = new Servico(servicoDTO);
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conexao.prepareStatement(
                    "INSERT INTO servico(Id_Pedido, Descricao, Preco, Tipo) " +
                            "values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            );
            stmt.setInt(1, servicoDTO.getPedido().getIdPedido());
            stmt.setString(2, servicoDTO.getDescricao());
            stmt.setBigDecimal(3, servicoDTO.getPreco());
            stmt.setString(4, servicoDTO.getTipo().toString());


            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    servico.setIdServico(id);
                }
            } else
                throw new DBException("Erro ao inserir linha");
        } catch (SQLException e) {
            throw new DBException("Erro ao inserir Serviço");
        } finally {
            DB.fecharStatement(stmt);
            DB.fecharResultSet(rs);
        }
    }

    @Override
    public void atualizarServico(ServicoDTO servicoDTO) {
        Servico servico = new Servico(servicoDTO);
        PreparedStatement stmt = null;

        try {
            stmt = conexao.prepareStatement(
                    "update servico set Id_Pedido = ?, Descricao = ?, Preco = ?, Tipo = ? " +
                            "where servico.Id_Servico = ?"
            );
            stmt.setInt(1, servico.getPedido().getIdPedido());
            stmt.setString(2, servico.getDescricao());
            stmt.setBigDecimal(3, servico.getPreco());
            stmt.setString(4, servico.getTipo().toString());
            stmt.setInt(5, servico.getIdServico());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DBException("Erro ao atualizar Serviço");
        } finally {
            DB.fecharStatement(stmt);
        }
    }

    @Override
    public void excluirServico(Integer id) {
        PreparedStatement stmt = null;
        try {
            stmt = conexao.prepareStatement(
                    "delete from servico where servico.Id_Servico = ?"
            );

            stmt.setInt(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao excluir Serviço de ID = " + id);
        } finally {
            DB.fecharStatement(stmt);
        }
    }

    @Override
    public ServicoDTO buscarServicoPorId(Integer id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conexao.prepareStatement(
                    "SELECT * FROM servico WHERE servico.Id_Servico = ?"
            );

            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return instanciarServico(rs);
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao buscar serviço de ID = " + id);
        } finally {
            DB.fecharStatement(stmt);
            DB.fecharResultSet(rs);
        }
        return null;
    }


    @Override
    public List<ServicoDTO> listarTodosOsServicos() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            stmt = conexao.prepareStatement(
                    "select * from servico"
            );
            rs = stmt.executeQuery();
            List<ServicoDTO> servicos = new ArrayList<>();

            while (rs.next()){
                servicos.add(instanciarServico(rs));
            }
            return servicos;
        }catch(SQLException e){
            throw new DBException("Erro ao listar Serviços");
        }finally {
            DB.fecharStatement(stmt);
            DB.fecharResultSet(rs);
        }
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
