package integrador.sistemaBanco.vista;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import integrador.sistemaBanco.dao.SolicitudDePolizasDAO;
import integrador.sistemaBanco.model.Empleado;
import integrador.sistemaBanco.model.SolicitudDePoliza;
import integrador.sistemaBanco.on.GestionEmpleadoON;



/**
 * Clase de tipo Bean para el manejo de JSF y archivos xhtml
 * 
 * @author jonat
 * @version: 1.0
 */
@Named
@SessionScoped
public class LoginBean implements Serializable{
	
	@Inject
	private GestionEmpleadoON empleadoON;
	private String usuario;

	private String contrasena;


	private boolean editable = false;

	private boolean editabledos = false;

	private String motivo;

	private Empleado empleado;

	private String tipoC;

	@PostConstruct
	public void init() {
	
	
		empleado = new Empleado();
		
	}


	public GestionEmpleadoON getEmpleadoON() {
		return empleadoON;
	}



	public void setEmpleadoON(GestionEmpleadoON empleadoON) {
		this.empleadoON = empleadoON;
	}



	/**
	 * Metodo para obtener el usuario
	 * 
	 * @return El usuario o valor digitado en la pagina xhtml
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Metodo para asignar un usuario
	 * 
	 * @param usuario El parametro usuario me permite asignar valor a mi variable
	 *                usuario.
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Metodo para obtener la contraseña
	 * 
	 * @return La contraseña que se ingresa en la pagina xhtml
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * Metodo para asignar una contraseña
	 * 
	 * @param contrasena El parametro contraseña me permite asignar un valor a mi
	 *                   variable contrasena
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	/**
	 * Metodo para obtener solicitudes
	 * @return Una lista de solicitudes de Credito
	 */

	
	/**
	 * Metodo para asiganr un booleano 
	 * @return Un boleano con TRUE O FALSE
	 */
	public boolean isEditable() {
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
	 * Metodo para asiganr un booleano 
	 * @return Un boleano con TRUE O FALSE
	 */
	public boolean isEditabledos() {
		return editabledos;
	}
	
	/**
	 * Asignar el valor a editable
	 * @param editabledos El parametro editabledos me permite asignar los valores de true o false
	 */
	public void setEditabledos(boolean editabledos) {
		this.editabledos = editabledos;
	}
	
	/**
	 * Metodo para obtener el motivo de credito
	 * @return El motivo de rechazo del credito
	 */
	public String getMotivo() {
		return motivo;
	}
	
	/**
	 * Asignar el motivo del credito
	 * @param motivo El parametro motivo me permite asignar
	 * el motivo del credito
	 */
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	/**
	 * Metodo para obtener el empleado
	 * @return
	 */
	public Empleado getEmpleado() {
		return empleado;
	}
	
	/**
	 * Asignar un empleado
	 * @param empleado El parametro empleado me permite asignar
	 * el empleado que se encuentra conectado
	 */
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	
	/**
	 * Metodo para obtener el tipo de cliente
	 * @return El tipo de cliente
	 */
	public String getTipoC() {
		return tipoC;
	}
	
	/**
	 * Asignar el tipo de cliente
	 * @param tipoC El parametro tipoC me permite asignar
	 * el tipo de cliente
	 */
	public void setTipoC(String tipoC) {
		this.tipoC = tipoC;
	}
	


	/**
	 * Metodo para validar rol de Empleado
	 * 
	 * @return La pagina de acuerdo al rol del empleado
	 */
	public String validarUsuario() {
		Empleado emp;
		try {
			emp = empleadoON.usuario(usuario, contrasena);
			System.out.println("******	USUARIO	****" + emp.getNombre());
			empleado = emp;
			if (emp != null && emp.getRol().equalsIgnoreCase("Cajero")) {
				try {
					addMessage("OK", "Ingreso");
					
					
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("empleado", emp);
					//FacesContext contex2 = FacesContext.getCurrentInstance();
					FacesContext contex = FacesContext.getCurrentInstance();
					
					contex.getExternalContext().redirect("PaginaCajero.xhtml");
				} catch (Exception e) {
				}
			} else if (emp != null && emp.getRol().equalsIgnoreCase("AsistenteCaptacion")) {
				try {
					FacesContext contex = FacesContext.getCurrentInstance();
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("empleado", emp);
					contex.getExternalContext().redirect("PaginaJefeCredito.xhtml");
				} catch (Exception e) {
				}
			} else if (emp != null && emp.getRol().equalsIgnoreCase("Admin")) {
				try {
					FacesContext contex = FacesContext.getCurrentInstance();
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("empleado", emp);
					contex.getExternalContext().redirect("Admin.xhtml");
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			addMessage("ERROR", "NO SE PUEDO INGRESAR, REVISE USUARIO CONTRASEÑA");
			return "InicioUsuarios";
		}
		
		return null;
	}
	
	/**
	
	/**
	 * Metodo para obtener solicitudes en estado Solicitando
	 * @return
	 */
	
	
	
	public void cambio() {
		editable = false;
		editabledos = true;
	}
	
	
	
	public String datos() {
		String res = empleadoON.getDatos();
		System.out.println(res);
		return "";
	}
	
	
	public void addMessage(String summary, String detail) {
		 System.out.println(summary+"mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmkkk"+detail);
	       
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
	    }
	
	
	   public String logout() {
	        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	        return "InicioUsuarios?faces-redirect=true";
		  // return null;
	        
	    }
	
}
