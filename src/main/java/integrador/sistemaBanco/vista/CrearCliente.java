package integrador.sistemaBanco.vista;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.MoveEvent;
import org.primefaces.model.file.UploadedFile;

import integrador.sistemaBanco.model.Cliente;
import integrador.sistemaBanco.model.CuentaDeAhorro;
import integrador.sistemaBanco.model.Transaccion;
import integrador.sistemaBanco.on.GestionClienteON;
import integrador.sistemaBanco.on.GestionCuentaON;
import integrador.sistemaBanco.on.GestionTransaccionesON;


/**  Esta clase implementa la logica que se utilizara en las diferentes interfaces
 * para poder utilizar las entidades o clases
 * 
 * @author jonath
 * @version 1.0
 */
@Named
@RequestScoped
public class CrearCliente implements Serializable{
	//Atributos de la clase
	
	@Inject
	private GestionClienteON gestionCliente;
	@Inject
	private GestionCuentaON gestionCuenta;
	@Inject
	private GestionTransaccionesON gestionTransaccion;
	
	private Cliente cliente;
	private String numeroCuenta;
	private String tipoCuenta;
	private String saldoCuenta;
	private CuentaDeAhorro cuentaDeAhorro;
	private List<Cliente> lstClientes;
	 private Part arCedula;
	private Part arPlanillaServicios;

	
	/** 
	 * Metodo que permite inicializar atributos y metodos al momento que se llama a
	 * esta clase
	 */
	@PostConstruct	
	private void iniciar() {
		cliente = new Cliente();
		cuentaDeAhorro = new CuentaDeAhorro();
	numeroCuenta=generarNumeroCuenta();
	}
	
	
	
	public GestionClienteON getGestionCliente() {
		return gestionCliente;
	}



	public void setGestionCliente(GestionClienteON gestionCliente) {
		this.gestionCliente = gestionCliente;
	}



	public GestionCuentaON getGestionCuenta() {
		return gestionCuenta;
	}



	public void setGestionCuenta(GestionCuentaON gestionCuenta) {
		this.gestionCuenta = gestionCuenta;
	}



	public GestionTransaccionesON getGestionTransaccion() {
		return gestionTransaccion;
	}



	public void setGestionTransaccion(GestionTransaccionesON gestionTransaccion) {
		this.gestionTransaccion = gestionTransaccion;
	}



	/** 
	 * Metodo que permte obtener el atrbuto de tipo Cliente cliente de la clase
	 * @return Atributo cliente de la clase
	 */
	public Cliente getCliente() {
		return cliente;
	}
	/** 
	 * Metodo que permite asignar un valor al atributo de tipo Cliente cliente de la clase
	 * @param cliente Variable asiganda al atributo cliente de la clase
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	/**
	 * Metodo que permte obtener el atrbuto de tipo String saldoCuenta de la clase
	 * @return Atributo saldoCuenta de la clase
	 */
	public String getSaldoCuenta() {
		return saldoCuenta;
	}
	/** 
	 * Metodo que permite asignar un valor al atributo de tipo String saldoCuenta de la clase
	 * @param saldoCuenta Variable asiganda al atributo saldoCuenta de la clase
	 */
	public void setSaldoCuenta(String saldoCuenta) {
		this.saldoCuenta = saldoCuenta;
	}
	/** 
	 * Metodo que permte obtener el atrbuto de tipo String numeroCuenta de la clase
	 * @return Atributo numeroCuenta de la clase
	 */
	public String getNumeroCuenta() {
		return numeroCuenta;
	}
	/** 
	 * Metodo que permite asignar un valor al atributo de tipo String numeroCuenta de la clase
	 * @param numeroCuenta Variable asiganda al atributo numeroCuenta de la clase
	 */
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	
	/** 
	 * Metodo que permte obtener el atrbuto de tipo CuentaDeAhorro cuentaDeAhorro de la clase
	 * @return Atributo numeroCuenta de la clase
	 */
	public CuentaDeAhorro getCuentaDeAhorro() {
		return cuentaDeAhorro;
	}
	/** 
	 * Metodo que permite asignar un valor al atributo de tipo CuentaDeAhorro cuentaDeAhorro de la clase
	 * @param cuentaDeAhorro Variable asiganda al atributo cuentaDeAhorro de la clase
	 */
	public void setCuentaDeAhorro(CuentaDeAhorro cuentaDeAhorro) {
		this.cuentaDeAhorro = cuentaDeAhorro;
	}
	/** 
	 * Metodo que permte obtener el atrbuto de tipo List lstClientes de la clase
	 * @return Atributo lstClientes de la clase
	 */
	public List<Cliente> getLstClientes() {
		return lstClientes;
	}
	/** 
	 * Metodo que permite asignar un valor al atributo de tipo List lstClientes de la clase
	 * @param lstClientes Variable asiganda al atributo lstClientes de la clase
	 */
	public void setLstClientes(List<Cliente> lstClientes) {
		this.lstClientes = lstClientes;
	}

	
	/** 
	 * Metodo que permite obtener el atrbuto de tipo Part arCedula de la clase
	 * @return Atributo arCedula de la clase
	 */
	public Part getArCedula() {
		return arCedula;
	}
	/** 
	 * Metodo que permite asignar un valor al atributo de tipo Part arCedula de la clase
	 * @param arCedula Variable asiganda al atributo arCedula de la clase
	 */
	public void setArCedula(Part arCedula) {
		this.arCedula = arCedula;
	}
	/** 
	 * Metodo que permite obtener el atrbuto de tipo Part arPlanillaServicios de la clase
	 * @return Atributo arPlanillaServicios de la clase
	 */
	public Part getArPlanillaServicios() {
		return arPlanillaServicios;
	}
	/** 
	 * Metodo que permite asignar un valor al atributo de tipo Part arPlanillaServicios de la clase
	 * @param arPlanillaServicios Variable asiganda al atributo arPlanillaServicios de la clase
	 */
	public void setArPlanillaServicios(Part arPlanillaServicios) {
		this.arPlanillaServicios = arPlanillaServicios;
	}


