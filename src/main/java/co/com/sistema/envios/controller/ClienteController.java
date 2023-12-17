package co.com.sistema.envios.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.sistema.envios.entity.Cliente;
import co.com.sistema.envios.repository.IClienteRepository;
@RestController
@RequestMapping("/cliente")
@CrossOrigin
public class ClienteController {
	
	@Autowired
	IClienteRepository clienteRepository;
	
	@GetMapping("/{cedula}")
	public ResponseEntity<?> findByCedula(@PathVariable Integer cedula){
		try {
			Optional<Cliente>cliente=clienteRepository.findByCedula(cedula);
			if(cliente.isPresent()) {
				return ResponseEntity.ok(cliente);
			}

			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
			// TODO: handle exception
		}
	}
	

}
