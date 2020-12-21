package integrador.sistemaBanco.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import integrador.sistemaBanco.model.DetallePoliza;
@Stateless
public class DetallePolizaDAO {
	@PersistenceContext(name = "sistemaBancoPersistenceUnit") 
	private EntityManager em;
	
	/**
	 * Metodo para guardar un DetallePoliza
	 * @param s El parametro s me permite asignar los datos del Detalle Poliza
	 */
	public void insert(DetallePoliza s) {
		em.persist(s);
	}
	
	/**
	 *Metodo para actualizar el Detalle Poliza
	 * @param s El parametro s me permite asignar los nuevos valores 
	 * a un Detalle Poliza
	 */
	public void update(DetallePoliza s) {
		em.merge(s);
	} 
	
	/**
	 * Metodo para obtener un Detalle Poliza
	 * 
	 * @param codigoPoliza El parametro codigoPoliza me permite obtener el Detalle
	 *                     Poliza con el codigo igual al paremetro
	 *
	 */
	public DetallePoliza read(int codigoPoliza) {
		return em.find(DetallePoliza.class, codigoPoliza);
	} 
	
	/**
	 * Metodo para eliminar un Poliza
	 * @param codigoPoliza El parametro codigoPoliza me permite eliminar el Detalle Poliza con el codigo
	 * igual al paremetro 
	 */
	public void delete(int codigoPoliza) {
		DetallePoliza c = read(codigoPoliza);
		em.remove(c);
	}
	
	/**
	 * Metodo para obtener los Detalle Poliza de la aplicacion
	 * @return Una lista de Detalles de Polizas
	 */
	public List<DetallePoliza> getDetallesPolizas() {
		String jpql = "SELECT s FROM DetallePoliza s ";

		Query q = em.createQuery(jpql, DetallePoliza.class);
		return q.getResultList();
	}
}
