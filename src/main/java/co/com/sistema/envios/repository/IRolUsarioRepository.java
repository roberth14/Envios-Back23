package co.com.sistema.envios.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.sistema.envios.entity.RolUsuario;

public interface IRolUsarioRepository extends JpaRepository<RolUsuario, Integer> {
	List<RolUsuario> findByUsuarioId(Integer usuario_id);
}
