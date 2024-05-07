package br.com.turbomotors.turbomotors.Repositorio;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.turbomotors.turbomotors.Tabelas.Marca;
import br.com.turbomotors.turbomotors.Tabelas.Pagamento;

@Repository
@Transactional
public interface PagamentoRepositorio extends JpaRepository<Pagamento, Long> {
}
