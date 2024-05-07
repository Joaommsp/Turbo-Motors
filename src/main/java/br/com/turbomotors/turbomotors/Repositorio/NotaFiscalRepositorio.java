package br.com.turbomotors.turbomotors.Repositorio;


import org.springframework.data.jpa.repository.JpaRepository;
import br.com.turbomotors.turbomotors.Tabelas.Aluguel;

public interface NotaFiscalRepositorio extends JpaRepository<Aluguel, Long>{
	

}
