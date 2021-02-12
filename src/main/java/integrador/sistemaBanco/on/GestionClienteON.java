package integrador.sistemaBanco.on;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.ejb.Stateless;
import javax.inject.Inject;
import integrador.sistemaBanco.on.GestionClienteONLocal;
import integrador.sistemaBanco.servicios.PolizaRespuesta;
import integrador.sistemaBanco.utils.Respuesta;
import integrador.sistemaBanco.dao.ClienteDAO;
import integrador.sistemaBanco.dao.CuentaDeAhorroDAO;
import integrador.sistemaBanco.model.Cliente;
import integrador.sistemaBanco.model.CuentaDeAhorro;
import integrador.sistemaBanco.model.Poliza;
/**
 * clase donde tendremos nuestro objeto de negocios para la gestion de Cliente
 * @author jonat
 *
 */
@Stateless
public class GestionClienteON implements GestionClienteONLocal  {
	@Inject
	private ClienteDAO clienteDAO;
	
	@Inject 
	private GestionPolizaONLocal on;
	
	@Inject
	private CuentaDeAhorroDAO ahorrosDAO;

	/**
	 * Metodo que me permite guardar el cliente en la base de datos
	 * 
	 * @param c Cliente que se guarda en la base de datos
	 */
	public void guardarCliente(Cliente c) {
		clienteDAO.insert(c);

	}

	/**
	 * Metodo que permite la busqueda de un cliente
	 * 
	 * @param cedulaCliente Cedula del cliente que se busca
	 * @return Cliente obtenido de la busqueda
	 */
	public Cliente buscarCliente(String cedulaCliente) {
		Cliente cliente = clienteDAO.read(cedulaCliente);
		return cliente;
	}

