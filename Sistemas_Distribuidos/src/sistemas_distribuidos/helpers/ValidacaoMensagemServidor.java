/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemas_distribuidos.helpers;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author User
 */
public class ValidacaoMensagemServidor extends Thread {

    private String email;
    private String senha;
    private String nome;
    private Integer operacao;
    private JSONObject mensagemFinal;

    public ValidacaoMensagemServidor(String email, String senha, String nome, Integer operacao, JSONObject mensagemFinal) {

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
                case 9:
                {
                    try {
                        validacaoOperacaoLogout(mensagemFinal);
                    } catch (JSONException ex) {
                        Logger.getLogger(ValidacaoMensagemServidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

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

    public static Boolean validacaoOperacaoLogout(JSONObject mensagem) throws JSONException {

        String token = mensagem.getString("token");
        Integer id = mensagem.getInt("id");
        Integer operacao = mensagem.getInt("operacao");

        if (token.length() > 0 && id > 0 && operacao == 9) {
            return true;
        }

        return false;

    }

}
