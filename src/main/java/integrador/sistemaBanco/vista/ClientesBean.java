package integrador.sistemaBanco.vista;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.servlet.http.Part;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.MoveEvent;
import org.primefaces.model.file.UploadedFile;

import integrador.sistemaBanco.model.Cliente;
import integrador.sistemaBanco.model.CuentaDeAhorro;
import integrador.sistemaBanco.model.Poliza;
import integrador.sistemaBanco.model.SesionCliente;
import integrador.sistemaBanco.model.SolicitudDePoliza;
import integrador.sistemaBanco.model.Transaccion;
import integrador.sistemaBanco.on.GestionClienteON;
import integrador.sistemaBanco.on.GestionClienteONLocal;
import integrador.sistemaBanco.on.GestionCuentaON;
import integrador.sistemaBanco.on.GestionCuentaONLocal;
import integrador.sistemaBanco.on.GestionPolizaON;
import integrador.sistemaBanco.on.GestionPolizaONLocal;
import integrador.sistemaBanco.on.GestionSesionON;
import integrador.sistemaBanco.on.GestionSesionONLocal;
import integrador.sistemaBanco.on.GestionTransaccionesON;
import integrador.sistemaBanco.on.GestionTransaccionesONLocal;

/**
 * Esta clase implementa la logica que se utilizara en las diferentes interfaces
 * para poder utilizar las entidades o clases
 * 
 * @author jonat
 * @version 1.0
 */
@Named
@SessionScoped
public class ClientesBean implements Serializable {
	// Atributos de la clase
	@Inject
	private GestionClienteONLocal gestionCliente;
	@Inject
	private GestionCuentaONLocal gestionCuenta;
	@Inject
	private GestionSesionONLocal gestionSesion;
	@Inject
	private GestionTransaccionesONLocal gestionTransaccion;
	@Inject
	private GestionPolizaONLocal gestionPolizas;

	private Cliente cliente;
	private SolicitudDePoliza solicitudDePoliza;
	private String numeroCuenta;
	private CuentaDeAhorro cuentaDeAhorro;
	private CuentaDeAhorro buscarCuentaDeAhorro;
	private String cedulaParametro;
	private Transaccion transaccion;
	private List<Cliente> lstClientes;
	private List<SesionCliente> lstSesionesCliente;
	private List<Transaccion> lstTransacciones;
	private List<Poliza> lstPolizasAprobados;
	private String saldoCuenta;
	private Date fechaInicio;
	private Date fechaFinal;
	private String tipoTransaccion;
	private double totalPoliza;
	private String fechasInvalidas;
	private Double montoPoliza;
	private String mesesPoliza;
	private double interes;
	private InputStream arCedula;

	private InputStream arPlanillaServicios;
	private int codigoPoliza;
	private boolean editable;

	/**
	 * Metodo que permite inicializar atributos y metodos al momento que se llama a
	 * esta clase
	 */
	@PostConstruct
	private void iniciar() {
		listarClientes();
		tipoTransaccion = "Todos";
		System.out.println(lstClientes.size());
		lstPolizasAprobados = new ArrayList<Poliza>();
		cuentaDeAhorro = new CuentaDeAhorro();
		solicitudDePoliza = new SolicitudDePoliza();
		cliente = new Cliente();

	}

	public SolicitudDePoliza getSolicitudDePoliza() {
		return solicitudDePoliza;
	}

	public void setSolicitudDePoliza(SolicitudDePoliza solicitudDePoliza) {
		this.solicitudDePoliza = solicitudDePoliza;
	}

	/**
	 * Metodo que permite obtener el atributo cliente
	 * 
	 * @return Atributo cliente de la clase
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * Metodo que permite asignar un valor al atributo cliente
	 * 
	 * @param cliente Variable asiganda al atributo cliente de la clase
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * Metodo que permite obtener el atributo numeroCuenta de la clase
	 * 
	 * @return Atributo numeroCuenta de la clase
	 */
	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	/**
	 * Metodo que permite asignar un valor al atributo numeroCuenta de la clase
	 * 
	 * @param numeroCuenta Variable asiganda al atributo numeroCuenta de la clase
	 */
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	/**
	 * Metodo que permite obtener el atributo cuentaDeAhorro de la clase
	 * 
	 * @return Atributo cuentaDeAhorro de la clase
	 */
	public CuentaDeAhorro getCuentaDeAhorro() {
		return cuentaDeAhorro;
	}

