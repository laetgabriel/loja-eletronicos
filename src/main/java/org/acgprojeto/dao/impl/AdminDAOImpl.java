package org.acgprojeto.dao.impl;

import org.acgprojeto.dao.AdminDAO;
import org.acgprojeto.db.exceptions.DBException;
import org.acgprojeto.dto.AdminDTO;
import org.acgprojeto.model.entities.Admin;

import java.sql.*;

public class AdminDAOImpl implements AdminDAO {

    private final Connection conexao;

    public AdminDAOImpl(Connection connection) {
        this.conexao = connection;
    }


    @Override
    public void inserirAdmin(AdminDTO adminDTO) {
        Admin admin = new Admin(adminDTO);
        String sql = "insert into admin (nome, email, senha) values (?,?, ?)";

        try(PreparedStatement st = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            st.setString(1, admin.getNome());
            st.setString(2, admin.getEmail());
            st.setString(3, admin.getSenha());

            int linhasAfetadas = st.executeUpdate();
            if(linhasAfetadas > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    admin.setId(rs.getInt(1));
                }
            }else
                throw new DBException("Erro ao inserir linha");

        }catch (SQLException e){
            throw new DBException("Erro ao inserir Admin");
        }

    }

    @Override
    public void atualizarAdmin(AdminDTO adminDTO) {
        String sql = "update admin set nome = ?, email = ? where Id_Admin = ?";
        try(PreparedStatement st = conexao.prepareStatement(sql)){

            st.setString(1, adminDTO.getNome());
            st.setString(2, adminDTO.getEmail());
            st.setInt(3, adminDTO.getId());
            st.executeUpdate();

        }catch (SQLException e){
            throw new DBException("Erro ao atualizar Admin");
        }
    }

    @Override
    public void excluirAdmin(Integer id) {
        String sql = "delete from admin where Id_Admin = ?";

        try(PreparedStatement st = conexao.prepareStatement(sql)){

            st.setInt(1, id);
            st.executeUpdate();

        }catch(SQLException e){
            throw new DBException("Erro ao excluir Admin");
        }
    }

    @Override
    public AdminDTO buscarAdminPorId(Integer idAdmin) {
        String sql = "select * from admin where Id_Admin = ?";
        AdminDTO admin;

        try(PreparedStatement st = conexao.prepareStatement(sql)){
            st.setInt(1, idAdmin);

            try(ResultSet resultSet = st.executeQuery()){
                if (resultSet.next()) {
                    admin = instaciarAdmin(resultSet);
                }else
                    throw new DBException("Admin não encontrado");
            }
        }catch (SQLException e){
            throw new DBException("Erro ao buscar admin com ID = " + idAdmin);
        }
        return admin;
    }

    private AdminDTO instaciarAdmin(ResultSet rs) throws SQLException {
        return new AdminDTO(
                rs.getInt("Id_Admin"),
                rs.getString("Nome"),
                rs.getString("Email"),
                rs.getString("Senha")
        );
    }

}
