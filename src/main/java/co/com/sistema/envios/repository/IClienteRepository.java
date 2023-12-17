package co.com.sistema.envios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.sistema.envios.entity.Cliente;

public interface IClienteRepository extends JpaRepository<Cliente, Integer>{
	Optional<Cliente> findByCedula(Integer cedula);
}
