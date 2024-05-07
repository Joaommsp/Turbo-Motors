package br.com.turbomotors.turbomotors.Repositorio;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.turbomotors.turbomotors.Tabelas.Cargo;

@Repository
@Transactional
public interface CargoRepositorio extends JpaRepository<Cargo, Long> {

    @Query("SELECT c FROM Cargo c WHERE c.idCargos = :idCargos")
    Cargo procurarPorID(@Param("idCargos") Long idCargo);
	
	
}
