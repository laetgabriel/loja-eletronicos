package org.acgprojeto.dao.impl;

import org.acgprojeto.dao.ClienteDAO;
import org.acgprojeto.db.exceptions.DBException;
import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.model.entities.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

    private final Connection conexao;

    public ClienteDAOImpl(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void inserirCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente(clienteDTO);

        try (PreparedStatement stmt = conexao.prepareStatement(
                "INSERT INTO cliente (Nome, Email, Telefone) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefone());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        cliente.setIdCliente(id);
                    }
                }
            } else {
                throw new DBException("Erro ao inserir linha");
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao inserir cliente: ");
        }
    }

    @Override
    public void atualizarCliente(ClienteDTO clienteDTO) {
        try (PreparedStatement stmt = conexao.prepareStatement(
                "UPDATE cliente SET Nome = ?, Email = ?, Telefone = ? WHERE Id_Cliente = ?")) {

            stmt.setString(1, clienteDTO.getNome());
            stmt.setString(2, clienteDTO.getEmail());
            stmt.setString(3, clienteDTO.getTelefone());
            stmt.setInt(4, clienteDTO.getIdCliente());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DBException("Erro ao atualizar cliente: ");
        }
    }

    @Override
    public void excluirCliente(Integer id) {
        try (PreparedStatement stmt = conexao.prepareStatement(
                "DELETE FROM cliente WHERE Id_Cliente = ?")) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao excluir cliente com ID = " + id);
        }
    }

    @Override
    public ClienteDTO buscarClientePorId(Integer id) {
        try (PreparedStatement stmt = conexao.prepareStatement(
                "SELECT * FROM cliente WHERE Id_Cliente = ?")) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return instanciarClienteDTO(rs);
                }
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao buscar cliente com ID = " + id);
        }
        return null;
    }

    @Override
    public List<ClienteDTO> listarTodosOsClientes() {
        List<ClienteDTO> clientes = new ArrayList<>();
        try (PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM cliente");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                clientes.add(instanciarClienteDTO(rs));
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao listar clientes: ");
        }
        return clientes;
    }

    private ClienteDTO instanciarClienteDTO(ResultSet rs) throws SQLException {
        return new ClienteDTO(
                rs.getInt("Id_Cliente"),
                rs.getString("Nome"),
                rs.getString("Email"),
                rs.getString("Telefone")
        );
    }
}