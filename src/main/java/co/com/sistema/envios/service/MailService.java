package co.com.sistema.envios.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import co.com.sistema.envios.dto.LoginDTO;
import co.com.sistema.envios.entity.Cliente;
import co.com.sistema.envios.entity.Destinatario;
import co.com.sistema.envios.entity.Envio;
import co.com.sistema.envios.entity.Remitente;
import co.com.sistema.envios.entity.Usuario;
import co.com.sistema.envios.repository.IClienteRepository;
import co.com.sistema.envios.repository.IDestinatarioRepository;
import co.com.sistema.envios.repository.IEnvioRepository;
import co.com.sistema.envios.repository.IRemitenteRepository;
import co.com.sistema.envios.repository.IUsuarioRepository;
import co.com.sistema.envios.security.JwtTokenProvider;

@Service
public class MailService {

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	IUsuarioRepository usuarioRepository;
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	IEnvioRepository envioRepository;
	@Autowired
	IClienteRepository clienteRepository;
	@Autowired
	IRemitenteRepository remitenteRepository;
	@Autowired
	IDestinatarioRepository destinatarioRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	public boolean usuarioNuevo(Usuario usuario) throws MessagingException {

		try {
			Optional<Usuario> user = usuarioRepository.findById(usuario.getId());
			if (user.isPresent()) {
				MimeMessage mimeMessageHelpe = javaMailSender.createMimeMessage();
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessageHelpe, "UTF-8");
				// GENERO TOKEN
				LoginDTO loginDTO = new LoginDTO();
				loginDTO.setEmail(usuario.getEmail());
				loginDTO.setPassword(usuario.getCedula() + "");
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

				SecurityContextHolder.getContext().setAuthentication(authentication);

				// obtenemos el token del jwtTokenProvider
				String token = jwtTokenProvider.generarToken(authentication);
				// TITULO DEL EMAIL
				String titulo = "DAFAR";
				// DESCRIPCION
				String detalle = "El siguiente link es para completar el proceso de registro y  habilitar su cuenta ";
				// FECHA GENERA EL EMAIL
				Date fecha = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", new Locale("es"));

				String url = "https://fabianveg.github.io/";
				String urlConToken = url + "?token=" + token;
				String fechaFormateada = sdf.format(fecha);
				// IMG
				// CREO EL CONTEXT PARA ENVIAR A LA PLANTILLA HTML

				Context context = new Context();
				context.setVariable("titulo", titulo);
				context.setVariable("link", urlConToken);
				context.setVariable("detalle", detalle);
				context.setVariable("usuario", usuario);
				context.setVariable("fecha", fechaFormateada);
				String htmlContent = templateEngine.process("email-template", context);
				messageHelper.setTo(usuario.getEmail());
				messageHelper.setSubject("VERIFICAR CORREO DAFAR");
				// messageHelper.setFrom("MENSAJE DE BIENVENIDA");
				messageHelper.setText(htmlContent, true);
				// CREO OBJ CODIGOREGISTRO

				javaMailSender.send(mimeMessageHelpe);
				return true;
			}
			return false;

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}

	public boolean usuarioRemitente(Envio envio) throws MessagingException {

		try {
			Optional<Remitente>remitente=remitenteRepository.findById(envio.getRemitenteId());
			Optional<Cliente> cliente = clienteRepository.findById(remitente.get().getUsuario_id());
			MimeMessage mimeMessageHelpe = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessageHelpe, "UTF-8");
			// BUSCO EL ENVIO

			// TITULO DEL EMAIL
			String titulo = "DAFAR";
			// DESCRIPCION
			String detalle = "El siguiente numero es la GUIA es para ver el estado de su envio ";
			// FECHA GENERA EL EMAIL
			Date fecha = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", new Locale("es"));

			String fechaFormateada = sdf.format(fecha);
			// IMG
			// CREO EL CONTEXT PARA ENVIAR A LA PLANTILLA HTML

			Context context = new Context();
			context.setVariable("titulo", titulo);
			context.setVariable("guia", envio.getGuia());
			context.setVariable("detalle", detalle);
			context.setVariable("usuario", cliente.get());
			context.setVariable("fecha", fechaFormateada);
			String htmlContent = templateEngine.process("guia", context);
			messageHelper.setTo(cliente.get().getEmail());
			messageHelper.setSubject("GUIA ENVIO DAFAR");
			// messageHelper.setFrom("MENSAJE DE BIENVENIDA");
			messageHelper.setText(htmlContent, true);
			// CREO OBJ CODIGOREGISTRO

			javaMailSender.send(mimeMessageHelpe);
			return true;

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}

	public boolean usuarioDestinatario(Envio envio) throws MessagingException {

		try {
			Optional<Destinatario>des=destinatarioRepository.findById(envio.getDestinatarioId());
			Optional<Cliente> cliente = clienteRepository.findById(des.get().getCliente_id());
			MimeMessage mimeMessageHelpe = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessageHelpe, "UTF-8");
			// BUSCO EL ENVIO

			// TITULO DEL EMAIL
			String titulo = "DAFAR";
			// DESCRIPCION
			String detalle = "El siguiente numero es la GUIA es para ver el estado de su envio ";
			// FECHA GENERA EL EMAIL
			Date fecha = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", new Locale("es"));

			String fechaFormateada = sdf.format(fecha);
			// IMG
			// CREO EL CONTEXT PARA ENVIAR A LA PLANTILLA HTML

			Context context = new Context();
			context.setVariable("titulo", titulo);
			context.setVariable("guia", envio.getGuia());
			context.setVariable("detalle", detalle);
			context.setVariable("usuario", cliente.get());
			context.setVariable("fecha", fechaFormateada);
			String htmlContent = templateEngine.process("guia", context);
			messageHelper.setTo(cliente.get().getEmail());
			messageHelper.setSubject("GUIA ENVIO DAFAR");
			// messageHelper.setFrom("MENSAJE DE BIENVENIDA");
			messageHelper.setText(htmlContent, true);
			// CREO OBJ CODIGOREGISTRO

			javaMailSender.send(mimeMessageHelpe);
			return true;

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}

	public int generarCodigo(int longitud) {
		Random random = new Random();
		int min = (int) Math.pow(10, longitud - 1);
		int max = (int) Math.pow(10, longitud) - 1;
		return random.nextInt(max - min + 1) + min;
	}
}
