package integrador.sistemaBanco.on;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Local;

import integrador.sistemaBanco.model.Empleado;

@Local
public interface GestionEmpleadoONLocal {
	public void guardarEmpleado(Empleado empleado) throws SQLException, Exception;
	public Empleado usuarioRegistrado(String cedula);
	public List<Empleado> listadoEmpleados();
	public Empleado usuario(String usuario, String contra) throws Exception;
	public boolean validadorDeCedula(String cedula) throws Exception;
}
