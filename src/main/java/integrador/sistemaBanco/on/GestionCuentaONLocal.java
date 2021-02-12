package integrador.sistemaBanco.on;

import javax.ejb.Local;

import integrador.sistemaBanco.model.CuentaDeAhorro;
import integrador.sistemaBanco.utils.Respuesta;
@Local
public interface GestionCuentaONLocal {
	public String generarNumeroDeCuenta();

	public String getUsuario(String cedula, String nombre, String apellido);

	public String getcontrasena();

	public void guardarCuentaDeAhorros(CuentaDeAhorro c);

	public CuentaDeAhorro buscarCuentaDeAhorro(String numeroCuentaDeAhorro);

	public CuentaDeAhorro buscarCuentaDeAhorroCliente(String cedulaCliente);

	public void eliminarCuentaDeAhorro(String numeroCuentaDeAhorro);

	public void actualizarCuentaDeAhorro(CuentaDeAhorro cuentaDeAhorro);
	public Respuesta obtenerClienteCuentaAhorro(String numeroCuenta);
	
}
