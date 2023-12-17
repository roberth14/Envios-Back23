package co.com.sistema.envios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.sistema.envios.entity.Destinatario;

public interface IDestinatarioRepository extends JpaRepository<Destinatario, Integer>{

}
