package org.acgprojeto.dao.impl;

import org.acgprojeto.dao.PedidoDAO;
import org.acgprojeto.db.exceptions.DBException;
import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.dto.PedidoProdutoDTO;
import org.acgprojeto.dto.ProdutoDTO;
import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.model.entities.Cliente;
import org.acgprojeto.model.entities.Pedido;
import org.acgprojeto.model.entities.Servico;
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

    @Override
    public List<PedidoDTO> buscarPedidosParaTabelaPedidos() {
        String sql = "SELECT p.Id_Pedido, c.Id_Cliente, s.Id_Servico, c.Nome, s.Descricao, s.Preco, s.Tipo, p.Estado," +
                " p.`Data` " +
                "FROM pedido p " +
                "INNER JOIN cliente c ON p.Id_Cliente = c.Id_Cliente " +
                "INNER JOIN servico s ON s.Id_Pedido = p.Id_Pedido ";

        List<PedidoDTO> pedidos = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pedidos.add(instanciarPedidoServico(rs));
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao listar PedidosServicos: ");
        }
        return pedidos;
    }

    @Override
    public List<PedidoDTO> buscarPedidosParaTabelaRelPedidos() {
        String sql = "SELECT p.Id_Pedido, c.Id_Cliente, ppp.Id_Produto, s.Id_Servico, C.Nome as Cliente,P2.Nome as Produto, PPP.Preco, PPP.Quant,P.Estado,s.Tipo,s.Preco as Total,p.`Data` " +
                    "FROM pedido_possui_produto ppp " +
                    "INNER JOIN pedido p ON ppp.Id_Pedido = p.Id_Pedido " +
                    "INNER JOIN produto p2 ON ppp.Id_Produto = p2.Id_Produto " +
                    "INNER JOIN cliente c ON p.Id_Cliente = c.Id_Cliente " +
                    "INNER JOIN servico s ON s.Id_Pedido = p.Id_Pedido ";

        List<PedidoDTO> pedidos = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pedidos.add(instanciarPedidoAll(rs));
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao listar PedidosAll: ");
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

    private PedidoDTO instanciarPedidoServico(ResultSet rs) throws SQLException {
        PedidoDTO pedidoDTO = instanciarPedido(rs);
        ServicoDTO servicoDTO = new ServicoDAOImpl(conexao).buscarServicoPorId(rs.getInt("Id_Servico"));
        pedidoDTO.setDescricaoServico(servicoDTO.getDescricao());
        pedidoDTO.setValorServico(servicoDTO.getPreco());
        pedidoDTO.setTipoServico(servicoDTO.getTipo());

        return pedidoDTO;
    }

    private PedidoDTO instanciarPedidoAll(ResultSet rs) throws SQLException{
        PedidoDTO pedidoDTO = instanciarPedidoServico(rs);
        ProdutoDTO produtoDTO = new ProdutoDAOImpl(conexao).buscarProdutoPorId(rs.getInt("Id_Produto"));

        PedidoProdutoDTO pedidoProdutoDTO = new PedidoProdutoDAOImpl(conexao).buscarPedidoProduto(rs.getInt("Id_Pedido"),
                rs.getInt("Id_Produto"));

        pedidoDTO.setNomeProduto(produtoDTO.getNomeProduto());
        pedidoDTO.setPrecoPedidoProduto(pedidoProdutoDTO.getPreco());
        pedidoDTO.setQuantidadePedidoProduto(pedidoProdutoDTO.getQuantidade());

        return pedidoDTO;
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
