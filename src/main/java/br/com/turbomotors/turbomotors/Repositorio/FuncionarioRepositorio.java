package br.com.turbomotors.turbomotors.Repositorio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.turbomotors.turbomotors.DTO.UsuarioLogadoDTO;
import br.com.turbomotors.turbomotors.Tabelas.Funcionarios;

@Repository
@Transactional
public interface FuncionarioRepositorio extends JpaRepository<Funcionarios, Long>{

    @Query("select f from Funcionarios f where f.user = ?1")
    Funcionarios obterUsuario(String user);
    
    @Query("SELECT new br.com.turbomotors.turbomotors.DTO.UsuarioLogadoDTO(f.user, f.url_anexo, c.nomeCargo, f.primeiroAcesso) FROM Funcionarios f " +
            "INNER JOIN f.cargos c " + 
            "WHERE f.user = ?1")
    UsuarioLogadoDTO obterUsuarioLogado(String user);

    @Modifying
    @Query(value = "UPDATE funcionarios set PRIMEIRO_ACESSO = :situacao WHERE user = :usuario", nativeQuery = true)
    void updateAcesso(@Param("usuario") String user, @Param("situacao") String acesso);



	
	
}
