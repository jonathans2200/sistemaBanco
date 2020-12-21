package integrador.sistemaBanco.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import integrador.sistemaBanco.model.SolicitudDePoliza;


/** 
 *  Esta clase me permite hacer las funciones basicas en una base de datos utilizando la clase SolicitudDePoliza
 * @author jona
 * @version 1.0
 */
@Stateless
public class SolicitudDePolizasDAO {
	//Atributo de la clase
	@PersistenceContext(name = "sistemaBancoPersistenceUnit")  
	private EntityManager em;
	/** 
	 * Metodo que permite registrar una Solicitud de Poliza en la base de datos
	 * @param s SolicitudDePoliza que se va a registrar en la base
	 */
	public void insert(SolicitudDePoliza s) {
		em.persist(s);
	}
	/** 
	 * Metodo que permite actualizar una Solicitud de Poliza en la base de datos
	 * @param s Solicitud de Poliza que se va a actualizar en la base
	 */
	public void update(SolicitudDePoliza s) {
		em.merge(s);
	} 
	/** 
	 * Metodo que permite obtener una Solicitud de Poliza de la base de datos
	 * @param codigoPoliza Codigo que se utilizara para obtener la Solicitud de Poliza
	 * @return Una Solicitud de Poliza que se encuentre registrado en la base
	 */
	public SolicitudDePoliza read(int codigoPoliza) {
		return em.find(SolicitudDePoliza.class, codigoPoliza);
	} 
	/** 
	 * Metodo que permite eliminar una Solicitud de Poliza de la base de datos
	 * @param codigoPoliza Codigo que se utiliza para poder eliminar la Solicitud de Poliza
	 */
	public void delete(int codigoPoliza) {
		SolicitudDePoliza c = read(codigoPoliza);
		em.remove(c);
	}
	/** 
	 * Metodo que permite obtener las Solicitudes de Poliza que estan registradas en la base de datos
	 * @return Lista de Solicitudes de Poliza que estan registradas en la base de datos
	 */
	public List<SolicitudDePoliza> getSolicitudDePolizas() {
		String jpql = "SELECT s FROM SolicitudDePoliza s ";

		Query q = em.createQuery(jpql, SolicitudDePoliza.class);
		return q.getResultList();
	} 
}
