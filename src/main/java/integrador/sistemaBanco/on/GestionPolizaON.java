package integrador.sistemaBanco.on;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import integrador.sistemaBanco.dao.CuentaDeAhorroDAO;
import integrador.sistemaBanco.dao.DetallePolizaDAO;
import integrador.sistemaBanco.dao.PolizaDAO;
import integrador.sistemaBanco.dao.SolicitudDePolizasDAO;
import integrador.sistemaBanco.model.Cliente;
import integrador.sistemaBanco.model.CuentaDeAhorro;
import integrador.sistemaBanco.model.DetallePoliza;
import integrador.sistemaBanco.model.Poliza;
import integrador.sistemaBanco.model.SolicitudDePoliza;
/**
 * clase donde tendremos nuestro objeto de negocios para la gestion de polizas
 * @author jonat
 *
 */
@Stateless
public class GestionPolizaON {
	@Inject
	private PolizaDAO polizaDAO;
	@Inject
	private DetallePolizaDAO detallePolizaDAO;
	@Inject
	private SolicitudDePolizasDAO solicitudDePolizaDAO;
	@Inject
	private CuentaDeAhorroDAO cuentaDAO;

	GestionCorreoON correo;;

	/**
	 * Método que permite guardar una solicitud de Poliza conjuntamente con el
	 * procesado para determinar el tipo de cliente.
	 * 
	 * @param solicituDePoliza Una clase SolicitudDePoliza para realizar el proceso
	 *                         de guardado.
	 * ForbiddenException   Una excepción de tiempo de ejecución que indica
	 *                              que el servidor ha prohibido el acceso a un
	 *                              recurso solicitado por un cliente.
	 * InterruptedException Se lanza cuando un hilo está esperando,
	 *                              durmiendo u ocupado de otra manera, y el hilo se
	 *                              interrumpe, ya sea antes o durante la actividad.
	 *  ExecutionException   Se lanza una excepción al intentar recuperar el
	 *                              resultado de una tarea que se canceló al lanzar
	 *                              una excepción
	 */

