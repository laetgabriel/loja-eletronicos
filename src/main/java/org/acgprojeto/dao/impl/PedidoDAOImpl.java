
package org.acgprojeto.dao.impl;

import org.acgprojeto.dao.ClienteDAO;
import org.acgprojeto.dao.PedidoDAO;
import org.acgprojeto.db.DB;
import org.acgprojeto.db.exceptions.DBException;
import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.dto.PedidoProdutoDTO;
import org.acgprojeto.model.entidades.Cliente;
import org.acgprojeto.model.entidades.Pedido;
import org.acgprojeto.model.entidades.PedidoProduto;
import org.acgprojeto.model.enums.Estado;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {
    private Connection conexao;

    public PedidoDAOImpl(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void inserirPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido(pedidoDTO);
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            stmt = conexao.prepareStatement(
                    "INSERT INTO Pedido(Id_Cliente, Estado, Data) " +
                            "values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            );
            stmt.setInt(1, pedido.getCliente().getIdCliente());
            stmt.setString(2, pedido.getEstado().toString());
            stmt.setDate(3, Date.valueOf(pedido.getData()));

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                rs = stmt.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    pedido.setIdPedido(id);
                }
            }else
                throw new DBException("Erro ao inserir linha");
        }catch(SQLException e){
            throw new DBException("Erro ao inserir Pedido");
        }finally {
            DB.fecharStatement(stmt);
            DB.fecharResultSet(rs);
        }
    }

    @Override
    public void atualizarPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido(pedidoDTO);
        PreparedStatement stmt = null;

        try {
            stmt = conexao.prepareStatement(
                    "update pedido set Id_Cliente = ?, Estado = ?, Data = ? " +
                            "where pedido.Id_Pedido = ?"
            );
            stmt.setInt(1, pedido.getCliente().getIdCliente());
            stmt.setString(2, pedido.getEstado().toString());
            stmt.setDate(3, Date.valueOf(pedido.getData()));
            stmt.setInt(4, pedido.getIdPedido());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DBException("Erro ao atualizar Pedido");
        } finally {
            DB.fecharStatement(stmt);
        }
    }

    @Override
    public void excluirPedido(Integer id) {
        PreparedStatement stmt = null;
        try {
            stmt = conexao.prepareStatement(
                    "delete from pedido where pedido.Id_Pedido = ?"
            );

            stmt.setInt(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao excluir Pedido de ID = " + id);
        } finally {
            DB.fecharStatement(stmt);
        }
    }

    @Override
    public PedidoDTO buscarPedidoPorId(Integer id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            stmt = conexao.prepareStatement(
                    "select * from pedido " +
                            "where pedido.Id_Pedido = ?"
            );

            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            if(rs.next()) {
                return instanciarPedido(rs);
            }

        }catch(SQLException e){
            throw new DBException("Erro ao buscar Pedido de ID = " + id);
        }finally {
            DB.fecharStatement(stmt);
            DB.fecharResultSet(rs);
        }
        return null;
    }

    @Override
    public List<PedidoDTO> buscarPedidos() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            stmt = conexao.prepareStatement(
                    "select * from pedido"
            );

            rs = stmt.executeQuery();

            List<PedidoDTO> pedidoProdutos = new ArrayList<>();

            while (rs.next()){
                pedidoProdutos.add(instanciarPedido(rs));
            }

            return pedidoProdutos;

        }catch(SQLException e){
            throw new DBException("Erro ao listar Pedidos");
        }finally {
            DB.fecharStatement(stmt);
            DB.fecharResultSet(rs);
        }
    }

    private PedidoDTO instanciarPedido(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(rs.getInt("Id_Pedido"));
        pedido.setCliente(new Cliente(new ClienteDAOImpl(conexao).buscarClientePorId(rs.getInt("Id_Cliente"))));
        pedido.setEstado(Estado.valueOf(rs.getString("Estado")));
        pedido.setData(LocalDate.parse(rs.getDate("Data").toString()));
        return new PedidoDTO(pedido);
    }
}
