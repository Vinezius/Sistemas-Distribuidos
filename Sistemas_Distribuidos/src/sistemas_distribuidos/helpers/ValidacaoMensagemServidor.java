/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemas_distribuidos.helpers;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import sistemas_distribuidos.entidades.Incidente;

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
                case 9: {
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

    private static Boolean validarUsuario(Integer id) {

        String url = "jdbc:mysql://localhost:3306/sistemas_distribuidos";
        String usuario = "root";
        String senhaBanco = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexao = DriverManager.getConnection(url, usuario, senhaBanco);

            Statement statement = conexao.createStatement();
            String sql = "SELECT * FROM usuario WHERE IDUsuario = ";
            ResultSet resultadosQuery = statement.executeQuery(sql + id);

            if (resultadosQuery.next()) {
                conexao.close();
                return true;

            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public static Boolean validarAlteracaoCadastro(String token, Integer id, String nome, String senha, String email) {

        String emailParse[] = email.split("@");
        String url = "jdbc:mysql://localhost:3306/sistemas_distribuidos";
        String usuario = "root";
        String senhaBanco = "";
        Boolean usuarioExiste = validarUsuario(id);

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

            if (usuarioExiste) {
                String sqlEdicaoDados = "UPDATE `Usuario` SET Nome = '" + nome + "', Email = '" + email + "', Senha = '" + senha + "' WHERE IDUsuario = " + id;
                System.out.println(sqlEdicaoDados);
                statement.executeUpdate(sqlEdicaoDados);
                conexao.close();
                return true;

            } else {

                return false;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

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

    public static List<Incidente> validacaoBuscaIncidentes(String data, String cidade, String estado) {
        List<Incidente> incidentes = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/sistemas_distribuidos";
        String usuario = "root";
        String senhaBanco = "";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexao = DriverManager.getConnection(url, usuario, senhaBanco);
            Statement statement = conexao.createStatement();
            String sql = "SELECT * FROM incidentes WHERE `Data_Incidente` LIKE '" + data + "' AND `Cidade` LIKE '" + cidade + "' AND `Estado` LIKE '" + estado + "'";
            System.out.println(sql);
            ResultSet resultadosQuery = statement.executeQuery(sql);

            while (resultadosQuery.next()) {
                Integer tipoIncidente = resultadosQuery.getInt("Tipo_Incidente");
                String hora = resultadosQuery.getString("Hora_Incidente");
                String rua = resultadosQuery.getString("Rua");
                String bairro = resultadosQuery.getString("Bairro");
                int idUser = resultadosQuery.getInt("IDUsuario");
                int idIncidente = resultadosQuery.getInt("IDIncidente");

                Incidente incidente = new Incidente(tipoIncidente, data, hora, cidade, bairro, rua, estado, idUser);
                incidente.setId(idIncidente);
                incidentes.add(incidente);

            }

            return incidentes;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return incidentes;

    }

    public static List<Incidente> validacaoBuscaIncidentesUsuario(Integer id) {
        List<Incidente> incidentes = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/sistemas_distribuidos";
        String usuario = "root";
        String senhaBanco = "";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexao = DriverManager.getConnection(url, usuario, senhaBanco);
            Statement statement = conexao.createStatement();
            String sql = "SELECT * FROM incidentes WHERE `IDUsuario` LIKE '" + id + "'";
            System.out.println(sql);
            ResultSet resultadosQuery = statement.executeQuery(sql);

            while (resultadosQuery.next()) {
                Integer tipoIncidente = resultadosQuery.getInt("Tipo_Incidente");
                String hora = resultadosQuery.getString("Hora_Incidente");
                String rua = resultadosQuery.getString("Rua");
                String bairro = resultadosQuery.getString("Bairro");
                String data = resultadosQuery.getString("Data_Incidente");
                String cidade = resultadosQuery.getString("Cidade");
                String estado = resultadosQuery.getString("Estado");
                int idUser = resultadosQuery.getInt("IDUsuario");
                int idIncidente = resultadosQuery.getInt("IDIncidente");

                Incidente incidente = new Incidente(tipoIncidente, data, hora, cidade, bairro, rua, estado, idUser);
                incidente.setId(idIncidente);
                incidentes.add(incidente);

            }

            return incidentes;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return incidentes;

    }

    public static Boolean validacaoExcluirIncidente(Integer id_incidente) {

        String url = "jdbc:mysql://localhost:3306/sistemas_distribuidos";
        String usuario = "root";
        String senhaBanco = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexao = DriverManager.getConnection(url, usuario, senhaBanco);

            Statement statement = conexao.createStatement();
            String sql = "Delete from `Incidentes` where `IDIncidente` = " + id_incidente;
            System.out.println(sql);
            statement.execute(sql);
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public static Boolean validacaoCadastroIncidente(String data, String cidade, String bairro, String estado, Integer tipo_incidente, String rua, String hora, Integer id) {
        Boolean dataValidada = FormatarDataHora.validarData(data);
        String url = "jdbc:mysql://localhost:3306/sistemas_distribuidos";
        String usuario = "root";
        String senhaBanco = "";

        if (data.isEmpty() || cidade.isEmpty() || bairro.isEmpty() || estado.isEmpty() || tipo_incidente == null || rua.isEmpty() || hora.isEmpty()) {
            return false;

        } else if (!dataValidada) {
            return false;
        } else if (rua.length() > 50 || cidade.length() > 50 || bairro.length() > 50) {
            return false;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexao = DriverManager.getConnection(url, usuario, senhaBanco);
            Statement statement = conexao.createStatement();
            String sqlCadastroIncidente = "INSERT INTO `incidentes` (`IDIncidente`,`Data_Incidente`,`Hora_Incidente`,`Estado`,`Cidade`,`Bairro`,`Tipo_Incidente`, `IDUsuario`, `Rua`) "
                    + "VALUES (" + null + ",'" + data + "','" + hora + "','" + estado + "','" + cidade + "','" + bairro + "','" + tipo_incidente + "','" + id + "','" + rua + "')";
            System.out.println(sqlCadastroIncidente);
            statement.executeUpdate(sqlCadastroIncidente);
            conexao.close();
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public static Boolean validacaoOperacaoExclusaoCadastro(String token, Integer id, String senha) {
        String url = "jdbc:mysql://localhost:3306/sistemas_distribuidos";
        String usuario = "root";
        String senhaBanco = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexao = DriverManager.getConnection(url, usuario, senhaBanco);
            Statement statement = conexao.createStatement();
            String sqlExclusaoCadastro = "DELETE FROM`usuario` WHERE `IDUsuario` LIKE '" + id + "' AND `Senha` like '"+ senha +"'";
            System.out.println(sqlExclusaoCadastro);
            statement.executeUpdate(sqlExclusaoCadastro);
            conexao.close();
            return true;

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