	public void guardarSolicitudPoliza(SolicitudDePoliza solicituDePoliza) {
		solicituDePoliza.setHistorialPoliza(solicituDePoliza.getHistorialPoliza());
		solicituDePoliza.setSaldoCuenta(String.valueOf(saldoCuenta(solicituDePoliza)));
		solicituDePoliza.setAnosCliente(obtenerEdad(solicituDePoliza.getClientePoliza().getFechaNacimiento()));
		solicituDePoliza.setCantidadPolizas(numeroPolizas(solicituDePoliza));

		String poliza = "{\"POLIZA \":\"" + solicituDePoliza.getClientePoliza().getCedula() + ";"
				+ String.valueOf(solicituDePoliza.getMesesPoliza()) + ";" + solicituDePoliza.getHistorialPoliza() + ";"
				+ String.valueOf(solicituDePoliza.getMontoPoliza()) + ";" + solicituDePoliza.getSaldoCuenta() + ";"
				+ String.valueOf(solicituDePoliza.getTasaPago()) + ";" + (solicituDePoliza.getActivo()) + ";"
				+ String.valueOf(solicituDePoliza.getAnosCliente()) + ";"
				+ String.valueOf(solicituDePoliza.getCantidadPolizas()) + ";\"}";
		try {
			solicitudDePolizaDAO.insert(solicituDePoliza);

		} catch (ForbiddenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void guardarPoliza(Poliza poliza) {
		polizaDAO.insert(poliza);
	}

	/**
	 * Método que permite actualizar un crédito;
	 * 
	 * @param poliza Una clase Poliza para realizar el proceso de actualizacion.
	 */

	public void actualizarPoliza(Poliza poliza) {
		polizaDAO.update(poliza);
	}

	/**
	 * Método que permite listar los créditos.
	 * 
	 * @return Una lista con clases Poliza con los datos de los créditos.
	 */

	/**
	 * Método que permite actualizar una solicitud de crédito.
	 * 
	 * @param SolicitudDePoliza Una clase SolicitudDePoliza para realizar el proceso
	 *                          de actualización.
	 */

	public void actualizarSolicitudPoliza(SolicitudDePoliza SolicitudDePoliza) {
		solicitudDePolizaDAO.update(SolicitudDePoliza);
	}

	/**
	 * Método que permite listar las solicitudes de crédito.
	 * 
	 * @return Un lista con clases SolicitudDePoliza con los datos de las
	 *         solicitudes de Poliza;
	 */

	public List<SolicitudDePoliza> listadoSolicitudDePolizas() {
		return solicitudDePolizaDAO.getSolicitudDePolizas();
	}

	public File generarTablaAmor(Poliza poliza) {
		try {
			Cliente cliente = poliza.getSolicitud().getClientePoliza();
			double monto = poliza.getMonto();
			double interes = poliza.getInteres();
			int meses = Integer.parseInt(poliza.getSolicitud().getMesesPoliza());
			Document document = new Document();

			File file = File.createTempFile("Tabla-Amortizacion", ".pdf");
			FileOutputStream fos = new FileOutputStream(file);
			PdfWriter.getInstance(document, fos);
			document.open();
			Paragraph par = new Paragraph();
			par.add(new Phrase("SISTEMA BANCARIO"));
			par.setAlignment(Element.ALIGN_CENTER);
			document.add(par);
			document.add(Chunk.NEWLINE);
			Paragraph par1 = new Paragraph();
			par1.add(new Phrase("TABLA DE AMORTIZACIÓN"));
			par1.setAlignment(Element.ALIGN_CENTER);
			document.add(par1);
			document.add(Chunk.NEWLINE);
			Paragraph par2 = new Paragraph();
			par2.add(new Phrase("               DETALLES DE POLIZA"));
			par2.add(Chunk.NEWLINE);
			par2.add(new Phrase("               Cliente: " + cliente.getNombre() + " " + cliente.getApellido()));
			par2.add(Chunk.NEWLINE);
			par2.add(new Phrase("               Fecha Registro: " + obtenerFecha2(poliza.getFechaRegistro())));
			par2.add(Chunk.NEWLINE);
			par2.add(new Phrase("               Fecha Vencimiento: " + obtenerFecha2(poliza.getFechaVencimiento())));
			par2.add(Chunk.NEWLINE);
			par2.add(new Phrase("               Monto: " + monto));
			par2.add(Chunk.NEWLINE);
			par2.add(new Phrase("               Interes: " + interes + "%"));
			par2.add(Chunk.NEWLINE);
			par2.add(new Phrase("               Plazo: " + meses + " meses"));
			document.add(par2);
			document.add(Chunk.NEWLINE);

			PdfPTable table = new PdfPTable(6);
			PdfPCell celdaInicial = new PdfPCell(new Paragraph("Detalles de las Cuotas"));
			celdaInicial.setColspan(6);
			celdaInicial.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(celdaInicial);
			PdfPCell ct1 = new PdfPCell(new Phrase("#Cuota"));
			ct1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(ct1);
			PdfPCell ct2 = new PdfPCell(new Phrase("Fecha"));
			ct2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(ct2);
			PdfPCell ct3 = new PdfPCell(new Phrase("Cuota"));
			ct3.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(ct3);
			PdfPCell ct4 = new PdfPCell(new Phrase("Capital"));
			ct4.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(ct4);
			PdfPCell ct5 = new PdfPCell(new Phrase("Interes"));
			ct5.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(ct5);
			PdfPCell ct6 = new PdfPCell(new Phrase("Saldo"));
			ct6.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(ct6);

			for (DetallePoliza dcre : poliza.getDetalles()) {
				PdfPCell cell1 = new PdfPCell(new Phrase(String.valueOf(dcre.getNumeroCuota())));
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell1);
				PdfPCell cell2 = new PdfPCell(new Phrase(obtenerFecha2(dcre.getFechaPago())));
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell2);
				PdfPCell cell3 = new PdfPCell(new Phrase(String.valueOf(cambiarDecimal(dcre.getSaldo()))));
				cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell3);
				PdfPCell cell4 = new PdfPCell(new Phrase(String.valueOf(cambiarDecimal(dcre.getCuota()))));
				cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell4);
				PdfPCell cell5 = new PdfPCell(new Phrase(String.valueOf(cambiarDecimal(dcre.getInteres()))));
				cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell5);
				PdfPCell cell6 = new PdfPCell(new Phrase(String.valueOf(cambiarDecimal(dcre.getMonto()))));
				cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell6);
			}
			document.add(table);

			document.close();
			return file;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Metodo que permite cambiar el formato de la fecha
	 * 
	 * @param fecha Fecha que se cambiara el formato
	 * @return La fecha en un formato requerido de tipo texto.
	 */

	public String obtenerFecha2(Date fecha) {
		DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return hourdateFormat.format(fecha);
	}

	/**
	 * Método que permite cambiar el formato de los nuemros que se generen.
	 * 
	 * @param valor El valor del double para transformar.
	 */
	public double cambiarDecimal(double valor) {
		String num = String.format(Locale.ROOT, "%.2f", valor);
		return Double.parseDouble(num);
	}

	/**
	 * Método que permite determinar el numero de polizas aprobados del cliente .
	 * 
	 * @param solicitudDePoliza Una clase solicitudDePoliza con los datos de la
	 *                          solicitud de polizas.
	 * @return Un mensaje indicando el numero de polizas del cliente.
	 */
	public int numeroPolizas(SolicitudDePoliza solicitudDePoliza) {
		List<Poliza> lstPolizas = polizaDAO.getPolizas();
		int contador = 0;
		for (Poliza Poliza : lstPolizas) {
			if (Poliza.getSolicitud().getClientePoliza().getCedula()
					.equalsIgnoreCase(solicitudDePoliza.getClientePoliza().getCedula())) {
				contador++;
			}
		}
		return contador;
	}

	/**
	 * Método que permite calcular la edad del cliente.
	 * 
	 * @param fechaNacimiento La fecha de nacimiento del cliente.
	 * @return La edad del cliente en base a su fecha de nacimiento.
	 */
	public int obtenerEdad(Date fechaNacimiento) {
		Calendar a = Calendar.getInstance();
		Calendar b = Calendar.getInstance();
		a.setTime(fechaNacimiento);
		b.setTime(new Date());
		int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
		if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH)
				|| (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
			diff--;
		}
		return diff;
	}

	/**
	 * Método que permite determinar el rango de saldo de cuenta del cliente.
	 * 
	 * @param solicitudDePoliza Una clase solicitudDePoliza con los datos de la
	 *                          solicitud de Poliza.
	 * @return Un mensaje indicado el valor del saldo de crédito en rangos.
	 */
	public Double saldoCuenta(SolicitudDePoliza solicitudDePoliza) {
		CuentaDeAhorro cuentaDeAhorro = cuentaDAO
				.getCuentaCedulaCliente(solicitudDePoliza.getClientePoliza().getCedula());
		if (cuentaDeAhorro != null) {
			double saldo = cuentaDeAhorro.getSaldoCuentaDeAhorro();

			return saldo;
		}
		return null;
	}

	/**
	 * Metodo que permite crear la tabla de amortización de un crédito aprobado que
	 * se convertiran en los detalles de un crédito.
	 * 
	 * @param cuotas  El numero de meses que el cliente indica cuando solicita un
	 *                Poliza.
	 * @param monto   El valor del Poliza indicado por el cliente en la solicitud.
	 * @param interes El valor calculado de los datos de la solicitud indicados por
	 *                el cliente en base a sus ingresos y egresos.
	 * @return Una lista con clases DetallePoliza con los datos de la tabla de
	 *         amortización.
	 */

	public List<DetallePoliza> crearTablaAmortizacion(int cuotas, double monto, double interes) {
		List<DetallePoliza> listaDet = new ArrayList<>();
		Date fecha = new Date();
		List<Date> fechas = new ArrayList<>();
		double vcuota = monto / cuotas;
		double icuota = monto * (interes / 100);
		for (int i = 0; i < cuotas; i++) {
			DetallePoliza detalle = new DetallePoliza();
			detalle.setEstado("Pendiente");
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(fecha); // Configuramos la fecha que se recibe
			calendar1.add(Calendar.MONTH, 1);
			fecha = calendar1.getTime();// numero de horas a añadir, o restar en caso de horas<0
			fechas.add(fecha);
			monto -= vcuota;
			detalle.setNumeroCuota(i + 1);
			detalle.setFechaPago(fecha);
			detalle.setInteres(cambiarDecimal(icuota));
			detalle.setSaldo(cambiarDecimal(vcuota + icuota));
			detalle.setMonto(cambiarDecimal(monto));
			detalle.setCuota(cambiarDecimal(vcuota));
			listaDet.add(detalle);
		}
		return listaDet;
	}

	/**
	 * Metodo que permite indicar los datos para enviar mediante el correo el
	 * rechazo de la solicitud de crédito.
	 * 
	 * @param cliente Una clase Cliente con los datos del cliente.
	 * @param razon   La descripción del rechazo de la solictud de Poliza.
	 *  Exception Excepción por si sucede algún error en el proceso de envio.
	 */
	public void rechazarPoliza(Cliente cliente, String razon) {
		String destinatario = cliente.getCorreo();
		String asunto = "RECHAZO DE POLIZA";
		String cuerpo = "--------SISTEMA BANCARIO ---------------\n"
				+ "------------------------------------------------------------------------------\n"
				+ "              Estimado(a): " + cliente.getNombre().toUpperCase() + " "
				+ cliente.getApellido().toUpperCase() + "\n"
				+ "------------------------------------------------------------------------------\n"
				+ "BANCONET le informa que su poliza no ha sido aprobado.                \n"
				+ "Los detalles del rechazo se muestran a continuación.                          \n"
				+ "                                   DETALLES                                   \n" + razon
				+ "						             \n"
				+ "                                                                              \n"
				+ "                                                                              \n"
				+ "------------------------------------------------------------------------------\n";
		CompletableFuture.runAsync(() -> {
			try {
				correo.enviarCorreo(destinatario, asunto, cuerpo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

//			} 
	}

	/**
	 * Metodo que permite indicar los datos para enviar mediante el correo de la
	 * aprobación de crédito.
	 * 
	 * @param poliza  Una clase Poliza con los datos del Poliza.
	 * @param cliente Una clase Cliente con los datos del cliente.
	 *  Exception Excepción por si sucede algún error en el proceso de envio.
	 */
	public void aprobarPoliza(Poliza poliza, Cliente cliente) {
		String destinatario = cliente.getCorreo();
		String asunto = "APROBACIÓN DE Poliza";
		String cuerpo = "------SISTEMA BANCARIO \n"
				+ "------------------------------------------------------------------------------\n"
				+ "              Estimado(a): " + cliente.getNombre().toUpperCase() + " "
				+ cliente.getApellido().toUpperCase() + "\n"
				+ "------------------------------------------------------------------------------\n"
				+ "BANCONET le informa que su Poliza ha sido aprobado.                   \n"
				+ "                                                                              \n"
				+ "                         Fecha: " + obtenerFecha(poliza.getFechaRegistro()) + "\n"
				+ "                                                                              \n"
				+ "La informacion de sus cuotas se encuentra en el archivo adjunto.              \n"
				+ "------------------------------------------------------------------------------\n";

		CompletableFuture.runAsync(() -> {
			try {
				correo.enviarCorreo2(destinatario, asunto, cuerpo, poliza);
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
	 * Método que permite obtener los Polizas aprovados para un cliente específico
	 * en base a su número de cédula.
	 * 
	 * 
	 * @return Una lista con clases Poliza con los datos de los créditos aprobados
	 *         del cliente en cuestión.
	 */

	public List<Poliza> polizasAprovados(String cedulaCliente) {
		List<Poliza> listaPolizas = polizaDAO.getPolizas();
		List<Poliza> listPolizaTotales = new ArrayList<Poliza>();
		for (Poliza Poliza : listaPolizas) {
			if (Poliza.getSolicitud().getClientePoliza().getCedula().equalsIgnoreCase(cedulaCliente)) {
				listPolizaTotales.add(Poliza);
			}
		}
		return listPolizaTotales;
	}

	/**
	 * Metodo que permite convertir una clase InputStream en un byte [] arreglo de
	 * bytes para su posterior guardado en la base de datos.
	 * 
	 * @param in Una clase InputStream que continue la información de un archivo que
	 *           se selecciona en el proceso de la solicitud de credito.
	 * @return Un clase byte [] un arreglo de bytes del InputStream pasado como
	 *         parametro.
	 * @throws IOException Excepción para el manejo de clases que tengan que ver con
	 *                     archivos.
	 */

	public byte[] toByteArray(InputStream in) throws IOException {

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		int len;

		// read bytes from the input stream and store them in buffer
		while ((len = in.read(buffer)) != -1) {
			// write bytes from the buffer into output stream
			os.write(buffer, 0, len);
		}

		return os.toByteArray();
	}

	/**
	 * Método que permite verificar una solicitud ce credito en base a la cedula del
	 * cliente.
	 * 
	 * @param cedulaCliente El numero de cédula del cliente.
	 * @return Un valor booleano que indica el estado de una solicitud.
	 */
	public boolean verificarSolicitudSolicitando(String cedulaCliente) {
		List<SolicitudDePoliza> solicitudes = solicitudDePolizaDAO.getSolicitudDePolizas();
		for (SolicitudDePoliza solicitudDeCredito : solicitudes) {
			if (solicitudDeCredito.getEstadoPoliza().equalsIgnoreCase("Solicitando")
					&& solicitudDeCredito.getClientePoliza().getCedula().equalsIgnoreCase(cedulaCliente)) {
				return false;
			}
		}
		return true;
	}


}