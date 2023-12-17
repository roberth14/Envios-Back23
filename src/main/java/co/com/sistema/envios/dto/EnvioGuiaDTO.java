package co.com.sistema.envios.dto;

import java.util.Date;


import lombok.Data;
@Data
public class EnvioGuiaDTO {
	
	private Integer id;
	private Integer guia;
	private String descripcion;
	private Integer alto;
	private Integer ancho;
	private Integer largo;
	private Integer peso;
	private String tarifa;
	private Integer adminId;
	private Integer mensajeroId;
	private Integer ciudadOrigenId;
	private Integer ciudadDestinoId;
	private String direccion;
	private Date fechaRegistro;

}
