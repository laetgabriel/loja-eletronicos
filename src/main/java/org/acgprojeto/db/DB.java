package org.acgprojeto.db;

import org.acgprojeto.db.exceptions.DBException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection conexao = null;

    public static Connection getConexao() {
        if (conexao == null) {
            try{
                Properties props = carregarPropriedades();
                String url = props.getProperty("dburl");
                conexao = DriverManager.getConnection(url, props);

            } catch (SQLException e) {
                throw new DBException("Erro ao conectar ao Banco de Dados: " + e.getMessage());
            }

        }
        return conexao;
    }

    public static void fecharConexao() {
        try {
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException e) {
            throw new DBException("Erro ao fechar o Banco de Dados: " + e.getMessage());
        }
    }

    private static Properties carregarPropriedades(){
        try(FileInputStream fs = new FileInputStream("db.properties")){
            Properties prop = new Properties();
            prop.load(fs);
            return prop;
        }catch (IOException e){
            throw new DBException("Erro ao abrir o arquivo db.properties");
        }
    }

    public static void fecharStatement(Statement st){
        if(st != null){
            try {
                st.close();
            } catch (SQLException e) {
                throw new DBException("Erro ao fechar o statement: " + e.getMessage());
            }
        }
    }

    public static void fecharResultSet(ResultSet rs){
        if(rs != null){
            try{
                rs.close();
            } catch (SQLException e) {
                throw new DBException("Erro ao fechar o ResultSet: " + e.getMessage());
            }
        }
    }
}
