package integrador.sistemaBanco.vista;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;

import javax.faces.context.FacesContext;
import javax.inject.Named;

import integrador.sistemaBanco.model.Cliente;
import integrador.sistemaBanco.model.Empleado;



@Named
@RequestScoped
public class VerificarSesionBean implements Serializable {

	public void verificarSession() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			Cliente cliente = (Cliente) context.getExternalContext().getSessionMap().get("cliente");

			if (cliente == null) {
				context.getExternalContext().redirect("InicioClientes.xhtml");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void verificarSessionAdmin(){
	        try {
	            FacesContext context = FacesContext.getCurrentInstance();
				Empleado  empleado= (Empleado) context.getExternalContext().getSessionMap().get("empleado");
	            if (empleado == null) {
	                context.getExternalContext().redirect("InicioUsuarios.xhtml");
	            }
	            
	        } catch (Exception e) {
	            System.out.println(e.getMessage());

	        } 
	}
}
