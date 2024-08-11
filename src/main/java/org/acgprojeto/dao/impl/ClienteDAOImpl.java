
package org.acgprojeto.dao.impl;

import org.acgprojeto.dao.ClienteDAO;
import org.acgprojeto.db.DB;
import org.acgprojeto.db.exceptions.DBException;
import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.model.entidades.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

    private Connection conexao;

    public ClienteDAOImpl(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void inserirCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente(clienteDTO);
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            stmt = conexao.prepareStatement(
                    "INSERT INTO cliente(Nome, Email, Telefone) " +
                            "values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefone());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                rs = stmt.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    cliente.setIdCliente(id);
                }
            }else
                throw new DBException("Erro ao inserir linha");
        }catch(SQLException e){
            throw new DBException("Erro ao inserir cliente");
        }finally {
            DB.fecharStatement(stmt);
            DB.fecharResultSet(rs);
        }
    }

    @Override
    public void atualizarCliente(ClienteDTO clienteDTO) {
        PreparedStatement stmt = null;

        try{
            stmt = conexao.prepareStatement(
                    "update cliente set Nome = ?, Email = ?, Telefone = ?" +
                            " where cliente.Id_Cliente = ?"
            );
            stmt.setString(1, clienteDTO.getNome());
            stmt.setString(2, clienteDTO.getEmail());
            stmt.setString(3, clienteDTO.getTelefone());
            stmt.setInt(4, clienteDTO.getIdCliente());

            stmt.executeUpdate();

        }catch(SQLException e){
            throw new DBException("Erro ao atualizar Cliente");
        }finally {
            DB.fecharStatement(stmt);
        }
    }

    @Override
    public void excluirCliente(Integer id) {

        PreparedStatement stmt = null;
        try{
            stmt = conexao.prepareStatement(
                    "delete from cliente where cliente.Id_Cliente = ?"
            );

            stmt.setInt(1, id);

            stmt.executeUpdate();
        }catch(SQLException e){
            throw new DBException("Erro ao excluir Cliente de ID = " + id);
        }finally {
            DB.fecharStatement(stmt);
        }
    }

    @Override
    public ClienteDTO buscarClientePorId(Integer id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            stmt = conexao.prepareStatement(
                    "SELECT * FROM cliente WHERE cliente.Id_Cliente = ?"
            );

            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()){
                return instanciarClienteDTO(rs);
            }
        }catch(SQLException e){
            throw new DBException("Erro ao buscar Cliente de ID = " + id);
        }finally {
            DB.fecharStatement(stmt);
            DB.fecharResultSet(rs);
        }
        return null;
    }

    @Override
    public List<ClienteDTO> listarTodosOsClientes() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            stmt = conexao.prepareStatement(
                    "select * from cliente"
            );
            rs = stmt.executeQuery();
            List<ClienteDTO> clientes = new ArrayList<>();

            while (rs.next()){
                clientes.add(instanciarClienteDTO(rs));
            }
            return clientes;
        }catch(SQLException e){
            throw new DBException("Erro ao listar Clientes");
        }
    }

    private ClienteDTO instanciarClienteDTO(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(rs.getInt("Id_Cliente"));
        cliente.setNome(rs.getString("Nome"));
        cliente.setEmail(rs.getString("Email"));
        cliente.setTelefone(rs.getString("Telefone"));
        return new ClienteDTO(cliente);
    }
}
