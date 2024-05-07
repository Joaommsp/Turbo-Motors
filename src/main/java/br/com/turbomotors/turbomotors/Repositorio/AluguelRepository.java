package br.com.turbomotors.turbomotors.Repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.turbomotors.turbomotors.Tabelas.Aluguel;
import br.com.turbomotors.turbomotors.Tabelas.Compra;

public interface AluguelRepository extends JpaRepository<Aluguel, Long>{
	
	
	/// Consulta MySQL Renê
//	select c.pes_nome, c.cpf_cnpj, al.data_inicio, al.data_fim, 
//	case when 
//		datediff(al.data_fim, al.data_inicio) = 0 then 'Mesmo Dia'
//	else 
//		datediff(al.data_fim, al.data_inicio) 
//	end as QuantidadeDeDias,
//	case when 
//		sum(datediff(al.data_fim, al.data_inicio) * al.valor_hora) = 0 then al.valor_hora
//	else 
//		sum(datediff(al.data_fim, al.data_inicio) * al.valor_hora)
//	end as ValorTotal
//	from aluguel al
//	inner join veiculo v on al.id_carro = v.id_carro
//	inner join cliente c on al.id_cliente = c.id_cliente
//	group by al.id_aluguel
//	order by al.data_inicio desc
	
	
			@Query(value = "SELECT c.pes_nome, c.cpf_cnpj, al.data_inicio, al.data_fim, " +
            "CASE WHEN DATEDIFF(al.data_fim, al.data_inicio) = 0 THEN 'Mesmo Dia' " +
            "ELSE DATEDIFF(al.data_fim, al.data_inicio) END AS QuantidadeDeDias, " +
            "CASE WHEN SUM(DATEDIFF(al.data_fim, al.data_inicio) * al.valor_hora) = 0 THEN al.valor_hora " +
            "ELSE SUM(DATEDIFF(al.data_fim, al.data_inicio) * al.valor_hora) END AS ValorTotal " +
            "FROM aluguel al " +
            "INNER JOIN veiculo v ON al.id_carro = v.id_carro " +
            "INNER JOIN cliente c ON al.id_cliente = c.id_cliente " +
            "GROUP BY al.id_aluguel " +
            "ORDER BY al.data_inicio DESC", nativeQuery = true)
    		List<Object[]> aluguelDetalhes();
    		
    		@Query(value = "SELECT * FROM Aluguel c WHERE c.id_cliente = :idCliente and c.status_aluguel = 'A'", nativeQuery = true)
    		List<Object[]> aluguelCliente(@Param("idCliente") Long idCliente);

	    		   
	    	@Query("select c from Aluguel c where c.idAluguel = ?1")
	    	Aluguel meusAlugueis(Long idAluguel);   
}
