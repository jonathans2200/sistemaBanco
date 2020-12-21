package integrador.sistemaBanco.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *  Esta clase representa una entidad o tabla llamada poliza de la base de datos y sus columnas
 * @author jonat
 *
 */

@Entity
public class Poliza implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_cab")
	private int codigoCre;

	private String estado;
	private double monto;
	private double interes;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRegistro;

	@Temporal(TemporalType.TIMESTAMP)
	private Date tiempo;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaVencimiento;

	@OneToOne
	@JoinColumn(name = "jefe_Poliza")
	private Empleado jefeC;

	@OneToOne
	@JoinColumn(name = "codigo_Poliza")
	private SolicitudDePoliza solicitud;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "codigo_cabezera")
	private List<DetallePoliza> detalles;

	
	public List<DetallePoliza> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetallePoliza> detalles) {
		this.detalles = detalles;
	}

	public int getCodigoCre() {
		return codigoCre;
	}

	public void setCodigoCre(int codigoCre) {
		this.codigoCre = codigoCre;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public double getInteres() {
		return interes;
	}

	public void setInteres(double interes) {
		this.interes = interes;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getTiempo() {
		return tiempo;
	}

	public void setTiempo(Date tiempo) {
		this.tiempo = tiempo;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Empleado getJefeC() {
		return jefeC;
	}

	public void setJefeC(Empleado jefeC) {
		this.jefeC = jefeC;
	}

	public SolicitudDePoliza getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(SolicitudDePoliza solicitud) {
		this.solicitud = solicitud;
	}

	@Override
	public String toString() {
		return "Poliza [codigoCre=" + codigoCre + ", estado=" + estado + ", monto=" + monto + ", interes=" + interes
				+ ", fechaRegistro=" + fechaRegistro + ", tiempo=" + tiempo + ", fechaVencimiento=" + fechaVencimiento
				+ ", jefeC=" + jefeC + ", solicitud=" + solicitud + "]";
	}

}
