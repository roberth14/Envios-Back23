package co.com.sistema.envios.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.sistema.envios.dto.UsuarioDTO;
import co.com.sistema.envios.entity.RolUsuario;
import co.com.sistema.envios.entity.Usuario;
import co.com.sistema.envios.repository.IRolRepository;
import co.com.sistema.envios.repository.IRolUsarioRepository;
import co.com.sistema.envios.repository.IUsuarioRepository;
import co.com.sistema.envios.service.MailService;

@RestController
@RequestMapping("/usuario")
@CrossOrigin
public class UsuarioController {
	@Autowired
	IUsuarioRepository usuarioRepository;
	@Autowired
	IRolUsarioRepository rolUsarioRepository;
	@Autowired
	IRolRepository rolRepository;
	@Autowired
	MailService mailService;

	@PutMapping("/create")
	public ResponseEntity<?> updatePassword(@RequestBody Usuario usuario) {
		try {
			Optional<Usuario> user = usuarioRepository.findByEmail(usuario.getEmail());
			if (user.isPresent()) {
				String newPass = new BCryptPasswordEncoder().encode(usuario.getPassword());
				if (newPass!=user.get().getPassword()) {
					user.get().setPassword(newPass);
					usuarioRepository.save(user.get());
					return ResponseEntity.ok(true);
				}
			}
			return ResponseEntity.ok(false);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping("/{modulo}/modulo")
	public ResponseEntity<?> modulo(@RequestBody Usuario usuario, @PathVariable String modulo) {
		try {
			Optional<Usuario> usuarioCurrent = usuarioRepository.findByEmail(usuario.getEmail());
			if (usuarioCurrent.isPresent()) {
				List<RolUsuario> rol = rolUsarioRepository.findByUsuarioId(usuarioCurrent.get().getId());
				for (int i = 0; i < rol.size(); i++) {

					if (modulo.equals("admin") && rol.get(i).getRolId() == 1) {
						return ResponseEntity.ok(true);
					} else if (modulo.equals("empleado") && rol.get(i).getRolId() == 2) {
						return ResponseEntity.ok(true);
					}

				}

			}
			return ResponseEntity.ok(false);

		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<?> lista() {
		try {

			return ResponseEntity.ok(usuarioRepository.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			// TODO: handle exception
		}
	}

	@GetMapping("/empleados")
	public ResponseEntity<?> rolEmpleados() {
		try {
			List<Usuario> usuarios = usuarioRepository.findByRoleId(2);

			List<UsuarioDTO> lista = new ArrayList<>();

			for (int i = 0; i < usuarios.size(); i++) {
				ModelMapper model = new ModelMapper();
				UsuarioDTO dto = model.map(usuarios.get(i), UsuarioDTO.class);
				lista.add(dto);

			}

			return ResponseEntity.ok(lista);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			// TODO: handle exception
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save/empleado")
	public ResponseEntity<?> saveMensajero(@RequestBody Usuario usuario) {
		try {
			usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getCedula() + ""));
			usuario.setFechaRegistro(new Date());
			Usuario usuarioCurrent = usuarioRepository.save(usuario);
			Optional<Usuario> usuarioReturn = usuarioRepository.findById(usuarioCurrent.getId());
			if (usuarioReturn.isPresent()) {
				RolUsuario rol = new RolUsuario();
				rol.setRolId(2);
				rol.setUsuarioId(usuarioReturn.get().getId());
				rolUsarioRepository.save(rol);
				mailService.usuarioNuevo(usuarioReturn.get());
				return ResponseEntity.ok(usuarioReturn);

			}
			return ResponseEntity.status(HttpStatus.CONFLICT).build();

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			// TODO: handle exception
		}
	}

}
