package org.acgprojeto.application;


import org.acgprojeto.db.DB;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        Connection conexao = DB.getConexao();
        System.out.println("to aq");
        DB.fecharConexao();

    }
}