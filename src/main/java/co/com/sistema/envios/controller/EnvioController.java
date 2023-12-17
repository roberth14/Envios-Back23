package co.com.sistema.envios.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.sistema.envios.dto.EnvioDTO;
import co.com.sistema.envios.dto.EnvioGuiaDTO;
import co.com.sistema.envios.dto.EstadosEnviosDTO;
import co.com.sistema.envios.entity.Cliente;
import co.com.sistema.envios.entity.Destinatario;
import co.com.sistema.envios.entity.Envio;
import co.com.sistema.envios.entity.EnvioEstado;
import co.com.sistema.envios.entity.Remitente;
import co.com.sistema.envios.entity.Usuario;
import co.com.sistema.envios.entity.UsuarioTurno;
import co.com.sistema.envios.repository.EnvioEstadoRepository;
import co.com.sistema.envios.repository.IClienteRepository;
import co.com.sistema.envios.repository.IDestinatarioRepository;
import co.com.sistema.envios.repository.IEnvioRepository;
import co.com.sistema.envios.repository.IRemitenteRepository;
import co.com.sistema.envios.repository.IUsuarioRepository;
import co.com.sistema.envios.repository.UsuarioTurnoRepository;

@RestController
@RequestMapping("/envio")
@CrossOrigin
public class EnvioController {

