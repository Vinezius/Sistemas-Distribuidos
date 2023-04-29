/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helpers;

import sistemas.distribuidos.framesCliente.EscolherPorta;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author User
 */
public class ConexaoCliente {
    
    public static void ConectarServidor(String mensagem) throws IOException{
    
        Socket socketCliente = new Socket("localhost", EscolherPorta.porta);
        
        
        PrintWriter pr = new PrintWriter(socketCliente.getOutputStream());
        
        pr.println(mensagem);
        pr.flush();
        
}
    
}
