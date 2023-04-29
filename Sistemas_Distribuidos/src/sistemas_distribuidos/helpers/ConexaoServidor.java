/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemas_distribuidos.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author User
 */
public class ConexaoServidor {

    public static void main(String[] args) throws IOException, JSONException {
        Scanner input = new Scanner(System.in);
        System.out.println("Escolha uma porta para conectar: ");
        Integer porta = input.nextInt();
        ConectarCliente(porta);
    }

    public static void ConectarCliente(Integer porta) throws IOException, JSONException {

        ServerSocket socketServidor = new ServerSocket(porta);

        Socket socketCliente = socketServidor.accept();

        System.out.println("Cliente conectado");

        InputStreamReader in = new InputStreamReader(socketCliente.getInputStream());

        BufferedReader bf = new BufferedReader(in);

        String mensagem = bf.readLine();
        System.out.println("Mensagem recebida: " + mensagem);

        JSONObject mensagemFinal = new JSONObject(mensagem);
        Integer operacao = mensagemFinal.getInt("operacao");
        String email = mensagemFinal.getString("email");
        String senha = mensagemFinal.getString("senha");
        String nome = mensagemFinal.getString("nome");

        System.out.println("Operacação: " + operacao);

        switch (operacao) {
            case 1 -> {
                Boolean resultado = ValidacaoMensagemServidor.validacaoCadastro(email, senha, nome);
                if (resultado) {
                    PrintWriter pr = new PrintWriter(socketCliente.getOutputStream());
                    JSONObject json = new JSONObject();
                    json.put("operacao", operacao);
                    json.put("status", "OK");
                    pr.println(json);
                    pr.flush();
                } else {
                    PrintWriter pr = new PrintWriter(socketCliente.getOutputStream());
                    JSONObject json = new JSONObject();
                    json.put("operacao", operacao);
                    json.put("status", "Erro Generico");
                    pr.println(json);
                    pr.flush();
                }
            }
            case 2 -> {
                String[] dados;

                dados = ValidacaoMensagemServidor.validacaoOperacaoLogin(email, senha);
                if (dados != null) {
                    PrintWriter pr = new PrintWriter(socketCliente.getOutputStream());
                    JSONObject json = new JSONObject();
                    String token = CriarToken.gerarToken();

                    String idString = dados[0];
                    Integer id = Integer.parseInt(idString);
                    json.put("operacao", operacao);
                    json.put("status", "OK");
                    json.put("token", token);
                    json.put("id", id);
                    pr.println(json);
                    pr.flush();
                } else {
                    PrintWriter pr = new PrintWriter(socketCliente.getOutputStream());
                    JSONObject json = new JSONObject();
                    json.put("operacao", operacao);
                    json.put("status", "Erro Generico");
                    pr.println(json);
                    pr.flush();
                }
            }

        }

    }

}
