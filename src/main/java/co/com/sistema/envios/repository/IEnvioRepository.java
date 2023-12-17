package co.com.sistema.envios.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.sistema.envios.entity.Envio;

public interface IEnvioRepository extends JpaRepository<Envio, Integer> {
	Optional<Envio> findByGuia(Integer guia);
	List<Envio> findByMensajeroId(Integer mensajeroId);
	
}
