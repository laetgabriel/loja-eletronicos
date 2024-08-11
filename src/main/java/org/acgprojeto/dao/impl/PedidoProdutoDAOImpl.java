package org.acgprojeto.dao.impl;

import org.acgprojeto.dao.PedidoDAO;
import org.acgprojeto.dao.PedidoProdutoDAO;
import org.acgprojeto.db.DB;
import org.acgprojeto.db.exceptions.DBException;
import org.acgprojeto.dto.PedidoProdutoDTO;
import org.acgprojeto.dto.ProdutoDTO;
import org.acgprojeto.model.entidades.Cliente;
import org.acgprojeto.model.entidades.Pedido;
import org.acgprojeto.model.entidades.PedidoProduto;
import org.acgprojeto.model.entidades.Produto;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoProdutoDAOImpl implements PedidoProdutoDAO {

    private Connection conexao;

    public PedidoProdutoDAOImpl(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void inserirPedidoProduto(PedidoProdutoDTO pedidoProdutoDTO) {
        PedidoProduto pedidoProduto = new PedidoProduto(pedidoProdutoDTO);
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            stmt = conexao.prepareStatement(
                    "INSERT INTO pedido_possui_produto(Id_Pedido, Id_Produto, Preco, Quant) " +
                            "values (?, ?, ?, ?)"
            );
            stmt.setInt(1, pedidoProduto.getPedido().getIdPedido());
            stmt.setInt(2, pedidoProduto.getProduto().getIdProduto());
            stmt.setBigDecimal(3, pedidoProduto.getPreco());
            stmt.setInt(4, pedidoProduto.getQuantidade());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas <= 0) {
                throw new DBException("Erro ao inserir linha");
            }

        }catch(SQLException e){
            throw new DBException("Erro ao inserir PedidoProduto");
        }finally {
            DB.fecharStatement(stmt);
            DB.fecharResultSet(rs);
        }
    }

    @Override
    public void atualizarPedidoProduto(PedidoProdutoDTO pedidoProdutoDTO) {
        PedidoProduto pedidoProduto = new PedidoProduto(pedidoProdutoDTO);
        PreparedStatement stmt = null;

        try {
            stmt = conexao.prepareStatement(
                    "update pedido_possui_produto set Id_Pedido = ?, Id_Produto  = ?, Preco = ?, Quant = ?" +
                            " where pedido_possui_produto.Id_Produto = ? and " +
                            "pedido_possui_produto.Id_Pedido = ?"
            );
            stmt.setInt(1, pedidoProduto.getPedido().getIdPedido());
            stmt.setInt(2, pedidoProduto.getProduto().getIdProduto());
            stmt.setBigDecimal(3, pedidoProduto.getPreco());
            stmt.setInt(4, pedidoProduto.getQuantidade());
            stmt.setInt(5, pedidoProduto.getProduto().getIdProduto());
            stmt.setInt(6, pedidoProduto.getPedido().getIdPedido());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DBException("Erro ao atualizar PedidoProduto");
        } finally {
            DB.fecharStatement(stmt);
        }
    }

    @Override
    public void excluirPedidoProduto(Integer id_pedido, Integer id_produto) {
        PreparedStatement stmt = null;
        try {
            stmt = conexao.prepareStatement(
                    "delete from pedido_possui_produto where pedido_possui_produto.Id_Produto = ? and " +
                    "pedido_possui_produto.Id_Pedido = ?"
            );

            stmt.setInt(1, id_produto);
            stmt.setInt(2, id_pedido);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao excluir PedidoProduto de idPedido = " + id_pedido + " e IdProduto = " + id_produto);
        } finally {
            DB.fecharStatement(stmt);
        }
    }

    @Override
    public PedidoProdutoDTO buscarPedidoProduto(Integer id_pedido, Integer id_produto) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            stmt = conexao.prepareStatement(
                    "select * from pedido_possui_produto as pp " +
                        "where pp.Id_Pedido = ? and pp.Id_Produto = ?"
            );

            stmt.setInt(1, id_pedido);
            stmt.setInt(2, id_produto);

            rs = stmt.executeQuery();

            if(rs.next()) {
                return instanciarPedidoProduto(rs);
            }

        }catch(SQLException e){
            throw new DBException("Erro ao buscar PedidoProduto de idPedido = " + id_pedido + " e IdProduto = " + id_produto);
        }finally {
            DB.fecharStatement(stmt);
            DB.fecharResultSet(rs);
        }
        return null;
    }

    @Override
    public List<PedidoProdutoDTO> listarPedidoProduto() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            stmt = conexao.prepareStatement(
                    "select * from pedido_possui_produto"
            );

            rs = stmt.executeQuery();

            List<PedidoProdutoDTO> pedidoProdutos = new ArrayList<>();

            while (rs.next()){
                pedidoProdutos.add(instanciarPedidoProduto(rs));
            }

            return pedidoProdutos;

        }catch(SQLException e){
            throw new DBException("Erro ao listar PedidoProduto");
        }finally {
            DB.fecharStatement(stmt);
            DB.fecharResultSet(rs);
        }
    }

    private PedidoProdutoDTO instanciarPedidoProduto(ResultSet rs) throws SQLException {
        PedidoProduto pedidoProduto = new PedidoProduto();
        pedidoProduto.setPedido(new Pedido(new PedidoDAOImpl(conexao).buscarPedidoPorId(rs.getInt("Id_Pedido"))));
        pedidoProduto.setProduto(new Produto(new ProdutoDAOImpl(conexao).buscarProdutoPorId(rs.getInt("Id_Produto"))));
        pedidoProduto.setPreco(rs.getBigDecimal("Preco"));
        pedidoProduto.setQuantidade(rs.getInt("Quant"));
        return new PedidoProdutoDTO(pedidoProduto);
    }
}
