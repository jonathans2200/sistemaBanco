package integrador.sistemaBanco.on;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import integrador.sistemaBanco.dao.CuentaDeAhorroDAO;
import integrador.sistemaBanco.dao.TransaccionDAO;
import integrador.sistemaBanco.dao.TransferenciaExternaDAO;
import integrador.sistemaBanco.dao.TransferenciaLocalDAO;
import integrador.sistemaBanco.model.CuentaDeAhorro;
import integrador.sistemaBanco.model.Transaccion;
import integrador.sistemaBanco.model.TransfereciaLocal;
import integrador.sistemaBanco.model.TransferenciaExterna;
import integrador.sistemaBanco.servicios.RespuestaTransferenciaExterna;
import integrador.sistemaBanco.utils.Respuesta;

/**
 * clase donde tendremos nuestro objeto de negocios para la gestion de transacciones locales y externas
 * @author jonat
 *
 */


@Stateless
public class GestionTransaccionesON  implements GestionTransaccionesONLocal{
	@Inject
	private TransaccionDAO transaccionDAO;
	@Inject
	private TransferenciaLocalDAO transferenciaLocalDAO;
	@Inject
	private CuentaDeAhorroDAO cuentaDeAhorroDAO;
	@Inject 
	private TransferenciaExternaDAO externaDao;

