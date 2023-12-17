package co.com.sistema.envios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.sistema.envios.entity.Envio;
import co.com.sistema.envios.entity.Usuario;
import co.com.sistema.envios.service.MailService;



@RestController
@RequestMapping("/mail")
@CrossOrigin
public class EmailController {
	
	@Autowired
	MailService mailService;
	
	@PostMapping("/new")
	public ResponseEntity<?> correoBienvenida(@RequestBody Usuario usuario){
		
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(mailService.usuarioNuevo(usuario));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		
	}
	
	@PostMapping("/guia")
	public ResponseEntity<?> correoGuia(@RequestBody Envio envio){
		
		try {
			boolean destinatario=mailService.usuarioDestinatario(envio);
			boolean remitente=mailService.usuarioRemitente(envio);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(destinatario+" " +remitente);
			
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		
	}

	

}
