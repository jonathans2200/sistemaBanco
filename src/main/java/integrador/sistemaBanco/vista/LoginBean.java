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
import integrador.sistemaBanco.model.CuentaDeAhorro;
import integrador.sistemaBanco.model.DetallePoliza;
import integrador.sistemaBanco.model.Empleado;
import integrador.sistemaBanco.model.Poliza;
import integrador.sistemaBanco.model.SolicitudDePoliza;
import integrador.sistemaBanco.on.GestionCuentaONLocal;
import integrador.sistemaBanco.on.GestionEmpleadoON;
import integrador.sistemaBanco.on.GestionEmpleadoONLocal;
import integrador.sistemaBanco.on.GestionPolizaONLocal;



/**
 * Clase de tipo Bean para el manejo de JSF y archivos xhtml
 * 
 * @author jonat
 * s
 */
@Named
@SessionScoped
public class LoginBean  implements Serializable{
	
	@Inject
	private GestionEmpleadoONLocal empleadoON;
	@Inject
	private GestionPolizaONLocal onPoliza;
	@Inject
	private GestionCuentaONLocal cuentaON;
	
	
	private String usuario;

	private String contrasena;
	private List<SolicitudDePoliza> solicitudes;

	private SolicitudDePoliza solicitudDePoliza;

	private SolicitudDePoliza solicitudDePolizaAux;

	private boolean editable = false;

	private boolean editabledos = false;

	private String motivo;

	private Empleado empleado;

	private String tipoC;

	@PostConstruct
	public void init() {
		solicitudes = new ArrayList<SolicitudDePoliza>();
		loadDataSol();
	
		empleado = new Empleado();
		
	}
	public GestionCuentaONLocal getCuentaON() {
		return cuentaON;
	}


	public void setCuentaON(GestionCuentaONLocal cuentaON) {
		this.cuentaON = cuentaON;
	}

	public GestionEmpleadoONLocal getEmpleadoON() {
		return empleadoON;
	}



