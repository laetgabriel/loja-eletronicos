package org.acgprojeto.db;

import org.acgprojeto.db.exceptions.DBException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection conexao = null;

    private DB(){}

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

    private static Properties carregarPropriedades(){
        try(FileInputStream fs = new FileInputStream("db.properties")){
            Properties prop = new Properties();
            prop.load(fs);
            return prop;
        }catch (IOException e){
            throw new DBException("Erro ao abrir o arquivo db.properties");
        }
    }

}
