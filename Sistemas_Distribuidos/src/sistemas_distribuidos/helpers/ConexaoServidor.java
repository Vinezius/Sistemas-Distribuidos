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
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sistemas_distribuidos.entidades.Incidente;
import sistemas_distribuidos.framesServidor.ListarClientes;

/**
 *
 * @author User
 */
public class ConexaoServidor {

    static ServerSocket socketServidor;
    static String[] clientesConectados = new String[5];
    static Integer count = 0;
    static ListarClientes cliente = new ListarClientes();
    
    public static void preencherClientesConectados(String[] clienteConectados){
        cliente.preencherLista(clienteConectados);
        
    }

    public static void ConectarCliente(Integer porta) throws JSONException {

        try {
            socketServidor = new ServerSocket(porta, 5);

            System.out.println("Ouvindo na porta: " + porta);

            while (true) {
                try {
                    Socket socketCliente = socketServidor.accept();
                    try {
                        clientesConectados[count] = socketCliente + "";
                        count++;

                        for (int i = 0; i < clientesConectados.length; i++) {
                            preencherClientesConectados(clientesConectados);
                        }

                        System.out.println("Cliente conectado: " + socketCliente);

                        InputStreamReader in = new InputStreamReader(socketCliente.getInputStream());

                        BufferedReader bf = new BufferedReader(in);

                        String mensagem = bf.readLine();

                        System.out.println("Mensagem recebida: " + mensagem);
                        JSONObject mensagemFinal = new JSONObject(mensagem);
                        Integer operacao = mensagemFinal.getInt("operacao");

                        System.out.println("Operacação: " + operacao);

                        switch (operacao) {
                            case 1 -> {
                                String email = mensagemFinal.getString("email");
                                String senha = mensagemFinal.getString("senha");
                                String nome = mensagemFinal.getString("nome");
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
                                String email = mensagemFinal.getString("email");
                                String senha = mensagemFinal.getString("senha");
                                String[] dados;

                                dados = ValidacaoMensagemServidor.validacaoOperacaoLogin(email, senha);

                                if (dados != null) {
                                    PrintWriter pr = new PrintWriter(socketCliente.getOutputStream());
                                    JSONObject json = new JSONObject();
                                    String idString = dados[0];
                                    Integer id = Integer.parseInt(idString);
                                    String token = CriarToken.autenticarUsuario(id);
                                    String nome = dados[1];

                                    json.put("operacao", operacao);
                                    json.put("status", "OK");
                                    json.put("token", token);
                                    json.put("id", id);
                                    json.put("nome", nome);

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
                            case 3 -> {
                                String email = mensagemFinal.getString("email");
                                String senha = mensagemFinal.getString("senha");
                                String nome = mensagemFinal.getString("nome");
                                String token = mensagemFinal.getString("token");
                                Integer id = mensagemFinal.getInt("id");
                                Boolean resultado = ValidacaoMensagemServidor.validarAlteracaoCadastro(token, id, nome, senha, email);
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
                            case 4 -> {
                                String cidade = mensagemFinal.getString("cidade");
                                String estado = mensagemFinal.getString("estado");
                                String data = mensagemFinal.getString("data");
                                Boolean dataValidada = FormatarDataHora.validarData(data);
                                PrintWriter pr = new PrintWriter(socketCliente.getOutputStream());
                                if (dataValidada) {
                                    List<Incidente> listaIncidentes = ValidacaoMensagemServidor.validacaoBuscaIncidentes(data, cidade, estado);
                                    JSONObject json = new JSONObject();
                                    json.put("operacao", operacao);
                                    json.put("status", "OK");
                                    if (listaIncidentes.isEmpty()) {
                                        JSONArray jsonArrayIncidentes = new JSONArray();
                                        json.put("incidentes", jsonArrayIncidentes);
                                        pr.println(json);
                                        pr.flush();

                                    } else {
                                        JSONArray jsonArrayIncidentes = new JSONArray();
                                        for (Incidente incidente : listaIncidentes) {
                                            JSONObject jsonIncidente = new JSONObject();
                                            jsonIncidente.put("tipo_incidente", incidente.getTipoIncidente());
                                            jsonIncidente.put("data", incidente.getData());
                                            jsonIncidente.put("hora", incidente.getHora());
                                            jsonIncidente.put("cidade", incidente.getCidade());
                                            jsonIncidente.put("bairro", incidente.getBairro());
                                            jsonIncidente.put("rua", incidente.getRua());
                                            jsonIncidente.put("estado", incidente.getEstado());
                                            jsonIncidente.put("id", incidente.getId());

                                            jsonArrayIncidentes.put(jsonIncidente);
                                        }
                                        json.put("incidentes", jsonArrayIncidentes);
                                        pr.println(json);
                                        pr.flush();
                                    }
                                } else {
                                    JSONObject json = new JSONObject();
                                    json.put("operacao", operacao);
                                    json.put("status", "Erro Generico");
                                    pr.println(json);
                                    pr.flush();
                                }

                            }
                            case 7 -> {
                                String data = mensagemFinal.getString("data");
                                String cidade = mensagemFinal.getString("cidade");
                                String bairro = mensagemFinal.getString("bairro");
                                String estado = mensagemFinal.getString("estado");
                                String tipoIncidente = mensagemFinal.getString("tipo incidente");
                                String rua = mensagemFinal.getString("rua");
                                String hora = mensagemFinal.getString("hora");
                                Integer id = mensagemFinal.getInt("id");
                                Boolean resultado = ValidacaoMensagemServidor.validacaoCadastroIncidente(data, cidade, bairro, estado, tipoIncidente, rua, hora, id);
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

                            case 9 -> {

                                Boolean validaMensagem;
                                validaMensagem = ValidacaoMensagemServidor.validacaoOperacaoLogout(mensagemFinal);

                                if (validaMensagem) {
                                    Integer idUsuario = mensagemFinal.getInt("id");
                                    CriarToken.removerToken(idUsuario);
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
                            default -> {
                                bf.close();
                            }

                        }

                    } catch (IOException e) {
                        System.out.println("Erro de conexão");;
                    }
                } catch (IOException e) {
                    System.out.println("Conexão não aceita");
                }
            }
        } catch (IOException e) {
            System.out.println("Não foi possivel abrir a porta:" + porta);
        } finally {
            try {
                socketServidor.close();
            } catch (IOException e) {
                System.out.println("Não foi possivel fechar o servidor");
                System.exit(1);
            }
        }

    }

    public static void main(String[] args) throws IOException, JSONException {
        Scanner input = new Scanner(System.in);
        System.out.println("Escolha uma porta para conectar: ");
        Integer porta = input.nextInt();
        ConectarCliente(porta);
    }

}
