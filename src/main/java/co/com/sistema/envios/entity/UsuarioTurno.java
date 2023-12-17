package co.com.sistema.envios.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Entity
@Data
@Table(name="usuario_turno")
public class UsuarioTurno {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		@Column(name="usuario_id")
		private Integer usuarioId;
		private Integer guia;
}
