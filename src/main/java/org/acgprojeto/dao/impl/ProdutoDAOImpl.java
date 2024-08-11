
package org.acgprojeto.dao.impl;

import org.acgprojeto.dao.ProdutoDAO;
import org.acgprojeto.db.DB;
import org.acgprojeto.db.exceptions.DBException;
import org.acgprojeto.dto.ProdutoDTO;
import org.acgprojeto.model.entidades.Cliente;
import org.acgprojeto.model.entidades.Produto;
import org.acgprojeto.model.enums.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAOImpl implements ProdutoDAO {

    private Connection conexao;

    public ProdutoDAOImpl(Connection conexao) {
        this.conexao = conexao;
    }


    @Override
    public void inserirProduto(ProdutoDTO produtoDTO) {
        Produto produto = new Produto(produtoDTO);
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conexao.prepareStatement(
                    "INSERT INTO Produto(Nome, Categoria, Preco, Quant_Estoque) " +
                            "values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getCategoria().toString());
            stmt.setBigDecimal(3, produto.getPreco());
            stmt.setInt(4, produto.getQuantidadeEstoque());


            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    produto.setIdProduto(id);
                }
            } else
                throw new DBException("Erro ao inserir linha");
        } catch (SQLException e) {
            throw new DBException("Erro ao inserir Produto");
        } finally {
            DB.fecharStatement(stmt);
            DB.fecharResultSet(rs);
        }
    }

    @Override
    public void atualizarProduto(ProdutoDTO produto) {
        PreparedStatement stmt = null;

        try {
            stmt = conexao.prepareStatement(
                    "update produto set Nome = ?, Categoria = ?, Preco = ?, Quant_Estoque = ?" +
                            " where produto.Id_Produto = ?"
            );
            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getCategoria().toString());
            stmt.setBigDecimal(3, produto.getPreco());
            stmt.setInt(4, produto.getQuantidadeEstoque());
            stmt.setInt(5, produto.getIdProduto());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DBException("Erro ao atualizar Produto ");
        } finally {
            DB.fecharStatement(stmt);
        }
    }

    @Override
    public void excluirProduto(Integer id) {
        PreparedStatement stmt = null;
        try {
            stmt = conexao.prepareStatement(
                    "delete from produto where produto.Id_Produto = ?"
            );

            stmt.setInt(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao excluir Produto de ID = " + id);
        } finally {
            DB.fecharStatement(stmt);
        }
    }

    @Override
    public ProdutoDTO buscarProdutoPorId(Integer id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conexao.prepareStatement(
                    "SELECT * FROM produto WHERE produto.Id_Produto = ?"
            );

            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return instanciarProduto(rs);
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao buscar Produto de ID = " + id);
        } finally {
            DB.fecharStatement(stmt);
            DB.fecharResultSet(rs);
        }
        return null;
    }

    @Override
    public List<ProdutoDTO> listarTodosOsProdutos() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            stmt = conexao.prepareStatement(
                    "select * from produto"
            );
            rs = stmt.executeQuery();
            List<ProdutoDTO> produtos = new ArrayList<>();

            while (rs.next()){
                produtos.add(instanciarProduto(rs));
            }
            return produtos;
        }catch(SQLException e){
            throw new DBException("Erro ao listar Produtos");
        }finally {
            DB.fecharStatement(stmt);
            DB.fecharResultSet(rs);
        }
    }

    private ProdutoDTO instanciarProduto(ResultSet rs) throws SQLException {
        Produto produto = new Produto();
        produto.setIdProduto(rs.getInt("Id_Produto"));
        produto.setNomeProduto(rs.getString("Nome"));
        String categoriaStr = rs.getString("Categoria");
        Categoria categoria = Categoria.valueOf(categoriaStr.toUpperCase());
        produto.setCategoria(categoria);
        produto.setPreco(rs.getBigDecimal("Preco"));
        produto.setQuantidadeEstoque(rs.getInt("Quant_Estoque"));
        return new ProdutoDTO(produto);
    }
}
