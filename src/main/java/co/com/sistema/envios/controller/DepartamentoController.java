package co.com.sistema.envios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.sistema.envios.repository.IDepartamentoRepository;

@RestController
@RequestMapping("/departamento")
@CrossOrigin
public class DepartamentoController {
	@Autowired
	IDepartamentoRepository departamentoRepository;
	
	@GetMapping
	public ResponseEntity<?> listaUsuario(){
		try {
			return ResponseEntity.ok(departamentoRepository.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			// TODO: handle exception
		}
	}
}
