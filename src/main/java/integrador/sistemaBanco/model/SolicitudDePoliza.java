package integrador.sistemaBanco.model;

import java.io.File;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import org.primefaces.model.file.UploadedFile;

@Entity
public class SolicitudDePoliza {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codigoPoliza; 
	@OneToOne
	@JoinColumn(name="cedula_cliente")
	private Cliente clientePoliza;  
	
	private double montoPoliza; 
	private String mesesPoliza;
	private String estadoPoliza;   
	@Lob 
	@Column(length=16777216)
    private byte[] arCedula; 
	@Lob 
	@Column(length=16777216)
    private byte[] arPlanillaServicios; 
	private String historialPoliza; 
	private String saldoCuenta;  
	private double tasaPago;  
	private int añosCliente; 
	private double totalPoliza;
	private String activo; 
	private int cantidadPolizas; 
	private String tipoCliente;
	public int getCodigoPoliza() {
		return codigoPoliza;
	}
	
	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public void setCodigoPoliza(int codigoPoliza) {
		this.codigoPoliza = codigoPoliza;
	}
	
	public double getTotalPoliza() {
		return totalPoliza;
	}

	public void setTotalPoliza(double totalPoliza) {
		this.totalPoliza = totalPoliza;
	}

	public Cliente getClientePoliza() {
		return clientePoliza;
	}
	public void setClientePoliza(Cliente clientePoliza) {
		this.clientePoliza = clientePoliza;
	}
	public double getMontoPoliza() {
		return montoPoliza;
	}
	public void setMontoPoliza(double montoPoliza) {
		this.montoPoliza = montoPoliza;
	}
	public String getMesesPoliza() {
		return mesesPoliza;
	}
	public void setMesesPoliza(String mesesPoliza) {
		this.mesesPoliza = mesesPoliza;
	}
	public String getEstadoPoliza() {
		return estadoPoliza;
	}
	public void setEstadoPoliza(String estadoPoliza) {
		this.estadoPoliza = estadoPoliza;
	}
	public byte[] getArCedula() {
		return arCedula;
	}
	public void setArCedula(byte[] arCedula) {
		this.arCedula = arCedula;
	}
	public byte[] getArPlanillaServicios() {
		return arPlanillaServicios;
	}
	public void setArPlanillaServicios(byte[] arPlanillaServicios) {
		this.arPlanillaServicios = arPlanillaServicios;
	}
	public String getHistorialPoliza() {
		return historialPoliza;
	}
	public void setHistorialPoliza(String historialPoliza) {
		this.historialPoliza = historialPoliza;
	}
	public String getSaldoCuenta() {
		return saldoCuenta;
	}
	public void setSaldoCuenta(String saldoCuenta) {
		this.saldoCuenta = saldoCuenta;
	}
	public double getTasaPago() {
		return tasaPago;
	}
	public void setTasaPago(double tasaPago) {
		this.tasaPago = tasaPago;
	}
	public int getAñosCliente() {
		return añosCliente;
	}
	public void setAñosCliente(int añosCliente) {
		this.añosCliente = añosCliente;
	}
	public int getCantidadPolizas() {
		return cantidadPolizas;
	}
	public void setCantidadPolizas(int cantidadPolizas) {
		this.cantidadPolizas = cantidadPolizas;
	}
	public String getTipoCliente() {
		return tipoCliente;
	}
	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
	@Override
	public String toString() {
		return "SolicitudDePoliza [codigoPoliza=" + codigoPoliza + ", clientePoliza=" + clientePoliza
				+ ", montoPoliza=" + montoPoliza + ", mesesPoliza=" + mesesPoliza + ", estadoPoliza="
				+ estadoPoliza + ", arCedula=" + Arrays.toString(arCedula) + ", arPlanillaServicios="
				+ Arrays.toString(arPlanillaServicios) + ", historialPoliza=" + historialPoliza + ", saldoCuenta="
				+ saldoCuenta + ", tasaPago=" + tasaPago + ", añosCliente=" + añosCliente + ", cantidadPolizas="
				+ cantidadPolizas + ", tipoCliente=" + tipoCliente + "]";
	}
	
	
}
