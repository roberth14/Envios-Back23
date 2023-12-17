package co.com.sistema.envios.dto;

import co.com.sistema.envios.entity.Cliente;
import co.com.sistema.envios.entity.Envio;
import lombok.Data;
@Data
public class EnvioDTO {
	private Envio envio;
	private Cliente remitente;
	private Cliente destinatario;

}
