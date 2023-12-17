package co.com.sistema.envios.dto;

import java.util.List;

import co.com.sistema.envios.entity.EnvioEstado;
import lombok.Data;
@Data
public class EstadosEnviosDTO {
	List<EnvioEstado>estados;
	EnvioGuiaDTO envio;
}
