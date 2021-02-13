package integrador.sistemaBanco.vista;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import integrador.sistemaBanco.model.Cliente;
import integrador.sistemaBanco.model.CuentaDeAhorro;
import integrador.sistemaBanco.model.DetallePoliza;
import integrador.sistemaBanco.model.Empleado;
import integrador.sistemaBanco.model.Poliza;
import integrador.sistemaBanco.model.SolicitudDePoliza;
import integrador.sistemaBanco.on.GestionCorreoON;
import integrador.sistemaBanco.on.GestionCuentaON;
import integrador.sistemaBanco.on.GestionCuentaONLocal;
import integrador.sistemaBanco.on.GestionPolizaON;
import integrador.sistemaBanco.on.GestionPolizaONLocal;
/**
 * Esta clase implementa la logica que se utilizara en las diferentes interfaces
 * para poder utilizar las entidades o clases
 * 
 * @author jonat
 *
 */

@Named
@RequestScoped
public class PolizaBean implements Serializable {
	@Inject
	private GestionPolizaONLocal polizaON;
	@Inject
	private GestionCuentaONLocal cuentaON;
	
	private List<SolicitudDePoliza> solicitudes;
	private SolicitudDePoliza solicitudDePoliza;
	private SolicitudDePoliza solicitudDePolizaAux;
	GestionCorreoON correo = new GestionCorreoON();
	private boolean editable = false;
	private Empleado empleado ;
	private double interes;
	private double total;
	private String motivo;
	private int dias;
	private double monto;
	private boolean editabledos = false;
	@PostConstruct
	public void init() {
		solicitudes = new ArrayList<SolicitudDePoliza>();
	loadDataSol();
	interes=0;
	monto=0;
	}
	
	/**
	 * Metodo para obtener solicitudes en estado Solicitando
	 * @return
	 */
	public List<SolicitudDePoliza> loTTT() {
		System.out.println("ENTRAAAAAAAA EN LOADDATASOL");
		// solicitudes = empleadoON.listadoSolicitudDePolizas();
		List<SolicitudDePoliza> soli = polizaON.listadoSolicitudDePolizas();
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
		List<SolicitudDePoliza> soli = polizaON.listadoSolicitudDePolizas();
		System.out.println(soli.size());
		List<SolicitudDePoliza> actual = new ArrayList<SolicitudDePoliza>();
		for (SolicitudDePoliza sol : soli) {
			if (sol.getEstadoPoliza().equals("Solicitando")) {
				actual.add(sol);
			}
		}
		solicitudes = actual;
	}


	public GestionPolizaONLocal getPolizaON() {
		return polizaON;
	}

	public void setPolizaON(GestionPolizaONLocal polizaON) {
		this.polizaON = polizaON;
	}

	public GestionCuentaONLocal getCuentaON() {
		return cuentaON;
	}

