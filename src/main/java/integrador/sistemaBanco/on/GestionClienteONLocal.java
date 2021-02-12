package integrador.sistemaBanco.on;

import java.util.List;

import javax.ejb.Local;

import integrador.sistemaBanco.model.Cliente;
import integrador.sistemaBanco.utils.Respuesta;

@Local
public interface GestionClienteONLocal {

	public void guardarCliente(Cliente c);

	public Cliente buscarCliente(String cedulaCliente);

	public void eliminarCliente(String cedulaCliente);

	public Cliente buscarClienteUsuariocontrasena(String usuario, String contrasena);

	public Respuesta loginServicio(String username, String password);

	public void actualizarCliente(Cliente cliente);

	public List<Cliente> listaClientes();

	public boolean validadorDeCedula(String cedula) throws Exception;

	public void cambiarContrasena(Cliente cliente);

	public Respuesta cambioContrasena(String correo, String contraAntigua, String contraActual);

	
}
