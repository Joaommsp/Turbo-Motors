package br.com.turbomotors.turbomotors.Repositorio;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.turbomotors.turbomotors.Tabelas.Tipo;


@Repository
@Transactional
public interface TipoRepositorio extends JpaRepository<Tipo, Long> {

    @Query("select t from Tipo t where t.idTipo = ?1")
    Tipo findByVeiculosIdtipo(Long idTipo);


    boolean existsByTipoNome(String tipoNome);
    

}