	/**
	 * Metodo para obtener una Lista de Transacciones
	 * 
	 * @param cedula El parametro cedula me permite obtener la lista de
	 *               transacciones de acuedo al parametro
	 * @return Lista de Transacciones que realizo un Cliente de acuerdo al parametro
	 */
	public List<Transaccion> listadeTransacciones(String cedula) {
		try {
			return transaccionDAO.getListaTransacciones(cedula);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Metodo para guardad una Transaccion
	 * 
	 * @param t El parametro t me permite registrar una Transaccion de acuerdo al
	 *          parametro
	 * @throws Exception Excepcion para un fallo en el registro de la Transaccion
	 */
	public void guardarTransaccion(Transaccion t) throws Exception {

		try {
			transaccionDAO.insert(t);
		} catch (Exception e) {
			throw new Exception(e.toString());
		}
	}

	/**
	 * Metodo que permite buscar las transacciones de un usuario entre fechas
	 * 
	 * @param cedula Numero de cedula de la persona que busca
	 * @param fechaI La fecha de inicio desde donde se quieren ver las
	 *               transacciones.
	 * @param fechaF La fecha de fin hasta donde se quieren ver las transacciones.
	 * @return Una lista de las transacciones/movimientos del usuario entre las
	 *         fechas indicadas.
	 * Exception Excepción por si el cliente no tiene transacciones.
	 */
	public List<Transaccion> obtenerTransaccionesFechaHora(String cedula, String fechaI, String fechaF) {
		String fechaInicio = fechaI + " 00:00:00.000000";
		String fechaFinal = fechaF + " 23:59:59.000000";
		try {
			return transaccionDAO.getListaTransaccionesFechas(cedula, fechaInicio, fechaFinal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Metodo que permite realizar una transacción por parte del cajero
	 * 
	 * @param cuenta          Numero de cuenta de la persona a la que se hace la
	 *                        transacción.
	 * @param monto           El valor de transacción.
	 * @param tipoTransaccion El tipo de transacción que se realiza depósito o
	 *                        retiro;
	 * @return Un mensaje indicado si se completo correctamente el proceso o algo
	 *         error que pueda ocurrir.
	 *  Exception Excepción por si sucede algún error.
	 */

	public String realizarTransaccion(String cuenta, double monto, String tipoTransaccion) {
		CuentaDeAhorro clp = cuentaDeAhorroDAO.read(cuenta);
		if (clp != null) {
			if (tipoTransaccion.equalsIgnoreCase("deposito")) {
				Double nvmonto = clp.getSaldoCuentaDeAhorro() + monto;
				clp.setSaldoCuentaDeAhorro(nvmonto);
				actualizarCuentaDeAhorro(clp);
				Transaccion t = new Transaccion();
				t.setCliente(clp.getCliente());
				t.setMonto(monto);
				t.setFecha(new Date());
				t.setTipo("deposito");
				t.setSaldoCuenta(nvmonto);
				try {
					// editable = false;
					guardarTransaccion(t);
					return "Hecho";
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.getMessage();
				}
			} else if (tipoTransaccion.equalsIgnoreCase("retiro") && monto <= clp.getSaldoCuentaDeAhorro()) {
				Double nvmonto2 = clp.getSaldoCuentaDeAhorro() - monto;
				clp.setSaldoCuentaDeAhorro(nvmonto2);
				actualizarCuentaDeAhorro(clp);
				Transaccion t2 = new Transaccion();
				t2.setCliente(clp.getCliente());
				t2.setMonto(monto);
				t2.setFecha(new Date());
				t2.setTipo("retiro");
				t2.setSaldoCuenta(nvmonto2);
				try {
					guardarTransaccion(t2);
					return "Hecho";
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.getMessage();
				}
			} else {
				return "Monto exedido";
			}
		} else {
			return "Cuenta Inexistente";
		}
		return "Fallido";
	}

	/**
	 * Metodo que permite realizar una transferencia
	 * 
	 * @param cedula        Numero de cedula de la persona que hace la
	 *                      transferencia.
	 * @param cuentaAhorro2 El numero de cuenta de la persona a la que se hace la
	 *                      transferencia.
	 * @param monto         El valor de la transferencia.
	 * @return Un clase Respuesta indicando los datos del desarrollo del proceso,
	 *         con un codigo, una descripción.
	 * Exception Excepción por si sucede algún error en el proceso.
	 */
	public Respuesta realizarTransferencia(String cedula, String cuentaAhorro2, double monto) {
		Respuesta respuesta = new Respuesta();
		CuentaDeAhorro cuentaAhorro = cuentaDeAhorroDAO.getCuentaCedulaCliente(cedula);
		CuentaDeAhorro cuentaAhorroTransferir = cuentaDeAhorroDAO.read(cuentaAhorro2);
		try {
			if (cuentaAhorro.getSaldoCuentaDeAhorro() >= monto) {
				cuentaAhorro.setSaldoCuentaDeAhorro(cuentaAhorro.getSaldoCuentaDeAhorro() - monto);
				actualizarCuentaDeAhorro(cuentaAhorro);
				cuentaAhorroTransferir.setSaldoCuentaDeAhorro(cuentaAhorroTransferir.getSaldoCuentaDeAhorro() + monto);
				actualizarCuentaDeAhorro(cuentaAhorroTransferir);
				TransfereciaLocal transfereciaLocal = new TransfereciaLocal();
				transfereciaLocal.setCliente(cuentaAhorro.getCliente());
				transfereciaLocal.setCuentaDeAhorroDestino(cuentaAhorroTransferir);
				transfereciaLocal.setMonto(monto);
				guardarTransferenciaLocal(transfereciaLocal);
				respuesta.setCodigo(1);
				respuesta.setDescripcion("Transferencia Satisfactoria");
			} else {
				respuesta.setCodigo(2);
				respuesta.setDescripcion("Monto Excedido");
			}
		} catch (Exception e) {
			respuesta.setCodigo(3);
			respuesta.setDescripcion(e.getMessage());
		}
		return respuesta;
	}

	/**
	 * Método que permite guardar una transferencia local.
	 * 
	 * @param transfereciaLocal Una clase TransferenciaLocal para realizar el
	 *                          proceso de guardado.
	 */

	public void guardarTransferenciaLocal(TransfereciaLocal transfereciaLocal) {
		transferenciaLocalDAO.insert(transfereciaLocal);
	}

	/**
	 * Metodo que permite actualizar una cuenta de ahorros
	 * 
	 * @param cuentaDeAhorro Cuenta de Ahorros que se desea actualizar
	 */
	public void actualizarCuentaDeAhorro(CuentaDeAhorro cuentaDeAhorro) {
		cuentaDeAhorroDAO.update(cuentaDeAhorro);
	}

	/**
	 * Método que permite obtener los datos de los tipos de cliente para hacer una gráfica, del servicio web de Django.
	 * 
	 * @return Un mensaje indicando los resultados separados por ";" para su posterior gráfica.
	 */
	public String getDatos() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://35.238.98.31:8000/apiAnalisis/verDiagrama/");
		String res = target.request().get(String.class);
		client.close();
		return res;
	}
	/**
	 * Método que permite realizar una transferencia externa en la aplicación móvil mediante un servicio web.
	 * 
	 * @param transferenciaExterna Una clase TransferenciaExterna que se envia en formato json  mediante el servicio web.
	 * @return Un clase RespuestaTransferenciaExterna indicando los datos del desarrollo del proceso, con un codigo, una descripción.
	 * @throws Exception Excepción por si sucede algún error en el proceso.
	 */
	public RespuestaTransferenciaExterna realizarTransferenciaExterna(TransferenciaExterna transferenciaExterna) {  
		RespuestaTransferenciaExterna respuestaTransferenciaExterna = new RespuestaTransferenciaExterna();
		try {  
			CuentaDeAhorro cuentaDeAhorro = cuentaDeAhorroDAO.read(transferenciaExterna.getCuentaPersonaLocal()); 
			if(cuentaDeAhorro!=null) { 
				if(cuentaDeAhorro.getSaldoCuentaDeAhorro()>=transferenciaExterna.getMontoTransferencia()) { 
					transferenciaExterna.setFechaTransaccion(new Date());
					externaDao.insert(transferenciaExterna);  
					cuentaDeAhorro.setSaldoCuentaDeAhorro(cuentaDeAhorro.getSaldoCuentaDeAhorro()-transferenciaExterna.getMontoTransferencia()); 
					cuentaDeAhorroDAO.update(cuentaDeAhorro);
					respuestaTransferenciaExterna.setCodigo(1); 
					respuestaTransferenciaExterna.setDescripcion("Transferencia se ha realizado exitosamente"); 
				}else { 
					respuestaTransferenciaExterna.setCodigo(2);
					respuestaTransferenciaExterna.setDescripcion("No tiene esa cantidad en su cuenta");
				}
			}else { 
				respuestaTransferenciaExterna.setCodigo(3); 
				respuestaTransferenciaExterna.setDescripcion("La cuenta no existe");
			}
		}catch (Exception e) {
			respuestaTransferenciaExterna.setCodigo(4); 
			respuestaTransferenciaExterna.setDescripcion("Error : " + e.getMessage());
		}
		return respuestaTransferenciaExterna;
	}

	
}
