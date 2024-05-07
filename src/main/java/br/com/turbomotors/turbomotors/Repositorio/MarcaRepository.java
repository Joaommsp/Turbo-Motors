package br.com.turbomotors.turbomotors.Repositorio;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.turbomotors.turbomotors.Tabelas.Marca;
import java.util.List;


@Repository
@Transactional
public interface MarcaRepository extends JpaRepository<Marca, Long> {
    @Query("select m from Marca m where m.idMarca =  ?1")
    Marca findByVeiculosPorId(Long idMarca);

    boolean existsByMarNome(String marNome);
    
}
