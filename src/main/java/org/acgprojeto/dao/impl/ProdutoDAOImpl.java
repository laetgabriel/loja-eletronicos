package org.acgprojeto.dao.impl;

import org.acgprojeto.dao.ProdutoDAO;
import org.acgprojeto.db.DB;
import org.acgprojeto.db.exceptions.DBException;
import org.acgprojeto.dto.ProdutoDTO;
import org.acgprojeto.model.entidades.Produto;
import org.acgprojeto.model.enums.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAOImpl implements ProdutoDAO {

    private final Connection conexao;

    public ProdutoDAOImpl(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void inserirProduto(ProdutoDTO produtoDTO) {
        Produto produto = Produto.ProdutoBuilder.aProduto()
                .nomeProduto(produtoDTO.getNomeProduto())
                .preco(produtoDTO.getPreco())
                .categoria(produtoDTO.getCategoria())
                .quantidadeEstoque(produtoDTO.getQuantidadeEstoque())
                .build();

        String sql = "INSERT INTO Produto(Nome, Categoria, Preco, Quant_Estoque) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getCategoria().toString());
            stmt.setBigDecimal(3, produto.getPreco());
            stmt.setInt(4, produto.getQuantidadeEstoque());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        produto.setIdProduto(id);
                    }
                }
            } else {
                throw new DBException("Erro ao inserir linha");
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao inserir Produto: ");
        }
    }

    @Override
    public void atualizarProduto(ProdutoDTO produtoDTO) {
        String sql = "UPDATE produto SET Nome = ?, Categoria = ?, Preco = ?, Quant_Estoque = ? WHERE Id_Produto = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, produtoDTO.getNomeProduto());
            stmt.setString(2, produtoDTO.getCategoria().toString());
            stmt.setBigDecimal(3, produtoDTO.getPreco());
            stmt.setInt(4, produtoDTO.getQuantidadeEstoque());
            stmt.setInt(5, produtoDTO.getIdProduto());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao atualizar Produto: ");
        }
    }

    @Override
    public void excluirProduto(Integer id) {
        String sql = "DELETE FROM produto WHERE Id_Produto = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao excluir Produto de ID = " + id);
        }
    }

    @Override
    public ProdutoDTO buscarProdutoPorId(Integer id) {
        String sql = "SELECT * FROM produto WHERE Id_Produto = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return instanciarProduto(rs);
                }
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao buscar Produto de ID = " + id);
        }
        return null;
    }

    @Override
    public List<ProdutoDTO> listarTodosOsProdutos() {
        String sql = "SELECT * FROM produto";
        List<ProdutoDTO> produtos = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                produtos.add(instanciarProduto(rs));
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao listar Produtos: ");
        }
        return produtos;
    }

    private ProdutoDTO instanciarProduto(ResultSet rs) throws SQLException {
        String categoriaStr = rs.getString("Categoria");
        Categoria categoria = Categoria.valueOf(categoriaStr.toUpperCase());
        return ProdutoDTO.ProdutoDTOBuilder.aProdutoDTO()
                .idProduto(rs.getInt("Id_Produto"))
                .nomeProduto(rs.getString("Nome"))
                .categoria(categoria)
                .preco(rs.getBigDecimal("Preco"))
                .quantidadeEstoque(rs.getInt("Quant_Estoque"))
                .build();

    }
}
