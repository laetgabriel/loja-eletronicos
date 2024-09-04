package org.acgprojeto.dao.impl;

import org.acgprojeto.dao.DAOFactory;
import org.acgprojeto.dao.PedidoDAO;
import org.acgprojeto.db.exceptions.DBException;
import org.acgprojeto.dto.*;
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
            stmt.setString(2, pedido.getEstado().getNomeEstado());
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
            throw new DBException("Erro ao inserir Pedido: " + e.getMessage()   );
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
    public List<TabelaPedidoDTO> buscarTabelaPedidoPorId(Integer id) {
        List<Integer> listaIndiceServico = indicesServicoPorIdPedido(id);

        String sql = "SELECT p.Id_Pedido, c.Id_Cliente, ppp.Id_Produto, c.Nome as Cliente, p2.Nome as Produto, ppp.Preco, ppp.Quant, p.Estado, p.Data " +
                "FROM pedido p " +
                "LEFT JOIN cliente c ON p.Id_Cliente = c.Id_Cliente " +
                "LEFT JOIN pedido_possui_produto ppp ON ppp.Id_Pedido = p.Id_Pedido " +
                "LEFT JOIN produto p2 ON ppp.Id_Produto = p2.Id_Produto " +
                "WHERE p.Id_Pedido = ?";

        List<TabelaPedidoDTO> pedidos = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                int indiceServico = 0;

                while (rs.next()) {
                    Integer idServico = null;

                    if (indiceServico < listaIndiceServico.size()) {
                        idServico = listaIndiceServico.get(indiceServico);
                        indiceServico++;
                    }

                    pedidos.add(instanciarTabelaPedidoAll(rs, idServico));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao listar Pedidos com Produtos: " + e);
        }

        return pedidos;
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
    public List<TabelaPedidoDTO> buscarPedidosParaTabelaPedidos() {
        String sql = "SELECT p.Id_Pedido, c.Id_Cliente, s.Id_Servico, c.Nome, s.Descricao, s.Preco, s.Tipo, p.Estado, p.`Data` " +
                "FROM pedido p " +
                "LEFT JOIN cliente c ON p.Id_Cliente = c.Id_Cliente " +
                "LEFT JOIN servico s ON s.Id_Pedido = p.Id_Pedido ";

        List<TabelaPedidoDTO> pedidos = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pedidos.add(instanciarTabelaPedidoServico(rs));
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao listar PedidosServicos: ");
        }
        return pedidos;
    }

    public List<TabelaPedidoDTO> buscarPedidosParaTabelaRelPedidos() {
        List<Integer> listaIndiceServico = indicesServicoPorPedido();

        String sql = "SELECT p.Id_Pedido, c.Id_Cliente, ppp.Id_Produto, c.Nome as Cliente, p2.Nome as Produto, ppp.Preco, ppp.Quant, p.Estado, p.Data " +
                "FROM pedido p " +
                "LEFT JOIN cliente c ON p.Id_Cliente = c.Id_Cliente " +
                "LEFT JOIN pedido_possui_produto ppp ON ppp.Id_Pedido = p.Id_Pedido " +
                "LEFT JOIN produto p2 ON ppp.Id_Produto = p2.Id_Produto";

        List<TabelaPedidoDTO> pedidos = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            int indiceServico = 0;

            while (rs.next()) {
                if (indiceServico < listaIndiceServico.size()) {
                    pedidos.add(instanciarTabelaPedidoAll(rs, listaIndiceServico.get(indiceServico)));
                    indiceServico++;
                } else {
                    pedidos.add(instanciarTabelaPedidoAll(rs, null));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao listar Pedidos com Produtos: " + e);
        }
        return pedidos;
    }

    private TabelaPedidoDTO instanciarTabelaPedido(ResultSet rs) throws SQLException {
        PedidoDTO pedido = new PedidoDTO();
        pedido.setIdPedido(rs.getInt("Id_Pedido"));

        ClienteDTO clienteDTO = DAOFactory.criarClienteDAO().buscarClientePorId(rs.getInt("Id_Cliente"));
        if(clienteDTO == null) {
            clienteDTO = new ClienteDTO();
        }

        pedido.setCliente(clienteDTO);
        pedido.setData(rs.getDate("Data").toLocalDate());

        // Lê o valor do estado e garante que ele esteja em maiúsculas
        String estadoString = rs.getString("Estado");
        Estado estadoEnum;
        try {
            estadoEnum = Estado.valueOf(estadoString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Valor inválido para o estado: " + estadoString, e);
        }

        pedido.setEstado(estadoEnum);
        TabelaPedidoDTO tabelaPedidoDTO = new TabelaPedidoDTO();
        tabelaPedidoDTO.setPedidoDTO(pedido);
        return tabelaPedidoDTO;
    }

    private TabelaPedidoDTO instanciarTabelaPedidoServico(ResultSet rs) throws SQLException {
        TabelaPedidoDTO tabelaPedidoDTO = instanciarTabelaPedido(rs);
        ServicoDTO servicoDTO = DAOFactory.criarServicoDAO().buscarServicoPorId(rs.getInt("Id_Servico"));
        if (servicoDTO == null){
            servicoDTO = new ServicoDTO();
        }

        tabelaPedidoDTO.setServicoDTO(servicoDTO);
        return tabelaPedidoDTO;

    }

    private TabelaPedidoDTO instanciarTabelaPedidoAll(ResultSet rs, Integer indiceServico) throws SQLException{
        TabelaPedidoDTO tabelaPedidoDTO = instanciarTabelaPedido(rs);

        ProdutoDTO produtoDTO = DAOFactory.criarProdutoDAO().buscarProdutoPorId(rs.getInt("Id_Produto"));

        if(produtoDTO == null){
            produtoDTO = ProdutoDTO.ProdutoDTOBuilder.aProdutoDTO().build();
        }
        tabelaPedidoDTO.setProdutoDTO(produtoDTO);

        PedidoProdutoDTO pedidoProdutoDTO = DAOFactory.criarPedidoProdutoDAO().buscarPedidoProduto(rs.getInt("Id_Pedido"),
                rs.getInt("Id_Produto"));

        if (pedidoProdutoDTO == null){
            pedidoProdutoDTO = new PedidoProdutoDTO();
        }
        tabelaPedidoDTO.setPedidoProdutoDTO(pedidoProdutoDTO);

        ServicoDTO servicoDTO = DAOFactory.criarServicoDAO().buscarServicoPorId(indiceServico);
        if (servicoDTO == null){
            servicoDTO = new ServicoDTO();
        }

        tabelaPedidoDTO.setServicoDTO(servicoDTO);

        return tabelaPedidoDTO;
    }

    public PedidoDTO obterUltimoPedido() {
        String sql = "SELECT * FROM Pedido ORDER BY Id_Pedido DESC LIMIT 1";
        PedidoDTO ultimoPedido = null;

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                ultimoPedido = instanciarPedido(rs);
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao obter o último pedido: " + e.getMessage());
        }

        return ultimoPedido;
    }

    private PedidoDTO instanciarPedido(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(rs.getInt("Id_Pedido"));
        pedido.setCliente(new Cliente(DAOFactory.criarClienteDAO().buscarClientePorId(rs.getInt("Id_Cliente"))));
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

    private List<Integer> indicesServicoPorIdPedido(Integer idPedido) {
        String sql = "SELECT s.Id_Servico " +
                "FROM pedido AS P " +
                "LEFT JOIN cliente c ON p.Id_Cliente = c.Id_Cliente " +
                "LEFT JOIN servico s ON s.Id_Pedido = p.Id_Pedido " +
                "WHERE p.Id_Pedido = ?";

        List<Integer> servicos = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    servicos.add(rs.getInt("Id_Servico"));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao listar IDs de serviços: " + e);
        }

        return servicos;
    }


    private List<Integer> indicesServicoPorPedido() {
        String sql = "SELECT p.Id_Pedido, c.Id_Cliente, s.Id_Servico, c.Nome, s.Descricao, s.Preco, s.Tipo, p.Estado,p.`Data` " +
                "FROM pedido AS P " +
                "LEFT JOIN cliente c ON p.Id_Cliente = c.Id_Cliente " +
                "LEFT JOIN servico s ON s.Id_Pedido = p.Id_Pedido ";

        List<Integer> servicos = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                servicos.add(rs.getInt("Id_Servico"));
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao listar IDs de serviços: " + e);
        }

        return servicos;
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
