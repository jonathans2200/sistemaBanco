 package integrador.sistemaBanco.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
/** 
 * Esta clase representa una entidad o tabla llamada CuentaDeAhorro de la base de datos y sus columnas
 * @author jonat
 */
@Entity
public class CuentaDeAhorro implements Serializable {
	//Atributos de la clase
	@Id
	@Column(name="numero_cuenta")
	private String numeroCuentaDeAhorro;
	private Date fechaDeRegistro;  
	private Double saldoCuentaDeAhorro; 
	private String tipoCuenta;
	@ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.ALL })
	@JoinColumn(name="cedula_cliente")
	private Cliente cliente;
	
	/** 
	 * Constructor de la clase
	 */
	public CuentaDeAhorro() {
	
	} 
	
	/** 
	 * Metodo que permite obtener el atributo numeroCuentaDeAhorro 
	 * @return El atributo numeroCuentaDeAhorro de esta clase
	 */
	public String getNumeroCuentaDeAhorro() {
		return numeroCuentaDeAhorro;
	} 
	
	/** 
	 * Metodo que permite asignarle un valor al atributo numeroCuentaDeAhorro
	 * @param numeroCuentaDeAhorro Variable que se asigna al atributo
	 */
	public void setNumeroCuentaDeAhorro(String numeroCuentaDeAhorro) {
		this.numeroCuentaDeAhorro = numeroCuentaDeAhorro;
	} 
	
	/** 
	 * Metodo que permite obtener el atributo fechaDeRegistro
	 * @return El atributo fechaDeRegistro de esta clase
	 */
	public Date getFechaDeRegistro() {
		return fechaDeRegistro;
	} 
	
	/** 
	 * Metodo que permite asignarle un valor al atributo fechaRegistro
	 * @param fechaDeRegistro Variable que se asigna al atributo
	 */
	public void setFechaDeRegistro(Date fechaDeRegistro) {
		this.fechaDeRegistro = fechaDeRegistro;
	} 
	
	
	
	public Double getSaldoCuentaDeAhorro() {
		return saldoCuentaDeAhorro;
	}

	public void setSaldoCuentaDeAhorro(Double saldoCuentaDeAhorro) {
		this.saldoCuentaDeAhorro = saldoCuentaDeAhorro;
	}

	/** 
	 * Metodo que permite obtener el atributo cliente
	 * @return El atributo cliente de esta clase
	 */
	public Cliente getCliente() {
		return cliente;
	} 
	
	/** 
	 * Metodo que permite asignarle un valor al atributo cliente
	 * @param cliente Variable que se asigna al atributo
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	
	/***
	 * metodo paa retornar que tipo de cuenta es 
	 * @return
	 */
	public String getTipoCuenta() {
		return tipoCuenta;
	}
	
	/**
	 * permite asignarle el valor al atribuo tipo de cuenta
	 * @param tipoCuenta guarda un tipo de cuenta
	 */

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	} 
	
	
	
	

}
