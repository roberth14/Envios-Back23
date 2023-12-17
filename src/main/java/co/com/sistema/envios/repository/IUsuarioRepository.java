package co.com.sistema.envios.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.sistema.envios.entity.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer>{
	
	Optional<Usuario> findByEmail(String email);
	@Query("SELECT u FROM Usuario u JOIN u.roles r WHERE r.id = 2")
    List<Usuario> findByRoleId(Integer roleId);
}
