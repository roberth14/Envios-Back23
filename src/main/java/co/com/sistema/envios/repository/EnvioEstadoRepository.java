package co.com.sistema.envios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.sistema.envios.entity.EnvioEstado;

public interface EnvioEstadoRepository extends JpaRepository<EnvioEstado, Integer>{
	List<EnvioEstado> findByEnvioId(Integer envio_id);
}
