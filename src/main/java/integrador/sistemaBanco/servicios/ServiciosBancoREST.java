package integrador.sistemaBanco.servicios;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import integrador.sistemaBanco.model.TransferenciaExterna;
import integrador.sistemaBanco.on.GestionClienteONLocal;
import integrador.sistemaBanco.on.GestionCuentaONLocal;
import integrador.sistemaBanco.on.GestionTransaccionesONLocal;
import integrador.sistemaBanco.utils.Respuesta;



@Path("/banco")
public class ServiciosBancoREST {

	@Inject
	private GestionTransaccionesONLocal on;
	@Inject
	private GestionClienteONLocal onCliente;
	@Inject
	private GestionCuentaONLocal onCuenta;
	@Inject
	private GestionTransaccionesONLocal onTransacciones;
	
	
	
	
	
	@POST
	@Path("/login")
	@Produces("application/json;charset=utf-8")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Respuesta login(@FormParam("username") String username, @FormParam("password") String password) {
		Respuesta respuesta = onCliente.loginServicio(username, password);
		return respuesta;
	} 
	
	@POST
	@Path("/cambiocontraseña")
	@Produces("application/json;charset=utf-8")
	@Consumes("application/json;charset=utf-8")
	public Respuesta cambioContraseña(@QueryParam("correo") String correo, @QueryParam("contraAntigua") 
	String contraAntigua, @QueryParam("contraActual") String contraActual) {
		Respuesta respuesta = onCliente.cambioContrasena(correo, contraAntigua, contraActual);
		return respuesta;
	} 
		
	@POST
	@Path("/transaccion")
	@Produces("application/json")
	@Consumes("application/json")
	public String realizarTransaccionBancaria(TransaccionRest transaccionRest) {
		return on.realizarTransaccion(transaccionRest.getCuenta(), transaccionRest.getMonto(),
				transaccionRest.getTipo());
	}

	@POST
	@Path("/transferencia")
	@Produces("application/json")
	@Consumes("application/json")
	public Respuesta realizarTransferencia(TransferenciaRest transferenciaRest) {
		return on.realizarTransferencia(transferenciaRest.getCedula(), transferenciaRest.getCuentaDeAhorro(),
				transferenciaRest.getMonto());
	} 
	
	@GET 
	@Path("/obtenerCliente") 
	@Produces("application/json") 
	public Respuesta obtenerCliente(@QueryParam("numeroCuenta") String numeroCuenta) { 
		return onCuenta.obtenerClienteCuentaAhorro(numeroCuenta);
	}
	
	@POST 
	@Path("/transferenciaExterna") 
	@Produces("application/json") 
	@Consumes("application/json") 
	public RespuestaTransferenciaExterna realizarTransferenciaExterna(TransferenciaExterna transferenciaExterna) { 
		return on.realizarTransferenciaExterna(transferenciaExterna);
	} 
	

}
