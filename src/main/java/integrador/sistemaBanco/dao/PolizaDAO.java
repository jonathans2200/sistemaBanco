package integrador.sistemaBanco.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import integrador.sistemaBanco.model.Poliza;



/**
 * Clase que me permite manejar la Entidad Poliza con la Base de Datos
 * Es para el manejo de los datos registrados en la Base de datos con respecto a la Entidad Poliza
 * @author jonat
 * @version 1.0
 */
@Stateless
public class PolizaDAO {
	
	@PersistenceContext(name = "sistemaBancoPersistenceUnit") 
	private EntityManager em;
	
	/**
	 * Metodo para guardar un Poliza
	 * @param s El parametro s me permite asignar los datos del Poliza
	 */
	public void insert(Poliza s) {
		em.persist(s);
	}
	
	/**
	 *Metodo para actualizar el Poliza
	 * @param s El parametro s me permite asignar los nuevos valores 
	 * a un Poliza
	 */
	public void update(Poliza s) {
		em.merge(s);
	} 
	
	/**
	 * Metodo para obtener un Poliza
	 * @param codigoPoliza El parametro codigoPoliza me permite obtener el Poliza con el codigo
	 * igual al paremetro 
	 * @returnn Un Poliza
	 */
	public Poliza read(int codigoPoliza) {
		return em.find(Poliza.class, codigoPoliza);
	} 
	
	/**
	 * Metodo para eliminar un Poliza
	 * @param codigoPoliza
	 * El parametro codigoPoliza me permite eliminar el Poliza con el codigo
	 * igual al paremetro 
	 */
	public void delete(int codigoPoliza) {
		Poliza c = read(codigoPoliza);
		em.remove(c);
	}
	
	/**
	 * Metodo para obtener los Polizas de la aplicacion
	 * @return Una lista de Polizas
	 */
	public List<Poliza> getPolizas() {
		String jpql = "SELECT s FROM Poliza s ";

		Query q = em.createQuery(jpql, Poliza.class);
		return q.getResultList();
	}

}
