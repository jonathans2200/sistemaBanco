package integrador.sistemaBanco.vista;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.MoveEvent;

import integrador.sistemaBanco.model.Empleado;
import integrador.sistemaBanco.on.GestionEmpleadoON;



/**
 * Clase de tipo Bean para el manejo de JSF y archivos xhtml
 * 
 * @author jonathan
 * @version: 1.0
 */
@Named
@RequestScoped
public class EmpleadosBean implements Serializable {

	@Inject 
	private GestionEmpleadoON empleadoON;
	private Empleado empleado;

	private boolean ced;

	private List<Empleado> listaEmpleados;

	private String tipoEmpleado;

	private boolean editable = false;
	
	private boolean editabledos = false;
	
	private String motivo;

	@PostConstruct
	public void init() {
		empleado = new Empleado();
		//solicitudDeCredito = new SolicitudDeCredito();
		loadData();
		
	}



	/**
	 * Metodo para obtener un Empleado
	 * 
	 * @return Un empleado para un registro en la Base de Datos
	 */
	public Empleado getEmpleado() {
		return empleado;
	}

	/**
	 * Metodo para asignar un Empleado
	 * 
	 * @param empleado El parametro empleado me Permite asignar datos a un Empleado
	 */
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	/**
	 * Metodo para Obtener un Mensaje
	 * 
	 * @return Si es TRUE o False
	 */
	public boolean isCed() {
		return ced;
	}

	/**
	 * Metodo para asignar un valor
	 * 
	 * @param ced El parametro ced me permite asignar el volor booleano de TRUE o
	 *            FALSE
	 */
	public void setCed(boolean ced) {
		this.ced = ced;
	}

	/**
	 * Metodo para obtener lista de Empleados
	 * 
	 * @return Una lista de tipo Empleados
	 */
	public List<Empleado> getListaEmpleados() {
		return listaEmpleados;
	}

	/**
	 * Metodo para asignar una lista de Empleado
	 * 
	 * @param listaEmpleados El parametro listaEmpleados me asigna los datos de los
	 *                       Empleados a mi lista
	 */
	public void setListaEmpleados(List<Empleado> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}

	/**
	 * Metodo para obtener un tipo de Empleado
	 * 
	 * @return El Tipod de Empleado que se esta asignando en la pagina xhtml
	 */
	public String getTipoEmpleado() {
		return tipoEmpleado;
	}

	/**
	 * Metodo para asignar el tipo de Empleado
	 * 
	 * @param tipoEmpleado El parametro tipoEmpleado me permite asignar el tipo de
	 *                     empleado seleccionado en la pagina xhtml
	 */
	public void setTipoEmpleado(String tipoEmpleado) {
		this.tipoEmpleado = tipoEmpleado;
	}
	

	public GestionEmpleadoON getEmpleadoON() {
		return empleadoON;
	}



	public void setEmpleadoON(GestionEmpleadoON empleadoON) {
		this.empleadoON = empleadoON;
	}



	/**
	 * Metodo para asiganr un booleano 
	 * @return Un boleano con TRUE O FALSE
	 */
	public boolean getEditable() {
		return editable;
	}
	
	/**
	 * Asignar el valor a editable
	 * @param editable El parametro editable me permite asignar los valores de true o false
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	/**
	 * Metodo para obtner el valor de la variable motivo
	 * @return El valor de la variable motivo
	 */
	public String getMotivo() {
		return motivo;
	}
	
	/**
	 * Asignar el valor de la variable motivo
	 * @param motivo El parametro motivo me permite asignar el valor
	 * del motivo
	 */
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	/**
	 * Metofo para obtner el valor de la variable editabledos
	 * @return El valor de la variable editabledos que puede ser true o false
	 */
	public boolean getEditabledos() {
		return editabledos;
	}
	
	/**
	 * Asignar el valor a la variable editabledos
	 * @param editabledos El parametro editabledos me permite asiganar el
	 * valor de true o false
	 */
	public void setEditabledos(boolean editabledos) {
		this.editabledos = editabledos;
	}

	/**
	 * Metodo para guardar datos del Empleado
	 * 
	 * @return La paguina con la lista de los Empleados registrados
	 */
	public String guardarDatos() {

		System.out.println(this.empleado.getCedula() + "   " + this.empleado.getNombre() + tipoEmpleado);

		try {
			if (tipoEmpleado.equalsIgnoreCase("cajero")) {
				empleado.setRol("Cajero");
				empleadoON.guardarEmpleado(empleado);
				addMessage("Confirmacion", "Empleado Guardado");
				
			} else if (tipoEmpleado.equalsIgnoreCase("jefeCredito")) {
				empleado.setRol("JefeCredito");
				empleadoON.guardarEmpleado(empleado);
				addMessage("Confirmacion", "Empleado Guardado");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			
			FacesContext contex = FacesContext.getCurrentInstance();
			contex.getExternalContext().redirect("Lista-Empleados.xhtml");
		} catch (Exception e) {
		}
		return null;

	}

	/**
	 * Metodo para validar un Empleado
	 * 
	 * @return Mensaje si el Empleado esta registrado en la Base de Datos
	 */
	public String valCedula() {
		System.out.println("*-------*" + empleado.getCedula());
		if (empleado.getCedula() != null) {
			Empleado usuarioRegistrado = empleadoON.usuarioRegistrado(empleado.getCedula());
			if (usuarioRegistrado != null) {
				System.out.println("Registrado");
				return "Empleado REGISTRADO";
			}
			try {
				ced = empleadoON.validadorDeCedula(empleado.getCedula());
				System.out.println(ced);
				if (ced) {
					return "Cedula Valida";
				} else if (ced == false) {
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
	 * Metodo para asignar la lista de Empleados registrados en la Base de Datos
	 */
	public void loadData() {
		listaEmpleados = empleadoON.listadoEmpleados();
	}
	
	
	
	
	public void cambio() {
		editable = false;
		editabledos = true;
	}
	
	
	
	public void addMessage(String summary, String detail) { 
		FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
		//FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		//FacesContext.getCurrentInstance().addMessage(null, message);
	}
 
 public void handleClose(CloseEvent event) {
        addMessage(event.getComponent().getId() + " closed", "So you don't like nature?");
    }
     
    public void handleMove(MoveEvent event) {
        event.setTop(500);
    	addMessage(event.getComponent().getId() + " moved", "Left: " + event.getLeft() + ", Top: " + event.getTop());
    }
	

}