	/**
	 * Metodo que permite asignar un valor al atributo cuentaDeAhorro de la clase
	 * 
	 * @param cuentaDeAhorro Variable asignada al atributo cuentaDeAhorro de la
	 *                       clase
	 */
	public void setCuentaDeAhorro(CuentaDeAhorro cuentaDeAhorro) {
		this.cuentaDeAhorro = cuentaDeAhorro;
	}

	/**
	 * Metodo que permite obtener el atributo buscarCuentaDeAhorro de la clase
	 * 
	 * @return Atributo buscarCuentaDeAhorro de la clase
	 */
	public CuentaDeAhorro getBuscarCuentaDeAhorro() {
		return buscarCuentaDeAhorro;
	}

	/**
	 * Metodo que permite asignar un valor al atributo buscarCuentaDeAhorro de la
	 * clase
	 * 
	 * @param buscarCuentaDeAhorro Variable asignada al atributo
	 *                             buscarCuentaDeAhorro de la clase
	 */
	public void setBuscarCuentaDeAhorro(CuentaDeAhorro buscarCuentaDeAhorro) {
		this.buscarCuentaDeAhorro = buscarCuentaDeAhorro;
	}

	/**
	 * Metodo que permite obtener el atributo cedulaParametro de la clase
	 * 
	 * @return Atributo cedulaParametro de la clase
	 */
	public String getCedulaParametro() {
		return cedulaParametro;
	}

	/**
	 * Metodo que permite asignar un valor al atributo fechasInvalidas de la clase
	 * 
	 * @return
	 */
	public String getFechasInvalidas() {
		return fechasInvalidas;
	}

	/**
	 * Metodo que permite asignar un valor al atributo fechasInvalidas de la clase
	 * 
	 * @param fechasInvalidas Variable asignada al atributo fechasInvalidas de la
	 *                        clase
	 */
	public void setFechasInvalidas(String fechasInvalidas) {
		this.fechasInvalidas = fechasInvalidas;
	}

