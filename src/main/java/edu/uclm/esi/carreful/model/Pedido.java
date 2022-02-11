package edu.uclm.esi.carreful.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Pedido {
	@Id @Column (length = 36)
	private String idPedido;
	private String fecha;
	private String estado;
	@ManyToMany
	private Collection <Product> productosPedido;
	
	public Pedido() {
		
	}
	public Pedido(Collection<Product> coll) {
		this.idPedido=UUID.randomUUID().toString();
		this.fecha = convertirFecha();
		this.estado="Consulte aquí el estado del pedido.";
		this.productosPedido=coll;
	}

	public String getFecha() {
		return fecha;
	}

	public Collection<Product> getProductosPedido() {
		return productosPedido;
	}

	public void setProductosPedido(Collection<Product> productosPedido) {
		this.productosPedido = productosPedido;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String convertirFecha() {
		Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        
        return df.format(date);
	}
}
