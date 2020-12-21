package integrador.sistemaBanco.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *  Esta clase representa una entidad o tabla llamada detallePoliza de la base de datos y sus columnas
 * @author jonat
 *
 */

@Entity
public class DetallePoliza implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_desc")
	private int codigoDetalle;

	private int numeroCuota;
	private double interes;
	private double monto;
	private double saldo;
	private double cuota;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaPago;
	private String estado;
	public int getCodigoDetalle() {
		return codigoDetalle;
	}
	public void setCodigoDetalle(int codigoDetalle) {
		this.codigoDetalle = codigoDetalle;
	}
	public int getNumeroCuota() {
		return numeroCuota;
	}
	public void setNumeroCuota(int numeroCuota) {
		this.numeroCuota = numeroCuota;
	}
	public double getInteres() {
		return interes;
	}
	public void setInteres(double interes) {
		this.interes = interes;
	}
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public double getCuota() {
		return cuota;
	}
	public void setCuota(double cuota) {
		this.cuota = cuota;
	}
	public Date getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

}
