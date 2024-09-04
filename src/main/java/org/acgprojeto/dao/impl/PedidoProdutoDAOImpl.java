package org.acgprojeto.dao.impl;

import org.acgprojeto.dao.PedidoDAO;
import org.acgprojeto.dao.PedidoProdutoDAO;
import org.acgprojeto.dao.ProdutoDAO;
import org.acgprojeto.db.exceptions.DBException;
import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.dto.PedidoProdutoDTO;
import org.acgprojeto.model.entities.Pedido;
import org.acgprojeto.model.entities.PedidoProduto;
import org.acgprojeto.model.entities.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoProdutoDAOImpl implements PedidoProdutoDAO {

    private final Connection conexao;

    public PedidoProdutoDAOImpl(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void inserirPedidoProduto(PedidoProdutoDTO pedidoProdutoDTO) {
        PedidoProduto pedidoProduto = new PedidoProduto(pedidoProdutoDTO);

        String sql = "INSERT INTO pedido_possui_produto(Id_Pedido, Id_Produto, Preco, Quant) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, pedidoProduto.getPedido().getIdPedido());
            stmt.setInt(2, pedidoProduto.getProduto().getIdProduto());
            stmt.setBigDecimal(3, pedidoProduto.getPreco());
            stmt.setInt(4, pedidoProduto.getQuantidade());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas <= 0) {
                throw new DBException("Erro ao inserir linha");
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao inserir PedidoProduto: ");
        }
    }

    @Override
    public void atualizarPedidoProduto(PedidoProdutoDTO pedidoProdutoDTO) {
        PedidoProduto pedidoProduto = new PedidoProduto(pedidoProdutoDTO);
        String sql = "UPDATE pedido_possui_produto SET Id_Pedido = ?, Id_Produto = ?, Preco = ?, Quant = ? WHERE Id_Produto = ? AND Id_Pedido = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, pedidoProduto.getPedido().getIdPedido());
            stmt.setInt(2, pedidoProduto.getProduto().getIdProduto());
            stmt.setBigDecimal(3, pedidoProduto.getPreco());
            stmt.setInt(4, pedidoProduto.getQuantidade());
            stmt.setInt(5, pedidoProduto.getProduto().getIdProduto());
            stmt.setInt(6, pedidoProduto.getPedido().getIdPedido());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao atualizar PedidoProduto: ");
        }
    }

    @Override
    public void excluirPedidoProduto(Integer idPedido, Integer idProduto) {
        String sql = "DELETE FROM pedido_possui_produto WHERE Id_Produto = ? AND Id_Pedido = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idProduto);
            stmt.setInt(2, idPedido);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao excluir PedidoProduto de idPedido = " + idPedido + " e IdProduto = " + idProduto);
        }
    }

    @Override
    public PedidoProdutoDTO buscarPedidoProduto(Integer idPedido, Integer idProduto) {
        String sql = "SELECT * FROM pedido_possui_produto WHERE Id_Pedido = ? AND Id_Produto = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.setInt(2, idProduto);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return instanciarPedidoProduto(rs);
                }
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao buscar PedidoProduto de idPedido = " + idPedido + " e IdProduto = " + idProduto);
        }
        return null;
    }

    @Override
    public List<PedidoProdutoDTO> listarPedidoProduto() {
        String sql = "SELECT * FROM pedido_possui_produto";
        List<PedidoProdutoDTO> pedidoProdutos = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pedidoProdutos.add(instanciarPedidoProduto(rs));
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao listar PedidoProduto: ");
        }
        return pedidoProdutos;
    }

    public PedidoProdutoDTO obterUltimoPedidoProduto(){
        String sql = "SELECT * FROM pedido_possui_produto ORDER BY Id_Pedido DESC LIMIT 1";
        PedidoProdutoDTO ultimoPedidoProduto = null;

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                ultimoPedidoProduto = instanciarPedidoProduto(rs);
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao obter o último pedido relacionado ao produto: " + e.getMessage());
        }

        return ultimoPedidoProduto;

    }

    private PedidoProdutoDTO instanciarPedidoProduto(ResultSet rs) throws SQLException {
        PedidoProduto pedidoProduto = new PedidoProduto();
        PedidoDAO pedidoDAO = new PedidoDAOImpl(conexao);
        ProdutoDAO produtoDAO = new ProdutoDAOImpl(conexao);

        pedidoProduto.setPedido(new Pedido(pedidoDAO.buscarPedidoPorId(rs.getInt("Id_Pedido"))));
        pedidoProduto.setProduto(new Produto(produtoDAO.buscarProdutoPorId(rs.getInt("Id_Produto"))));
        pedidoProduto.setPreco(rs.getBigDecimal("Preco"));
        pedidoProduto.setQuantidade(rs.getInt("Quant"));

        return new PedidoProdutoDTO(pedidoProduto);
    }
}

