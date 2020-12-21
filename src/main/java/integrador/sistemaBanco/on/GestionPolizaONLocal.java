package integrador.sistemaBanco.on;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import integrador.sistemaBanco.model.Cliente;
import integrador.sistemaBanco.model.DetallePoliza;
import integrador.sistemaBanco.model.Poliza;
import integrador.sistemaBanco.model.SolicitudDePoliza;

@Local
public interface GestionPolizaONLocal {

	public boolean verificarSolicitudSolicitando(String cedulaCliente);
	public byte[] toByteArray(InputStream in) throws IOException;
	public List<Poliza> polizasAprovados(String cedulaCliente);
	public String obtenerFecha(Date fecha);
	public void aprobarPoliza(Poliza poliza, Cliente cliente);
	public void rechazarPoliza(Cliente cliente, String razon);
	public List<DetallePoliza> crearTablaAmortizacion(int cuotas, double monto, double interes);
	public Double saldoCuenta(SolicitudDePoliza solicitudDePoliza);
	public int obtenerEdad(Date fechaNacimiento);
	public int numeroPolizas(SolicitudDePoliza solicitudDePoliza);
	public double cambiarDecimal(double valor);
	public File generarTablaAmor(Poliza poliza);
	public List<SolicitudDePoliza> listadoSolicitudDePolizas() ;
	public void actualizarSolicitudPoliza(SolicitudDePoliza SolicitudDePoliza);
	public void actualizarPoliza(Poliza poliza);
	public void guardarPoliza(Poliza poliza);
	public void guardarSolicitudPoliza(SolicitudDePoliza solicituDePoliza) ;
}