	@Autowired
	IRemitenteRepository remitenteRepository;
	@Autowired
	IDestinatarioRepository destinatarioRepository;
	@Autowired
	IUsuarioRepository usuarioRepository;
	@Autowired
	IEnvioRepository envioRepository;
	@Autowired
	EnvioEstadoRepository envioEstadoRepository;
	@Autowired
	IClienteRepository clienteRepository;
	@Autowired
	UsuarioTurnoRepository usuarioTurnoRepository;

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<?>lista(){
		try {
			return ResponseEntity.ok(envioRepository.findAll());
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
		}
	}
	@GetMapping("/{id}/mensajero")
	public ResponseEntity<?>listaEnviosMensaero(@PathVariable Integer id){
		try {
			return ResponseEntity.ok(envioRepository.findByMensajeroId(id));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
		}
	}
	@PutMapping("/{id}/estado/{mensajero}")
	public ResponseEntity<?>updateEstado(@PathVariable Integer id,@RequestBody Envio envio,@PathVariable Integer mensajero){
		try {
			Optional<Envio>envioReturn=envioRepository.findById(envio.getId());
			if(envioReturn.isPresent() && id<5) {
				EnvioEstado estado=new EnvioEstado();
				estado.setEnvioId(envioReturn.get().getId());
				estado.setEstadoId(id+1);
				estado.setUsuarioId(mensajero);
				estado.setFechaRegistro(new Date());
				return ResponseEntity.ok(envioEstadoRepository.save(estado));
			}
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
		}
	}
	@GetMapping("/{id}")
	public ResponseEntity<?>envioById(@PathVariable Integer id){
		try {
			Optional<Envio>envio=envioRepository.findById(id);
			if(envio.isPresent()) {
				List<EnvioEstado>estados=envioEstadoRepository.findByEnvioId(envio.get().getId());
				ModelMapper model=new ModelMapper();
				EnvioGuiaDTO envioReturn=model.map(envio.get(),EnvioGuiaDTO.class);
				EstadosEnviosDTO estadosReturn=new EstadosEnviosDTO();
				estadosReturn.setEstados(estados);
				estadosReturn.setEnvio(envioReturn);
				return ResponseEntity.ok(estadosReturn);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
		}
	}
	@GetMapping("/guia/{guia}")
	public ResponseEntity<?>envioByGuia(@PathVariable Integer guia){
		try {
			Optional<Envio>envio=envioRepository.findByGuia(guia);
			if(envio.isPresent()) {
				List<EnvioEstado>estados=envioEstadoRepository.findByEnvioId(envio.get().getId());
				ModelMapper model=new ModelMapper();
				EnvioGuiaDTO envioReturn=model.map(envio.get(),EnvioGuiaDTO.class);
				EstadosEnviosDTO estadosReturn=new EstadosEnviosDTO();
				estadosReturn.setEstados(estados);
				estadosReturn.setEnvio(envioReturn);
				return ResponseEntity.ok(estadosReturn);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
		}
	}
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save")
	public ResponseEntity<?> saveEnvio(@RequestBody EnvioDTO envio) {

		try {
			// PASO 1 REMITENTE
			// Verifico si el remitente esta registrado
			Optional<Cliente> remitente = clienteRepository.findByCedula(envio.getRemitente().getCedula());
			Cliente remitenteReturn = new Cliente();
			if (remitente.isPresent()) {
				remitenteReturn = remitente.get();
			} else {
				envio.getRemitente().setFechaResgistro(new Date());
				remitenteReturn = clienteRepository.save(envio.getRemitente());
			}
			// PASO 2 DESTINATARIO
			// Verifico si el remitente esta registrado
			Optional<Cliente> destinatario = clienteRepository.findByCedula(envio.getDestinatario().getCedula());
			Cliente destinatarioReturn = new Cliente();
			if (destinatario.isPresent()) {
				destinatarioReturn = destinatario.get();
			} else {
				envio.getDestinatario().setFechaResgistro(new Date());
				destinatarioReturn = clienteRepository.save(envio.getDestinatario());
			}
			// Registro destinatario
			Destinatario des = new Destinatario();
			des.setCliente_id(destinatarioReturn.getId());
			Remitente rem = new Remitente();
			rem.setUsuario_id(remitenteReturn.getId());

			// PASO 3 BUSCO MENSAJERO
			UsuarioTurno turno = mensajero();

			Envio envioReturn = new Envio();
			envioReturn.setAlto(envio.getEnvio().getAlto());
			envioReturn.setAncho(envio.getEnvio().getAncho());
			envioReturn.setLargo(envio.getEnvio().getLargo());
			envioReturn.setPeso(envio.getEnvio().getPeso());

			envioReturn.setTarifa(envio.getEnvio().getTarifa());
			envioReturn.setAdminId(envio.getEnvio().getAdminId());

			envioReturn.setMensajeroId(turno.getUsuarioId());
			envioReturn.setDescripcion(envio.getEnvio().getDescripcion());
			envioReturn.setGuia(turno.getGuia());

			envioReturn.setDireccion(envio.getEnvio().getDireccion());
			envioReturn.setCiudadOrigenId(envio.getEnvio().getCiudadOrigenId());
			envioReturn.setCiudadDestinoId(envio.getEnvio().getCiudadDestinoId());
			envioReturn.setDestinatarioId(destinatarioRepository.save(des).getId());
			envioReturn.setRemitenteId(remitenteRepository.save(rem).getId());

			envioReturn.setFechaRegistro(new Date());
			Envio envioSave=envioRepository.save(envioReturn);
			
			EnvioEstado estado=new EnvioEstado();
			estado.setEnvioId(envioSave.getId());
			estado.setEstadoId(1);
			estado.setFechaRegistro(new Date());
			estado.setUsuarioId(envioSave.getAdminId());
			envioEstadoRepository.save(estado);
			return ResponseEntity.ok(envioSave);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
	}

	public UsuarioTurno mensajero() {
		List<Usuario> usuarios = usuarioRepository.findByRoleId(2);
		Optional<UsuarioTurno> turno = usuarioTurnoRepository.findById(1);
		int tamaño = usuarios.size();
		System.out.print("tamanño"+tamaño+"  "+ turno.get().toString());
		int guia = turno.get().getGuia() + 1;
		turno.get().setGuia(guia);
		// Si es el ultimo de la lista
		if (tamaño > 0) {
			System.out.print("Ultimo usuario "+usuarios.get(tamaño-1).toString());
			if (usuarios.get(tamaño - 1).getId() == turno.get().getUsuarioId()) {
				
				turno.get().setUsuarioId(usuarios.get(0).getId());
				return usuarioTurnoRepository.save(turno.get());
			}
			if (turno.get().getUsuarioId() > 0) {

				for (int i = 0; i < usuarios.size(); i++) {

					Usuario user = usuarios.get(i);
					if (user.getId() == turno.get().getUsuarioId()) {

						turno.get().setUsuarioId(usuarios.get(i + 1).getId());

						return usuarioTurnoRepository.save(turno.get());

					}

				}
			} else {

				turno.get().setUsuarioId(usuarios.get(0).getId());
				turno.get().setGuia(10001);
				return usuarioTurnoRepository.save(turno.get());

			}
		}

		return null;
	}

}