	public void handleClose(CloseEvent event) {
		addMessage(event.getComponent().getId() + " closed", "So you don't like nature?");
	}

	public void handleMove(MoveEvent event) {
		event.setTop(500);
		addMessage(event.getComponent().getId() + " moved", "Left: " + event.getLeft() + ", Top: " + event.getTop());
	}
	/** 
	 * Metodo que permite asignar un valor para poder enviar un mensaje de confirmacion 
	 * @param summary summary Variable tipo String la cual sera el titulo de la confirmacion 
	 * @param detail Varibale tipo String en donde se almacenara la descripcion del mensaje
	 */
	public void addMessage(String summary, String detail) { 
		FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
		//FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		//FacesContext.getCurrentInstance().addMessage(null, message);
	}
	/** 
	 * Metodo que permite guardar un cliente con sus respectivos atributos
	 * @return Nulo
	 */
	public String crearCliente() {
		try {
			gestionCliente.guardarCliente(cliente);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/** 
	 * Metodo que permite verificar que la cedula que se ingresa sea valida
	 * @return Variable de tipo String en donde se asigna un mensaje si la cedula es correcta o no
	 */
	public String validarCedula() {
		if (cliente.getCedula() != null) {
			Cliente cli = gestionCliente.buscarCliente(cliente.getCedula());
			if (cli != null) {
				return "Este cliente ya se encuentra registrado";
			}
			try {
				boolean verificar = gestionCliente.validadorDeCedula(cliente.getCedula());
				if (verificar) {
					return "Cedula Valida";
				} else if (verificar == false) {
					return "Cedula Incorrecta";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return " ";

	}
	/** 
	 * Metodo que permite obtener el numero de cuenta que se asignara al cliente
	 * @return Atributo numeroCuenta de la clase en donde se asigana el numero de cuenta que se genera
	 */
	public String generarNumeroCuenta() {
		this.numeroCuenta = gestionCuenta.generarNumeroDeCuenta();
		return numeroCuenta;
	}
	
	/** 
	 * Metodo que permite crear una cuenta con sus respectivos atributos  
	 * y la primera transaccion que se realiza al crear la cuenta de ahorro
	 * @return Nulo
	 */
	public String crearCuenta() {
		try {
			cuentaDeAhorro.setNumeroCuentaDeAhorro(numeroCuenta);
			cuentaDeAhorro.setFechaDeRegistro(new Date());
			cuentaDeAhorro.setTipoCuenta(tipoCuenta);
			cuentaDeAhorro.setCliente(cliente);
			System.out.println("saldo es "+saldoCuenta+ "tipo de cuenta"+tipoCuenta);
			cuentaDeAhorro.setSaldoCuentaDeAhorro(Double.parseDouble(saldoCuenta));
		    gestionCuenta.guardarCuentaDeAhorros(cuentaDeAhorro);
			Transaccion transaccion = new Transaccion();
			transaccion.setFecha(new Date());
			transaccion.setMonto(cuentaDeAhorro.getSaldoCuentaDeAhorro());
			transaccion.setTipo("deposito");
			transaccion.setCliente(cliente);
			transaccion.setSaldoCuenta(cuentaDeAhorro.getSaldoCuentaDeAhorro());
			gestionTransaccion.guardarTransaccion(transaccion);   
			addMessage("Confirmacion", "Cliente Guardado");   
			cliente = new Cliente();
			try {
				FacesContext contex = FacesContext.getCurrentInstance();
				contex.getExternalContext().redirect("CrearCliente.xhtml");
			} catch (Exception t) { 
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/** 
	 * Metodo que permite obtener los clientes que se encuentren creados
	 * @return Variable de tipo List en donde se encuentran todos los clientes creados
	 */
	public List<Cliente> obtenerClientes() {
		try {
			List<Cliente> clis = gestionCliente.listaClientes();
			System.out.println(clis.size());
			return gestionCliente.listaClientes();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}



	public String getTipoCuenta() {
		return tipoCuenta;
	}



	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	
}