	public void setCuentaON(GestionCuentaONLocal cuentaON) {
		this.cuentaON = cuentaON;
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

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
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

	public GestionCorreoON getCorreo() {
		return correo;
	}

	public void setCorreo(GestionCorreoON correo) {
		this.correo = correo;
	}

	public boolean isEditable() {
		return editable;
	}

	public double getInteres() {
		return interes;
	}

	public void setInteres(double interes) {
		this.interes = interes;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public boolean isEditabledos() {
		return editabledos;
	}

	public void setEditabledos(boolean editabledos) {
		this.editabledos = editabledos;
	}

	/**
	 * Metodo para carfar las solicitudes aprobadas segun el tipo de cliente
	 * @param apr El parametro me permite devolver las listas de los
	 * Polizas segun el tipo de cliente
	 * @return Lista de solicitudes de Polizas
	 */
	public List<SolicitudDePoliza> loadDataSolAR(String apr) {
		System.out.println("ENTRAAAAAAAA APROBADOS RECHAZADOS");
		// solicitudes = empleadoON.listadoSolicitudDePolizas();
		List<SolicitudDePoliza> soli = polizaON.listadoSolicitudDePolizas();
		System.out.println(soli.size());
		List<SolicitudDePoliza> actual = new ArrayList<SolicitudDePoliza>();
		List<SolicitudDePoliza> actual2 = new ArrayList<SolicitudDePoliza>();
		for (SolicitudDePoliza sol : soli) {
			
				actual.add(sol);
			
		}

		if (apr.equals("Ap")) {
			return actual;
		} else if (apr.equals("Rch")) {
			return actual2;
		}
		return null;
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
	 * Metodo que permite indicar los datos para enviar mediante el correo de la aprobación de la poliza
	 * 
	 * @param Poliza Una clase Poliza con los datos del Poliza.
	 * @param cliente Una clase Cliente con los datos del cliente.
	 *  Exception Excepción por si sucede algún error en el proceso de envio.
	 */
	public void aprobarPoliza(Poliza Poliza, Cliente cliente) {
		String destinatario = cliente.getCorreo();
		String asunto = "APROBACIÓN DE Poliza";
		String cuerpo = "JAMVirtual SISTEMA TRANSACCIONAL\n"
				+ "------------------------------------------------------------------------------\n"
				+ "              Estimado(a): " + cliente.getNombre().toUpperCase() + " "
				+ cliente.getApellido().toUpperCase() + "\n"
				+ "------------------------------------------------------------------------------\n"
				+ "COOPERATIVA JAM le informa que su Poliza ha sido aprobado.                   \n"
				+ "                                                                              \n"
				+ "                         Fecha: " + obtenerFecha(Poliza.getFechaRegistro()) + "\n"
				+ "                                                                              \n"
				+ "La informacion de sus cuotas se encuentra en el archivo adjunto.              \n"
				+ "------------------------------------------------------------------------------\n";

		CompletableFuture.runAsync(() -> {
			try {
				correo.enviarCorreo2(destinatario, asunto, cuerpo, Poliza);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
//			} 
	}

	
	/**
	 * Metodo que permite cambiar el formato de la fecha
	 * 
	 * @param fecha Fecha que se cambiara el formato
	 * @return La fecha en un formato requerido de tipo texto.
	 */
	public String obtenerFecha(Date fecha) {
		DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return hourdateFormat.format(fecha);
	}
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
				List<DetallePoliza> li = polizaON.crearTablaAmortizacion(Integer.parseInt(sol.getMesesPoliza()),
						sol.getMontoPoliza(), 12.00);
				System.out.println(li.toString());
				Poliza.setFechaVencimiento(li.get(li.size()-1).getFechaPago());
				Poliza.setDetalles(li);
				polizaON.guardarPoliza(Poliza);
				polizaON.aprobarPoliza(Poliza, sol.getClientePoliza());
				System.out.println(Poliza);
				solicitudDePoliza.setEstadoPoliza("Aprobado");
				polizaON.actualizarSolicitudPoliza(solicitudDePoliza);
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

		polizaON.actualizarSolicitudPoliza(solicitudDePoliza);
		System.out.println(motivo);
		// System.out.println(SolicitudDePoliza.getCodigoPoliza());
		polizaON.rechazarPoliza(solicitudDePoliza.getClientePoliza(), motivo);
		solicitudDePoliza = new SolicitudDePoliza();
		editable = false;
		editabledos = false;
		loadDataSol();
		return "PaginaJefeCredito";
	}
	
	public void cambio() {
		editable = false;
		editabledos = true;
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

	/**
	 * metodo para ver el archivo de la cedula o de la plantilla
	 * @param tipo
	 * @throws IOException
	 */
	public void ver2(String tipo) throws IOException {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

		File file = File.createTempFile("archivoTemp", ".pdf");
		try (FileOutputStream fos = new FileOutputStream(file)) {
			if (tipo.equalsIgnoreCase("cedula")) {
				fos.write(solicitudDePolizaAux.getArCedula());
			} else if (tipo.equalsIgnoreCase("planilla")) {
				fos.write(solicitudDePolizaAux.getArPlanillaServicios());
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

		// Inform JSF that it doesn't need to handle response.
		// This is very important, otherwise you will get the following exception in the
		// logs:
		// java.lang.IllegalStateException: Cannot forward after response has been
		// committed.
		facesContext.responseComplete();
	}
/**
 * metodo para obtener  el interes de acuerdo a los dias de la poliza
 * @param tiempo valor en dias para la poliza
 * @return
 */
	public Double obtenerInteres(int tiempo) {
		

			
		
		if(tiempo>=30 && tiempo <=59) {
		return 5.50	;
		}else 
			if(tiempo>=60 &&  tiempo <=89) {
			return 5.75;
			}else 
				if(tiempo>=90 &&  tiempo <=179) {
					return 6.25;
					}else 
						if(tiempo>=180 &&  tiempo <=269) {
							return 7.00;
							}else 
								if(tiempo>=270 &&  tiempo <=359) {
									return 7.50;
									}else 
										if(tiempo>=360) {
											return 8.50;
											}
	
		return null;
		
		
	}
	
	
	/***
	 * metodo que permite obtener los datos del interes y el monto total de la poliza 
	 * @param mes
	 * @param monto
	 */
	public String tablaAmor(){
		    interes= obtenerInteres(dias);
		    this.setInteres(interes);
		   total =((monto*interes)*dias)/100;
		        System.out.println("monto inicial "+monto);
		        System.out.println("interes es " + interes);
		        System.out.println("valor total " + total);
		        System.out.println("valoooooor"+this.getTotal());
		      this.setTotal(total);
		  return null;
	}
}
