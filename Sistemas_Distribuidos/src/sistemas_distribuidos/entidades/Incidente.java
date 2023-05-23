/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemas_distribuidos.entidades;

/**
 *
 * @author User
 */
public class Incidente {
    
	private Integer tipoIncidente;
	private String data;
	private String hora;
	private String cidade;
	private String bairro;
	private String rua;
	private String estado;
	private String token;
	private int idUsuario;
	
	private int id = -1;
	
	public Incidente(Integer tipoIncidente, String data, String hora, String cidade, String bairro, String rua,
			String estado, int idUsuario) {
		this.tipoIncidente = tipoIncidente;
		this.data = data;
		this.hora = hora;
		this.cidade = cidade;
		this.bairro = bairro;
		this.rua = rua;
		this.estado = estado;
		this.idUsuario = idUsuario;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Integer getTipoIncidente() {
		return tipoIncidente;
	}
	public String getData() {
		return data;
	}
	public String getHora() {
		return hora;
	}
	public String getCidade() {
		return cidade;
	}
	public String getBairro() {
		return bairro;
	}
	public String getRua() {
		return rua;
	}
	public String getEstado() {
		return estado;
	}
	public int getIdUsuario() {
		return idUsuario;
	}

}