	/**
	 * Metodo que permite asignar un valor al atributo cedulaParametro de la clase y
	 * a su vez buscar la cuenta de ahorros y transaccion de un cliente que tenga la
	 * cedula asignada al metodo
	 * 
	 * @param cedulaParametro Variable asignada al atributo cedulaParametro de la
	 *                        clase
	 */
	public void setCedulaParametro(String cedulaParametro) {
		this.cedulaParametro = cedulaParametro;
		if (cedulaParametro != null) {
			try {
				buscarCuentaDeAhorro = gestionCuenta.buscarCuentaDeAhorroCliente(cedulaParametro);
				List<Transaccion> lista = gestionTransaccion.listadeTransacciones(cedulaParametro);
				transaccion = lista.get(lista.size() - 1);
				ultimosDias();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/***
	 * Metodo que permite obtener el atributo transaccion de la clase
	 * 
	 * @return Atributo transaccion de la clase
	 */
	public Transaccion getTransaccion() {
		return transaccion;
	}

	/**
	 * Metodo que permite asignar un valor al atributo transaccion de la clase
	 * 
	 * @param transaccion Variable asignada al atributo transaccion de la clase
	 */
	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}

	/**
	 * Metodo que permite obtener el atributo de tipo lista lstClientes de la clase
	 * 
	 * @return Atributo de tipo lista lstClientes de la clase
	 */
	public List<Cliente> getLstClientes() {
		return lstClientes;
	}

	/**
	 * Metodo que permite asignar un valor al atributo de tipo lista lstClientes de
	 * la clase
	 * 
	 * @param lstClientes Variable asignada al atributo de tipo lista lstClientes de
	 *                    la clase
	 */
	public void setLstClientes(List<Cliente> lstClientes) {
		this.lstClientes = lstClientes;
	}

	/**
	 * Metodo que permite obtener el atributo de tipo lista lstSesionesClientes de
	 * la clase
	 * 
	 * @return Atributo de tipo lista lstSesionesClientes de la clase
	 */
	public List<SesionCliente> getLstSesionesCliente() {
		return lstSesionesCliente;
	}

	/**
	 * Metodo que permite asignar un valor al atributo de tipo lista
	 * lstSesionesClientes de la clase
	 * 
	 * @param lstSesionesCliente Variable asignada al atributo de tipo lista
	 *                           lstSesionesClientes de la clase
	 */
	public void setLstSesionesCliente(List<SesionCliente> lstSesionesCliente) {
		this.lstSesionesCliente = lstSesionesCliente;
	}

	/**
	 * Metodo que permite obtener el atributo de tipo lista lstTransacciones de la
	 * clase
	 * 
	 * @return Atributo de tipo lista lstTransacciones de la clase
	 */
	public List<Transaccion> getLstTransacciones() {
		return lstTransacciones;
	}

	/**
	 * Metodo que permite asignar un valor al atributo de tipo lista
	 * lstTransacciones de la clase
	 * 
	 * @param lstTransacciones Variable asignada al atributo de tipo lista
	 *                         lstTransacciones de la clase
	 */
	public void setLstTransacciones(List<Transaccion> lstTransacciones) {
		this.lstTransacciones = lstTransacciones;
	}

	/**
	 * Metodo que permite obtener el atributo de tipo date fechaInicio de la clase
	 * 
	 * @return Atributo de tipo date fechaInicio de la clase
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Metodo que permite asignar un valor al atributo de tipo date fechainicio de
	 * la clase
	 * 
	 * @param fechaInicio Variable asignada al atributo de tipo date fechaInicio de
	 *                    la clase
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * Metodo que permite obtener el atributo de tipo date fechaFinal de la clase
	 * 
	 * @return Atributo de tipo date fechaFinal de la clase
	 */
	public Date getFechaFinal() {
		return fechaFinal;
	}

	/**
	 * Metodo que permite asignar un valor al atributo de tipo date fechaFinal de la
	 * clase
	 * 
	 * @param fechaFinal Variable asignada al atributo de tipo date fechaFinal de la
	 *                   clase
	 */
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	/**
	 * Metodo que permite obtener el atributo de tipo String tipoTransaccion de la
	 * clase
	 * 
	 * @return Atributo de tipo String tipoTransaccion de la clase
	 */
	public String getTipoTransaccion() {
		return tipoTransaccion;
	}

	/**
	 * Metodo que permite asignar un valor al atributo de tipo String
	 * tipoTransaccion de la clase
	 * 
	 * @param tipoTransaccion Variable asignada al atributo de tipo String
	 *                        tipoTransaccion de la clase
	 */
	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	/**
	 * Metodo que permite obtener el atributo de tipo Cliente garante de la clase
	 * 
	 * @return Atributo de tipo Cliente garante de la clase
	 */

	/**
	 * Metodo que permite obtener el atributo de tipo boolean editable de la clase
	 * 
	 * @return Atributo de tipo boolean editable de la clase
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * Metodo que permite asignar un valor al atributo de tipo boolean editable de
	 * la clase
	 * 
	 * @param editable Variable asignada al atributo de tipo boolean editable de la
	 *                 clase
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	/**
	 * Metodo que permite crear un cliente
	 * 
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
	 * Metodo que permite validar la cedula de un cliente
	 * 
	 * @return Mensaje de confirmacion
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
	 * Metodo que permite generar el numero de cuenta
	 * 
	 * @return Numero de cuenta que se ha generado
	 */
	public String generarNumeroCuenta() {
		this.numeroCuenta = gestionCuenta.generarNumeroDeCuenta();
		return numeroCuenta;
	}

	/**
	 * Metodo que permite obtener el atributo de tipo String saldoCuenta de la clase
	 * 
	 * @return Atributo de tipo String saldoCuenta de la clase
	 */
	public String getSaldoCuenta() {
		return saldoCuenta;
	}

	public double getInteres() {
		return interes;
	}

	public void setInteres(double interes) {
		this.interes = interes;
	}

	/**
	 * Metodo que permite asignar un valor al atributo de tipo String saldoCuenta de
	 * la clase
	 * 
	 * @param saldoCuenta Variable asignada al atributo de tipo string saldoCuenta
	 *                    de la clase
	 */
	public void setSaldoCuenta(String saldoCuenta) {
		this.saldoCuenta = saldoCuenta;
	}

	public void handleClose(CloseEvent event) {
		addMessage(event.getComponent().getId() + " closed", "So you don't like nature?");
	}

	public void handleMove(MoveEvent event) {
		event.setTop(500);
		addMessage(event.getComponent().getId() + " moved", "Left: " + event.getLeft() + ", Top: " + event.getTop());
	}

	/**
	 * Metodo que permite asignar un valor para poder enviar un mensaje de
	 * confirmacion
	 * 
	 * @param summary Variable tipo String la cual sera el titulo de la confirmacion
	 * @param detail  Varibale tipo String en donde se almacenara la descripcion del
	 *                mensaje
	 */
	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
	 * Metodo que me permite obtener una lista de los clientes y asignarlo a la
	 * variable lstClientes de la clase
	 */
	public void listarClientes() {
		lstClientes = gestionCliente.listaClientes();
	}

	/**
	 * Metodo que permite cambiar el formato a una fecha
	 * 
	 * @param fecha Fecha a la que se cambia el formato
	 * @return Fecha cambiada el formato
	 */
	public String obtenerFecha(Date fecha) {
		DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return hourdateFormat.format(fecha);
	}

	/**
	 * Metodo que permite obtener las sesiones de una cliente
	 * 
	 * 
	 * @return Lista de sesiones que tiene el cliente
	 */
	public List<SesionCliente> cargarSesiones() {
		List<SesionCliente> lis = gestionSesion.obtenerSesionesCliente(cedulaParametro);
		if (lis != null) {
			lstSesionesCliente = lis;
			return lstSesionesCliente;
		}
		return null;
	}

	/**
	 * Metodo que me permite obtener un mensaje
	 * 
	 * @return Variable de tipo String que me devuelve un mensaje en especifico
	 */
	public String consultarTransacciones() {
		return "ConsultaTransacciones";
	}

	public List<Poliza> getLstPolizasAprobados() {
		return lstPolizasAprobados;
	}

	public void setLstPolizasAprobados(List<Poliza> lstPolizasAprobados) {
		this.lstPolizasAprobados = lstPolizasAprobados;
	}

	public int getCodigoPoliza() {
		return codigoPoliza;
	}

	public void setCodigoPoliza(int codigoPoliza) {
		this.codigoPoliza = codigoPoliza;
	}

	/**
	 * Metodo qe me permite validar entre fechas, y obtener una lista de
	 * transacciones entre dichas fechas
	 * 
	 * @throws Exception
	 */
	public void validarFechas() throws Exception {
		if (this.fechaInicio != null && this.fechaFinal != null) {

			DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String inicioF = hourdateFormat.format(fechaInicio);
			String finalF = hourdateFormat.format(fechaFinal);
			List<Transaccion> listaTrans = gestionTransaccion.obtenerTransaccionesFechaHora(cedulaParametro, inicioF,
					finalF);
			lstTransacciones = listaTrans;
			System.out.println("H" + lstTransacciones.size());
			System.out.println(cedulaParametro);
			System.out.println(new Date());
		}
	}

	/**
	 * Metodo que me devuelve el tipo de transaccion que se esta utilizando
	 */
	public void obtenerTransaccionesInicioFinal() {
		/*
		 * lstTransacciones =
		 * gestionUsuarios.obtenerTransaccionesFechaHora(buscarCuentaDeAhorro.getCliente
		 * ().getCedula(), fechaInicio, fechaFinal);
		 */
		System.out.println("Este es el tipo de transaccion : " + tipoTransaccion);

	}

	/**
	 * Metodo que permite obtener una lista de transacciones entre una fecha de
	 * inicio y una fecha final
	 */
	public void ultimosDias() {
		Calendar c = Calendar.getInstance();
		fechaFinal = c.getTime();
		c.add(Calendar.DATE, -30);
		fechaInicio = c.getTime();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String inicioF = hourdateFormat.format(fechaInicio);
		String finalF = hourdateFormat.format(fechaFinal);
		List<Transaccion> listaTrans = gestionTransaccion.obtenerTransaccionesFechaHora(cedulaParametro, inicioF,
				finalF);
		lstTransacciones = listaTrans;
		System.out.println(lstTransacciones.size());
		System.out.println(cedulaParametro);
	}

	/**
	 * Metodo que permite obtener las transacciones entre una fechas, las cuales se
	 * obtienen dependiendo el tipo de transaccion que se requiera
	 * 
	 * @throws Exception
	 */
	public void validarFechas2() throws Exception {
		System.out.println(tipoTransaccion);

		if (this.fechaInicio != null && this.fechaFinal != null) {

			if (errorFechas() == null) {
				fechasInvalidas = errorFechas();
				DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String inicioF = hourdateFormat.format(fechaInicio);
				String finalF = hourdateFormat.format(fechaFinal);
				List<Transaccion> listaTrans = gestionTransaccion.obtenerTransaccionesFechaHora(cedulaParametro,
						inicioF, finalF);

				if (tipoTransaccion != null) {
					if (tipoTransaccion.equals("Todos")) {
						lstTransacciones = listaTrans;
					} else if (tipoTransaccion.equals("Depositos")) {
						lstTransacciones = new ArrayList<Transaccion>();
						for (Transaccion transaccion : listaTrans) {
							if (transaccion.getTipo().equals("deposito")) {
								lstTransacciones.add(transaccion);
							}
						}
					} else {
						lstTransacciones = new ArrayList<Transaccion>();
						for (Transaccion transaccion : listaTrans) {
							if (transaccion.getTipo().equals("retiro")) {
								lstTransacciones.add(transaccion);
							}
						}
					}
				}
			} else {
				fechasInvalidas = errorFechas();
				lstTransacciones.removeAll(lstTransacciones);
			}

		}

		System.out.println("TRANSACCIONES REALIZADAS :   " + lstTransacciones.size());
	}

	/**
	 * Metodo que permite verificar que la fecha de inicio no sea mayor a la fecha
	 * final
	 * 
	 * @return Variable de tipo String, el cual me dice si la fecha de inicio es
	 *         mayor
	 */
	public String errorFechas() {

		Date fechaInicioDate = this.fechaInicio; // String a date
		Date fechaFinDate = this.fechaFinal; // String a date

		System.out.println("Inicial: " + fechaInicioDate);
		System.out.println("Final: " + fechaFinDate);
		// comprueba si es que inicio esta después que fecha actual
		if (fechaInicioDate.after(fechaFinDate)) {
			return "Fecha inicio mayor";
		}
		return null;
	}

	/**
	 * Metodo que me permite asignar un archivo al atributo de tipo InputStream
	 * arCedula de la clase
	 * 
	 * @param event Variable de tipo FileUploadEvent
	 * @throws IOException
	 */
	public void archivo1(FileUploadEvent event) throws IOException {
		FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		arCedula = event.getFile().getInputStream();
	}

	/**
	 * Metodo que me permite asignar un archivo al atributo de tipo InputStream
	 * arPlanillaServicios de la clase
	 * 
	 * @param event Variable de tipo FileUploadEvent
	 * @throws IOException
	 */
	public void archivo2(FileUploadEvent event) throws IOException {
		FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		arPlanillaServicios = event.getFile().getInputStream();
	}

	/**
	 * Metodo que me permite asignar un valor a las variables codigoPoliza de la
	 * clase, y editable de la clase
	 * 
	 * @param cod Variable que se asigna ala variable codigoPoliza de tipo int dde
	 *            la clase
	 */
	public void cambioVar(int cod) {
		codigoPoliza = cod;
		editable = true;
	}

	/**
	 * Metodo que permite obtener una lista de Polizas aprovados, en donde se pasa
	 * la cedula del cliente de quien se desea obtener los Polizas aprovados. La
	 * lista de Polizas aprovados se asigana a la variable de tipo list
	 * lstPolizasAprovados de la clase
	 * 
	 * @param cedula Variable en donde se asigna la cedula del cliente de quien se
	 *               desea obtener los Polizas aprovados
	 */
	public void PolizasAprovados(String cedula) {
		System.out.println("ENTRO EN ESTE PINCHE METODO" + cedulaParametro);
		lstPolizasAprobados = gestionPolizas.polizasAprovados(cedula);
	}

	public GestionClienteONLocal getGestionCliente() {
		return gestionCliente;
	}

	public void setGestionCliente(GestionClienteONLocal gestionCliente) {
		this.gestionCliente = gestionCliente;
	}

	public GestionCuentaONLocal getGestionCuenta() {
		return gestionCuenta;
	}

	public void setGestionCuenta(GestionCuentaONLocal gestionCuenta) {
		this.gestionCuenta = gestionCuenta;
	}

	public GestionSesionONLocal getGestionSesion() {
		return gestionSesion;
	}

	public void setGestionSesion(GestionSesionONLocal gestionSesion) {
		this.gestionSesion = gestionSesion;
	}

	public GestionPolizaONLocal getGestionPolizas() {
		return gestionPolizas;
	}

	public void setGestionPolizas(GestionPolizaONLocal gestionPolizas) {
		this.gestionPolizas = gestionPolizas;
	}

	public double getTotalPoliza() {
		return totalPoliza;
	}

	public void setTotalPoliza(double totalPoliza) {
		this.totalPoliza = totalPoliza;
	}

	public Double getMontoPoliza() {
		return montoPoliza;
	}

	public void setMontoPoliza(Double montoPoliza) {
		this.montoPoliza = montoPoliza;
	}

	public String getMesesPoliza() {
		return mesesPoliza;
	}

	public void setMesesPoliza(String mesesPoliza) {
		this.mesesPoliza = mesesPoliza;
	}

	public GestionTransaccionesONLocal getGestionTransaccion() {
		return gestionTransaccion;
	}

	public void setGestionTransaccion(GestionTransaccionesONLocal gestionTransaccion) {
		this.gestionTransaccion = gestionTransaccion;
	}

	public InputStream getArCedula() {
		return arCedula;
	}

	public void setArCedula(InputStream arCedula) {
		this.arCedula = arCedula;
	}

	public InputStream getArPlanillaServicios() {
		return arPlanillaServicios;
	}

	public void setArPlanillaServicios(InputStream arPlanillaServicios) {
		this.arPlanillaServicios = arPlanillaServicios;
	}

	/**
	 * Metodo que permite guardar una solicitud de Poliza con sus respectivos
	 * atributos
	 * 
	 * @return Pagina en donde se realiza la Solicitud de Poliza
	 * @throws IOException
	 */
	public String crearSolicitudPoliza() throws IOException {
		System.out.println("ENTRO EN LA SOLICITUD");
		solicitudDePoliza.setClientePoliza(gestionCliente.buscarCliente(cedulaParametro));
		solicitudDePoliza.setEstadoPoliza("Solicitando");
		solicitudDePoliza.setArCedula(gestionPolizas.toByteArray(arCedula));
		solicitudDePoliza.setArPlanillaServicios(gestionPolizas.toByteArray(arPlanillaServicios));
		solicitudDePoliza.setTasaPago(obtenerInteres(Integer.parseInt(mesesPoliza)));
		System.out.println("valor de interz1111111111111111111111 "+  obtenerInteres(Integer.parseInt(mesesPoliza)));
		solicitudDePoliza.setTotalPoliza(calcular());
		solicitudDePoliza.setMontoPoliza(montoPoliza);
		solicitudDePoliza.setMesesPoliza(mesesPoliza);

		if (gestionPolizas.verificarSolicitudSolicitando(cedulaParametro)) {
			gestionPolizas.guardarSolicitudPoliza(solicitudDePoliza);
			addMessage("Confirmacion", "Solicitud Guardada");
		} else {
			addMessage("Atencion", "Usted ya ha enviado una solicitud de Poliza para su aprovacion");
		}
		solicitudDePoliza = new SolicitudDePoliza();
		return "SolicitudPoliza";
	}

	public double calcular() {
		int meses = Integer.parseInt(mesesPoliza);
		return ((montoPoliza * meses) * Integer.parseInt(mesesPoliza)) / 100;
	}

	/**
	 * metodo que permite saber cual es el interes de acuerdo al tiempo en dias
	 * 
	 * @param tiempo los dias que va a estar la poliza
	 * 
	 * @return
	 */
	public Double obtenerInteres(int tiempo) {
		double retorno = 0.00;
		if (tiempo >= 30 && tiempo <= 59) {
			retorno = 5.50;

		} else if (tiempo >= 60 && tiempo <= 89) {
			retorno = 5.75;
		} else if (tiempo >= 90 && tiempo <= 179) {
			retorno = 6.25;
		} else if (tiempo >= 180 && tiempo <= 269) {
			retorno = 7.00;
		} else if (tiempo >= 270 && tiempo <= 359) {
			retorno = 7.50;
		} else if (tiempo >= 360) {
			retorno = 8.50;
		}
		return retorno;

	}

}