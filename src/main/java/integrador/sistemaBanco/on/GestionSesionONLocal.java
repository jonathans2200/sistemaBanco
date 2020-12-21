package integrador.sistemaBanco.on;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import integrador.sistemaBanco.model.SesionCliente;
@Local
public interface GestionSesionONLocal {
	public void guardarSesion(SesionCliente sesionCliente,int fallidos);
	public SesionCliente buscarSesionCliente(int codigoSesionCliente) ;
	public List<SesionCliente> obtenerSesionesCliente(String cedulaCliente) ;
	
}
