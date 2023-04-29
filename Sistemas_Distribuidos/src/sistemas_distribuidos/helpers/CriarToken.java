/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemas_distribuidos.helpers;

import java.util.Random;

/**
 *
 * @author User
 */
public class CriarToken {

    public static String gerarToken() {

        String regex = "[A-Za-z0-9]";
        Random valorAleatorio = new Random();
        int length = 20;
        String token = "";
        int n = 0;
        while (length > 0) {
            n = valorAleatorio.nextInt(94) + 33;
            char c = (char) n;
            if (Character.toString(c).matches(regex)) {
                token += c;
            } else {
                continue;
            }
            length--;
        }
        return token;
    }
}
