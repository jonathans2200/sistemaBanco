package integrador.sistemaBanco.on;

import java.util.List;

import integrador.sistemaBanco.model.CuentaDeAhorro;
import integrador.sistemaBanco.model.Transaccion;
import integrador.sistemaBanco.model.TransfereciaLocal;
import integrador.sistemaBanco.model.TransferenciaExterna;
import integrador.sistemaBanco.servicios.RespuestaTransferenciaExterna;
import integrador.sistemaBanco.utils.Respuesta;

public interface GestionTransaccionesONLocal {
	public List<Transaccion> listadeTransacciones(String cedula);
	public void guardarTransaccion(Transaccion t) throws Exception; 
	public List<Transaccion> obtenerTransaccionesFechaHora(String cedula, String fechaI, String fechaF);
	public Respuesta realizarTransferencia(String cedula, String cuentaAhorro2, double monto); 
	public String realizarTransaccion(String cuenta, double monto, String tipoTransaccion);
	public void guardarTransferenciaLocal(TransfereciaLocal transfereciaLocal);
	public void actualizarCuentaDeAhorro(CuentaDeAhorro cuentaDeAhorro);
	public RespuestaTransferenciaExterna realizarTransferenciaExterna(TransferenciaExterna transferenciaExterna);
	public String getDatos();
}
