package org.acgprojeto.dao.impl;

import org.acgprojeto.dao.PedidoDAO;
import org.acgprojeto.db.exceptions.DBException;
import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.model.entities.Cliente;
import org.acgprojeto.model.entities.Pedido;
import org.acgprojeto.model.state.EstadoPedido;
import org.acgprojeto.model.state.impl.EstadoAndamento;
import org.acgprojeto.model.state.impl.EstadoCancelado;
import org.acgprojeto.model.state.impl.EstadoFinalizado;
import org.acgprojeto.model.state.impl.EstadoPronto;
import org.acgprojeto.model.enums.Estado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {
    private final Connection conexao;

    public PedidoDAOImpl(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void inserirPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido(pedidoDTO);

        String sql = "INSERT INTO Pedido(Id_Cliente, Estado, Data) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, pedido.getCliente().getIdCliente());
            stmt.setString(2, pedido.getEstado().toString());
            stmt.setDate(3, Date.valueOf(pedido.getData()));

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        pedido.setIdPedido(id);
                    }
                }
            } else {
                throw new DBException("Erro ao inserir linha");
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao inserir Pedido: ");
        }
    }

    @Override
    public void atualizarPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido(pedidoDTO);

        String sql = "UPDATE pedido SET Id_Cliente = ?, Estado = ?, Data = ? WHERE Id_Pedido = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getCliente().getIdCliente());
            stmt.setString(2, pedido.getEstado().toString());
            stmt.setDate(3, Date.valueOf(pedido.getData()));
            stmt.setInt(4, pedido.getIdPedido());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao atualizar Pedido: ");
        }
    }

    @Override
    public void atualizarEstadoPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido(pedidoDTO);

        String sql = "UPDATE pedido SET Estado = ? WHERE Id_Pedido = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, pedido.getEstado().getNomeEstado());
            stmt.setInt(2, pedido.getIdPedido());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao atualizar Pedido: ");
        }
    }

    @Override
    public void excluirPedido(Integer id) {
        String sql = "DELETE FROM pedido WHERE Id_Pedido = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao excluir Pedido de ID = " + id );
        }
    }

    @Override
    public PedidoDTO buscarPedidoPorId(Integer id) {
        String sql = "SELECT * FROM pedido WHERE Id_Pedido = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return instanciarPedido(rs);
                }
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao buscar Pedido de ID = " + id);
        }
        return null;
    }

    @Override
    public List<PedidoDTO> buscarPedidos() {
        String sql = "SELECT * FROM pedido";
        List<PedidoDTO> pedidos = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pedidos.add(instanciarPedido(rs));
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao listar Pedidos: ");
        }
        return pedidos;
    }

    private PedidoDTO instanciarPedido(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(rs.getInt("Id_Pedido"));
        pedido.setCliente(new Cliente(new ClienteDAOImpl(conexao).buscarClientePorId(rs.getInt("Id_Cliente"))));
        pedido.setData(rs.getDate("Data").toLocalDate());

        // Lê o valor do estado e garante que ele esteja em maiúsculas
        String estadoString = rs.getString("Estado");
        Estado estadoEnum;
        try {
            estadoEnum = Estado.valueOf(estadoString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Valor inválido para o estado: " + estadoString, e);
        }

        EstadoPedido estadoPedido = createEstado(estadoEnum);
        pedido.setEstado(estadoPedido);
        return new PedidoDTO(pedido);
    }


    private EstadoPedido createEstado(Estado estadoEnum) {
        switch (estadoEnum) {
                case ANDAMENTO:
                    return new EstadoAndamento();
                case PRONTO:
                    return new EstadoPronto();
                case CANCELADO:
                    return new EstadoCancelado();
                case FINALIZADO:
                    return new EstadoFinalizado();
                default:
                    throw new IllegalArgumentException("Estado inválido: " + estadoEnum);
            }
    }
}
