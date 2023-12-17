package co.com.sistema.envios.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="envio_estado")
public class EnvioEstado {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name="usuario_id")
	private Integer usuarioId;
	@Column(name="envio_id")
	private Integer envioId;
	@Column(name="estado_id")
	private Integer estadoId;
	@Column(name="fecha_registro")
	private Date fechaRegistro;

}
