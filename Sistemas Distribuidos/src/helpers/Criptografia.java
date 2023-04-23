package helpers;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author User
 */
public class Criptografia {

    public static String criptografarSenha(String senha) {
        String senhaCriptografada = "";

        for (int i = 0; i < senha.length(); i++) {
            senhaCriptografada += (char) (senha.charAt(i) + 2);
        }
        return senhaCriptografada;
    }

    public static String descriptografar(String senha) {
        String senhaDescriptografada = "";
        for (int i = 0; i < senha.length(); i++) {
            senhaDescriptografada += (char) (senha.charAt(i) - 2);
        }
        return senhaDescriptografada;
    }

}