	public void setEmpleadoON(GestionEmpleadoONLocal empleadoON) {
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
	 * @return Una lista de solicitudes de poliza
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
	 * Metodo para obtener el motivo de poliza
	 * @return El motivo de rechazo del poliza
	 */
	public String getMotivo() {
		return motivo;
	}
	
	/**
	 * Asignar el motivo del poliza
	 * @param motivo El parametro motivo me permite asignar
	 * el motivo del poliza
	 */
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	/**
	 * Metodo para obtener el empleado
	 * @return un empleado
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
					loadDataSol();
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
	 * 
	 */
	
	
	
	public void cambio() {
		editable = false;
		editabledos = true;
	}
	
	
/*	
	public String datos() {
		String res = empleadoON.getDatos();
		System.out.println(res);
		return "";
	}
	*/
	
	public void addMessage(String summary, String detail) {
		 System.out.println(summary+"mensaje +kkk"+detail);
	       
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
	    }
	
	
	   public String logout() {
	        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	        return "InicioUsuarios?faces-redirect=true";
		  // return null;
	        
	    }
	
	   /**
		 * Metodo para obtener solicitudes en estado Solicitando
		 * @return
		 */
		public List<SolicitudDePoliza> loTTT() {
			System.out.println("ENTRAAAAAAAA EN LOADDATASOL");
			// solicitudes = empleadoON.listadoSolicitudDePolizas();
			List<SolicitudDePoliza> soli = onPoliza.listadoSolicitudDePolizas();
			System.out.println(soli.size());
			List<SolicitudDePoliza> actual = new ArrayList<SolicitudDePoliza>();
			for (SolicitudDePoliza sol : soli) {
				if (sol.getEstadoPoliza().equals("Solicitando")) {
					actual.add(sol);
				}
			}
			return actual;
		}
		/**
		 * Metodo para cargar las solicitudes de Poliza
		 */
		public void loadDataSol() {
			solicitudes = new ArrayList<SolicitudDePoliza>();
			System.out.println("ENTRAAAAAAAA EN LOADDATASOL");
			// solicitudes = empleadoON.listadoSolicitudDePolizas();
			List<SolicitudDePoliza> soli = onPoliza.listadoSolicitudDePolizas();
			System.out.println(soli.size());
			List<SolicitudDePoliza> actual = new ArrayList<SolicitudDePoliza>();
			for (SolicitudDePoliza sol : soli) {
				if (sol.getEstadoPoliza().equals("Solicitando")) {
					actual.add(sol);
				}
			}
			solicitudes = actual;
		}


	


		public GestionPolizaONLocal getOnPoliza() {
			return onPoliza;
		}


		public void setOnPoliza(GestionPolizaONLocal onPoliza) {
			this.onPoliza = onPoliza;
		}


		public List<SolicitudDePoliza> getSolicitudes() {
			return solicitudes;
		}


		public void setSolicitudes(List<SolicitudDePoliza> solicitudes) {
			this.solicitudes = solicitudes;
		}


		public SolicitudDePoliza getSolicitudDePoliza() {
			return solicitudDePoliza;
		}


		public void setSolicitudDePoliza(SolicitudDePoliza solicitudDePoliza) {
			this.solicitudDePoliza = solicitudDePoliza;
		}


		public SolicitudDePoliza getSolicitudDePolizaAux() {
			return solicitudDePolizaAux;
		}


		public void setSolicitudDePolizaAux(SolicitudDePoliza solicitudDePolizaAux) {
			this.solicitudDePolizaAux = solicitudDePolizaAux;
		}
		/**
		 * Metodo para obtenet la solicitud de Poliza que se desea visualizar
		 * @param cod El parametro cod me permite devolver la solicitud de Poliza
		 * con el codigo igual al parametro cod
		 * @return Una solicitud de Poliza
		 */
		public String cargarSol(int cod) {
			editable = true;
			System.out.println("**********/****/--" + cod + editable);

			for (SolicitudDePoliza sol : solicitudes) {
				if (sol.getCodigoPoliza() == cod) {
					solicitudDePoliza = sol;
				}
			}
			return null;
		}
		/**
		 * Metodo para actualizar el estado de una salicitud de Poliza
		 * @param cod El parametro cod me permite actualizar la solicitud con el codigo igual al parametro cod
		 * @return El nombre de la pagina del Jefe de Poliza
		 */
		/**
		 * Metodo para actualizar el estado de una salicitud de Poliza
		 * @param cod El parametro cod me permite actualizar la solicitud con el codigo igual al parametro cod
		 * @return El nombre de la pagina del Jefe de Poliza
		 */
		public String aprobar(int cod) {
			System.out.println("//////-/////////-/////" + empleado.getNombre());
			for (SolicitudDePoliza sol : solicitudes) {
				if (sol.getCodigoPoliza() == cod && sol.getEstadoPoliza().equalsIgnoreCase("Solicitando")) {

					Poliza Poliza = new Poliza();
					Poliza.setFechaRegistro(new Date());
					Poliza.setInteres(12);
					Poliza.setMonto(sol.getMontoPoliza());
					Poliza.setJefeC(empleado);
					Poliza.setEstado("Pendiente");
					Poliza.setSolicitud(sol);
					List<DetallePoliza> li = onPoliza.crearTablaAmortizacion(Integer.parseInt(sol.getMesesPoliza()),
							sol.getMontoPoliza(), 12.00);
					System.out.println(li.toString());
					Poliza.setFechaVencimiento(li.get(li.size()-1).getFechaPago());
					Poliza.setDetalles(li);
					onPoliza.guardarPoliza(Poliza);
					onPoliza.aprobarPoliza(Poliza, sol.getClientePoliza());
					System.out.println(Poliza);
					solicitudDePoliza.setEstadoPoliza("Aprobado");
					onPoliza.actualizarSolicitudPoliza(solicitudDePoliza);
					CuentaDeAhorro ccv = cuentaON.buscarCuentaDeAhorroCliente(sol.getClientePoliza().getCedula());
					ccv.setSaldoCuentaDeAhorro(ccv.getSaldoCuentaDeAhorro() + sol.getMontoPoliza());
					cuentaON.actualizarCuentaDeAhorro(ccv);
					solicitudDePoliza = new SolicitudDePoliza();
					editable = false;
					editabledos = false;
					loadDataSol();
				}
			}

			return "PaginaJefeCredito";
		}
		

		
		/**
		 * Metodo para rechazar una solicitud de Poliza
		 * @return El nombre de la pagina del Jefe de Poliza
		 */
public String rechazar() {
			solicitudDePoliza.setEstadoPoliza("Rechazado");

			onPoliza.actualizarSolicitudPoliza(solicitudDePoliza);
			System.out.println(motivo);
			// System.out.println(SolicitudDePoliza.getCodigoPoliza());
			onPoliza.rechazarPoliza(solicitudDePoliza.getClientePoliza(), motivo);
			solicitudDePoliza = new SolicitudDePoliza();
			editable = false;
			editabledos = false;
			loadDataSol();
			return "PaginaJefeCredito";
		}
		
/**
 * Metodo para visualizar los documentos de una solicitud
 * @param tipo El parametro tipo nos permite asignar el nombre del documento que se desea visualizar
 * @throws IOException Excepcion para errores de visualizacion
 */
public void ver(String tipo) throws IOException {

	FacesContext facesContext = FacesContext.getCurrentInstance();
	ExternalContext externalContext = facesContext.getExternalContext();
	HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

	File file = File.createTempFile("archivoTemp", ".pdf");
	try (FileOutputStream fos = new FileOutputStream(file)) {
		if (tipo.equalsIgnoreCase("cedula")) {
			fos.write(solicitudDePoliza.getArCedula());
		} else if (tipo.equalsIgnoreCase("planilla")) {
			fos.write(solicitudDePoliza.getArPlanillaServicios());
		}

	}
	BufferedInputStream input = null;
	BufferedOutputStream output = null;

	try {
		// Open file.
		input = new BufferedInputStream(new FileInputStream(file), 10240);

		// Init servlet response.
		response.reset();
		response.setHeader("Content-Type", "application/pdf");
		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
		output = new BufferedOutputStream(response.getOutputStream(), 10240);

		// Write file contents to response.
		byte[] buffer = new byte[10240];
		int length;
		while ((length = input.read(buffer)) > 0) {
			output.write(buffer, 0, length);
		}

		// Finalize task.
		output.flush();
	} finally {

	}

	facesContext.responseComplete();
}
		
}
