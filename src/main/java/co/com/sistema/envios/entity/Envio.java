package co.com.sistema.envios.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name="envio")
public class Envio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer guia;
	private String descripcion;
	private Integer alto;
	private Integer ancho;
	private Integer largo;
	private Integer peso;
	private String tarifa;
	@Column(name="admin_id")
	private Integer adminId;
	@Column(name="mensajero_id")
	private Integer mensajeroId;
	@Column(name="ciudad_origen_id")
	private Integer ciudadOrigenId;
	@Column(name="ciudad_destino_id")
	private Integer ciudadDestinoId;
	private String direccion;

	@Column(name="fecha_registro")
	private Date fechaRegistro;
	@Column(name="remitente_id")
	private Integer remitenteId;
	@Column(name="destinatario_id")
	private Integer destinatarioId;
}
