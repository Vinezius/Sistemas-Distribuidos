/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemas_distribuidos.helpers;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

/**
 *
 * @author User
 */
public class ValidacaoMensagemServidor extends Thread {

    private String email;
    private String senha;
    private String nome;
    private Integer operacao;

    public ValidacaoMensagemServidor(String email, String senha, String nome,Integer operacao) {

        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.operacao = operacao;
        
    }

    public void run() {
        while (true) {
            switch (operacao) {
                case 1:
                    validacaoCadastro(email, senha, nome);
                case 2:
                    validacaoOperacaoLogin(email, senha);
            }
        }

    }

    public static String[] validacaoOperacaoLogin(String email, String senha) {
        String emailParse[] = email.split("@");
        String url = "jdbc:mysql://localhost:3306/sistemas_distribuidos";
        String usuario = "root";
        String senhaBanco = "";

        if (email.isEmpty() || senha.isEmpty()) {
            return null;

        } else if (!email.contains("@") || emailParse[0].length() > 50 || emailParse[1].length() > 10
                || emailParse[0].length() < 3 || emailParse[1].length() < 3) {
            return null;

        } else if (senha.length() < 5 || senha.length() > 10) {

            return null;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexao = DriverManager.getConnection(url, usuario, senhaBanco);

            Statement statement = conexao.createStatement();
            String sql = "SELECT * FROM usuario WHERE Email = ";
            System.out.println(sql + "'" + email + "' AND senha = " + "'" + senha + "'");
            ResultSet resultadosQuery = statement.executeQuery(sql + "'" + email + "' AND senha = " + "'" + senha + "'");

            String[] dados = new String[2];

            if (resultadosQuery.next()) {
                dados[0] = resultadosQuery.getString("IDUsuario");
                dados[1] = resultadosQuery.getString("Nome");
                conexao.close();
                return dados;
            }
            conexao.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    public static Boolean validacaoCadastro(String email, String senha, String nome) {
        String emailParse[] = email.split("@");
        String url = "jdbc:mysql://localhost:3306/sistemas_distribuidos";
        String usuario = "root";
        String senhaBanco = "";

        if (email.isEmpty() || senha.isEmpty()) {
            return false;

        } else if (!email.contains("@") || emailParse[0].length() > 50 || emailParse[1].length() > 10
                || emailParse[0].length() < 3 || emailParse[1].length() < 3) {
            return false;

        } else if (senha.length() < 5 || senha.length() > 10) {

            return false;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexao = DriverManager.getConnection(url, usuario, senhaBanco);

            Statement statement = conexao.createStatement();
            String sql = "SELECT * FROM usuario WHERE Email = ";
            System.out.println(sql + "'" + email + "'");
            ResultSet resultadosQuery = statement.executeQuery(sql + "'" + email + "'");

            if (resultadosQuery.next()) {
                conexao.close();
                return false;
            } else {
                String sqlCadastro = "INSERT INTO `usuario` (`IDUsuario`,`Nome`, `Email`, `Senha`) VALUES (" + null + ",'" + nome + "','" + email + "','" + senha + "')";
                System.out.println(sqlCadastro);
                statement.executeUpdate(sqlCadastro);
                conexao.close();
                return true;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
