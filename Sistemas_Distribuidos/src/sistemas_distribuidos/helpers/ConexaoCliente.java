/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemas_distribuidos.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.json.JSONException;
import org.json.JSONObject;
import sistemas_distribuidos.framesCliente.EscolherPorta;

/**
 *
 * @author User
 */
public class ConexaoCliente {

    public static String ConectarServidor(JSONObject mensagem) throws IOException, JSONException {

        Integer porta = EscolherPorta.porta;
        System.out.println("Porta do cliente: " + porta);

        Socket socketCliente = new Socket("localhost", porta);

        PrintWriter pr = new PrintWriter(socketCliente.getOutputStream());

        pr.println(mensagem);
        pr.flush();

        InputStreamReader in = new InputStreamReader(socketCliente.getInputStream());

        BufferedReader bf = new BufferedReader(in);

        String mensagemRecebida = bf.readLine();
        System.out.println("Mensagem recebida: " + mensagemRecebida);
        
        JSONObject mensagemFinal = new JSONObject(mensagemRecebida);
        String status = mensagemFinal.getString("status");
        
        return status;

    }

}
