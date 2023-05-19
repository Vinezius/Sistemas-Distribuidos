/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemas_distribuidos.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 *
 * @author User
 */
public class FormatarDataHora {
    public static boolean validarData(String data) {
		//formato da data = yyyy-mm-dd
		try {
		    LocalDate dataFormatada = LocalDate.parse(data);
		    if (dataFormatada != null) {
		       return true;
		    } else {
		       return false;
		    }
		} catch (DateTimeParseException e) {
		    System.out.println("Data inválida: " + data);
		    return false;
		}
	}
    
        public static String formatarHora(String hora){
        return hora;
    }

}
