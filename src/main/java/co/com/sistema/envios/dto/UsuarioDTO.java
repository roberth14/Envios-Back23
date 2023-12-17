package co.com.sistema.envios.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
	private Integer id;
	private String nombre;
	private String apellido;
	private String telefono;
	private Integer cedula;
	private String email;

}
