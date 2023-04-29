/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import sistemas.distribuidos.framesCliente.EscolherPorta;

/**
 *
 * @author User
 */
public class ConexaoServidor {
    
     public static void ConectarServidor() throws IOException{
    
        ServerSocket socketServidor = new ServerSocket(EscolherPorta.porta);
        
        Socket socketCliente = socketServidor.accept();
        
         System.out.println("Cliente conectado");
         
         InputStreamReader in = new InputStreamReader(socketCliente.getInputStream());
         
         BufferedReader bf = new BufferedReader(in);
         
         String mensagem = bf.readLine();
         System.out.println("Cliente: " + mensagem);

}
    
}