	/**
	 * Metodo que permite la busqueda del cliente en base a su usuario y contrasena
	 * 
	 * @param usuario    Usuario del cliente
	 * @param contrasena contrasena del cliente
	 * @return Cliente obtenido de la busqueda
	 */
	public Cliente buscarClienteUsuariocontrasena(String usuario, String contrasena) {
		try {
			return clienteDAO.obtenerClienteUsuariocontrasena(usuario, contrasena);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Metodo que permite eliminar un cliente
	 * 
	 * @param cedulaCliente Cedula del cliente que se elimina
	 */
	public void eliminarCliente(String cedulaCliente) {
		clienteDAO.delete(cedulaCliente);
	}

	/**
	 * Metodo que permite actualizar un cliente
	 * 
	 * @param cliente Cliente que se actualiza
	 */
	public void actualizarCliente(Cliente cliente) {
		clienteDAO.update(cliente);
	}

	/**
	 * Metodo que permite listar los clientes
	 * 
	 * @return Lista de todos los clientes
	 */
	public List<Cliente> listaClientes() {
		List<Cliente> clientes = clienteDAO.getClientes();
		return clientes;
	}

	/**
	 * Metodo para validacion
	 * 
	 * @param cedula El parmetro cedula sirve para la validacion de la una cedula
	 *               Ecuatoriana
	 * @return Si la cedula esta correcta o incorrecta en una variable booleana TRUE
	 *         o FALSE
	 * @throws Exception
	 */
	public boolean validadorDeCedula(String cedula) throws Exception {
		System.out.println(cedula + "    En Metodo ");
		boolean cedulaCorrecta = false;
		try {
			if (cedula.length() == 10) // ConstantesApp.LongitudCedula
			{
				int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
				if (tercerDigito < 6) {
					int[] coefValCedula = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
					int verificador = Integer.parseInt(cedula.substring(9, 10));
					int suma = 0;
					int digito = 0;
					for (int i = 0; i < (cedula.length() - 1); i++) {
						digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
						suma += ((digito % 10) + (digito / 10));
					}
					if ((suma % 10 == 0) && (suma % 10 == verificador)) {
						cedulaCorrecta = true;
					} else if ((10 - (suma % 10)) == verificador) {
						cedulaCorrecta = true;
					} else {
						cedulaCorrecta = false;
					}
				} else {
					cedulaCorrecta = false;
				}
			} else {
				cedulaCorrecta = false;
			}
		} catch (NumberFormatException nfe) {
			cedulaCorrecta = false;
		} catch (Exception err) {
			cedulaCorrecta = false;
			throw new Exception("Error cedula");
		}
		if (!cedulaCorrecta) {
			return cedulaCorrecta;
			// throw new Exception("Cedula Incorrecta");

		}
		return cedulaCorrecta;
	}


	/**
	 * Metodo que permite dar acceso al cliente en la aplicación móvil mediante un servicio web.
	 * 
	 * @param username El nombre de usuario del cliente que se envio en el correo.
	 * @param password La contraseña del cliente que se envio en el correo de creación de la cuenta.
	 * @return Un clase Respuesta indicando los datos del desarrollo del proceso, con un codigo, una descripción.
	 * @throws Exception Excepción por si sucede algún error en el proceso.
	 */
	public Respuesta loginServicio(String username, String password) {
		Cliente cliente = new Cliente();
		Respuesta respuesta = new Respuesta();
		CuentaDeAhorro cuentaDeAhorro = new CuentaDeAhorro();
		List<Poliza> lstCreditos = new ArrayList<Poliza>();
		try {
			cliente = clienteDAO.obtenerClienteUsuariocontrasena(username, password);
			if (cliente != null) {
				respuesta.setCodigo(1);
				respuesta.setDescripcion("Ha ingresado exitosamente");
				respuesta.setCliente(cliente);
				cuentaDeAhorro = ahorrosDAO.getCuentaCedulaCliente(cliente.getCedula());
				respuesta.setCuentaDeAhorro(cuentaDeAhorro);
				lstCreditos = on.polizasAprovados(cliente.getCedula());
				List<PolizaRespuesta> lstNuevaCreditos = new ArrayList<PolizaRespuesta>();
				for (Poliza credito : lstCreditos) {
					PolizaRespuesta creditoRespuesta = new PolizaRespuesta();
					creditoRespuesta.setCodigoPoliza(credito.getCodigoPoliza());
					creditoRespuesta.setEstado(credito.getEstado());
					creditoRespuesta.setMonto(credito.getMonto());
					creditoRespuesta.setInteres(credito.getInteres());
					creditoRespuesta.setFechaRegistro(credito.getFechaRegistro());
					creditoRespuesta.setFechaVencimiento(credito.getFechaVencimiento());
					creditoRespuesta.setDetalles(credito.getDetalles());
					lstNuevaCreditos.add(creditoRespuesta);
				}
				respuesta.setListaPoliza(lstNuevaCreditos);
			}
		} catch (Exception e) {
			respuesta.setCodigo(2);
			respuesta.setDescripcion("Error " + e.getMessage());
		}
		return respuesta;
	}
	

	
	
	
	/**
	 * Metodo que permite indicar los datos para enviar mediante el correo el mensaje de cambio de contraseña.
	 * 
	 * @param cliente Una clase Cliente con los datos del cliente.
	 * @throws Exception Excepción por si sucede algún error en el proceso de envio.
	 */
	public void cambiarContrasena(Cliente cliente) {
		String destinatario = cliente.getCorreo();
		String asunto = "CAMBIO DE CONTRASEÑA";
		String cuerpo = "BANCONET                                              SISTEMA BANCARIO\n"
				+ "------------------------------------------------------------------------------\n"
				+ "              Estimado(a): " + cliente.getNombre().toUpperCase() + "          "
				+ cliente.getApellido().toUpperCase() + "\n"
				+ "------------------------------------------------------------------------------\n"
				+ "COOPERATIVA JAM le informa que su contraseña ha sido cambiada exitosamente.   \n"
				+ "                                                                              \n"
				+ "                   Su nueva contraseña es:   " + cliente.getClave() + "       \n"
				+ "                       Fecha: " + fecha() + "                                 \n"
				+ "                                                                              \n"
				+ "------------------------------------------------------------------------------\n";
		CompletableFuture.runAsync(() -> {
			try {
			GestionCorreoON correo=new GestionCorreoON();
				correo.enviarCorreo(destinatario, asunto, cuerpo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

//			} 
	}
	
	/**
	 * Metodo que permite cambiar la contraseña del cliente en la aplicación móvil mediante un servicio web.
	 * 
	 * @param correo El correo del cliente que describio cuando creo una cuenta de ahorros.
	 * @param contraAntigua La contraseña del cliente antigua.
	 * @param contraActual La contraseña del cliente nueva.
	 * @return Un clase Respuesta indicando los datos del desarrollo del proceso, con un codigo, una descripción.
	 * @throws Exception Excepción por si sucede algún error en el proceso.
	 */
	
	public Respuesta cambioContrasena(String correo, String contraAntigua, String contraActual) {
		System.out.println(correo + "" + contraAntigua);
		Cliente cliente = new Cliente();
		Respuesta respuesta = new Respuesta();
		try {
			cliente = clienteDAO.obtenerClienteCorreocontrasena(correo, contraAntigua);
			System.out.println(cliente.toString());
			cliente.setClave(contraActual);
			clienteDAO.update(cliente);
			respuesta.setCodigo(1);
			respuesta.setDescripcion("Se ha actualizado su contraseña exitosamente"); 
			cambiarContrasena(cliente);
		} catch (Exception e) {
			respuesta.setCodigo(2);
			respuesta.setDescripcion("Error " + e.getMessage());
		}

		return respuesta;
	} 
	/**
	 * Metodo que permite cambiar el formato de la fecha
	 * 
	 * @return Fecha con nuevo formato
	 */
	public String fecha() {
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return hourdateFormat.format(date);
	}

}
