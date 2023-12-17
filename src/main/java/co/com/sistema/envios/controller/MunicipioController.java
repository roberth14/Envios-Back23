package co.com.sistema.envios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.sistema.envios.repository.IMunicipioRepository;

@RestController
@RequestMapping("/municipio")
@CrossOrigin
public class MunicipioController {
	
	@Autowired
	IMunicipioRepository municipioRepository;
	
	@GetMapping
	public ResponseEntity<?> listaMunicio(){
		try {
			return ResponseEntity.ok(municipioRepository.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			// TODO: handle exception
		}
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> municioById(@PathVariable Integer id){
		try {
			return ResponseEntity.ok(municipioRepository.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			// TODO: handle exception
		}
	}

}
