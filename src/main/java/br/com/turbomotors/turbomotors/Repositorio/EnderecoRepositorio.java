package br.com.turbomotors.turbomotors.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.turbomotors.turbomotors.Tabelas.Endereco;

import java.util.List;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface EnderecoRepositorio extends JpaRepository<Endereco, Long> {
    
	@Query(value = "SELECT * FROM endereco e WHERE e.id_cliente = ?1", nativeQuery = true)
	List<Object[]> findByEndderecoCliente(Long clienteId);
    
}
