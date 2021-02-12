package integrador.sistemaBanco.servicios;

import java.util.Date;
import java.util.List;

import integrador.sistemaBanco.model.DetallePoliza;

public class PolizaRespuesta {
	private int codigoCre;
	private String estado;
	private double monto;
	private double interes;
	private Date fechaRegistro;
	private Date fechaVencimiento;
    private List<DetallePoliza> detalles;
	public int getCodigoPoliza() {
		return codigoCre;
	}
	public void setCodigoPoliza(int codigoCre) {
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
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public List<DetallePoliza> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<DetallePoliza> detalles) {
		this.detalles = detalles;
	}
    
    
}
