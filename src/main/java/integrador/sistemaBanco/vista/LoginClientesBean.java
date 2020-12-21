package integrador.sistemaBanco.vista;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import integrador.sistemaBanco.model.Cliente;
import integrador.sistemaBanco.model.SesionCliente;
import integrador.sistemaBanco.on.GestionClienteON;
import integrador.sistemaBanco.on.GestionSesionON;

/**
 * Esta clase implementa la logica que se utilizara en las diferentes interfaces
 * para poder utilizar las entidades o clases
 * 
 * @author jonat
 * @version 1.0
 */
@Named
@SessionScoped
public class LoginClientesBean implements Serializable{
	// Atributos de la clase
	@Inject
	private GestionSesionON gestionUsuario;
	@Inject
	private GestionClienteON gestionCliente;

	private Cliente cliente;
	private String usuario;
	private String contrasena;

	/**
	 * Metodo que permite inicializar atributos y metodos al momento que se llama a
	 * esta clase
	 */
	@PostConstruct
	public void init() {
		cliente = new Cliente();
	}

	public GestionSesionON getGestionUsuario() {
		return gestionUsuario;
	}

	public void setGestionUsuario(GestionSesionON gestionUsuario) {
		this.gestionUsuario = gestionUsuario;
	}

	public GestionClienteON getGestionCliente() {
		return gestionCliente;
	}

	public void setGestionCliente(GestionClienteON gestionCliente) {
		this.gestionCliente = gestionCliente;
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
	 * @param cliente Variable asignada al atributo cliente de la clase
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * Metodo que permite obtener el atributo usuario
	 * 
	 * @return Atributo usuario de la clase
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Metodo que permite asignar un valor al atributo usuario
	 * 
	 * @param usuario Variable asignada al atributo usuario de la clase
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Metodo que permite obtener el atributo contrasena
	 * 
	 * @return Atributo contrasena de la clase
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * Metodo que permite asignar un valor al atributo contrasena
	 * 
	 * @param contrasena Variable asignada al atributo contrasena de la clase
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	/**
	 * Metodo que permite guardar una sesion
	 * 
	 * @return Nombre de Pagina a donde se va a redirigir la pagina
	 */
	public String validarCliente() {
		List<Cliente> lstClis = gestionCliente.listaClientes();
		System.out.println("--------------PASO POR LA LISTA-----------");
		for (Cliente c : lstClis) {
			System.out.println("------ENTRA EN EL FOR------------");
			if (c.getUsuario().equalsIgnoreCase(usuario) && c.getClave().equalsIgnoreCase(contrasena)) {
				System.out.println("-----ENTRO EN EL IF CORRECTO----------");
				SesionCliente sesionCliente = new SesionCliente();
				sesionCliente.setCliente(c);
				sesionCliente.setFechaSesion(new Date());
				sesionCliente.setEstado("Correcto");
				gestionUsuario.guardarSesion(sesionCliente);
				try {
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cliente", c);
					FacesContext contex = FacesContext.getCurrentInstance();
					contex.getExternalContext()
							.redirect("PaginaPrincipalCliente.xhtml?faces-redirect=true&cedula=" + c.getCedula());
				} catch (Exception e) {
				}
			} else if (c.getUsuario().equalsIgnoreCase(usuario)) {
				System.out.println("------ENTRO EN EL IF INCORRECTO----");
				SesionCliente sesionCliente2 = new SesionCliente();
				sesionCliente2.setCliente(c);
				sesionCliente2.setFechaSesion(new Date());
				sesionCliente2.setEstado("Incorrecto");
				gestionUsuario.guardarSesion(sesionCliente2);
				return "InicioClientes";
			}
		}
		return "InicioClientes";
	}

	/**
	 * metodo  para finalizar la sesion he ir al login
	 * @return
	 */
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "InicioClientes?faces-redirect=true";
		// return null;

	}

}
