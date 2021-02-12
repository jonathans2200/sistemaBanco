package integrador.sistemaBanco.utils;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import integrador.sistemaBanco.model.Cliente;
import integrador.sistemaBanco.model.CuentaDeAhorro;
import integrador.sistemaBanco.servicios.PolizaRespuesta;
/**
 * clase donde tendremos los datos del cliente o de la  cuenta de ahorros
 * @author jonat
 *
 */


public class Respuesta {
	
	private int codigo;
	private String descripcion;
	private @JsonProperty("Cliente")Cliente cliente;   
	private @JsonProperty("Cuenta")CuentaDeAhorro cuentaDeAhorro;
	private @JsonProperty("Poliza")List<PolizaRespuesta> listaPoliza;
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public CuentaDeAhorro getCuentaDeAhorro() {
		return cuentaDeAhorro;
	}
	public void setCuentaDeAhorro(CuentaDeAhorro cuentaDeAhorro) {
		this.cuentaDeAhorro = cuentaDeAhorro;
	}
	public List<PolizaRespuesta> getListaPoliza() {
		return listaPoliza;
	}
	public void setListaPoliza(List<PolizaRespuesta> listaPoliza) {
		this.listaPoliza = listaPoliza;
	}

	
	
	

}
