package br.com.turbomotors.turbomotors.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.turbomotors.turbomotors.Tabelas.Cliente;

import java.util.List;

import javax.transaction.Transactional;
import br.com.turbomotors.turbomotors.Tabelas.Compra;
import br.com.turbomotors.turbomotors.Tabelas.Endereco;




@Repository
@Transactional
public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

    @Query("select u from Cliente u where u.email = ?1")
    Cliente findByEmail(String email);


	@Query(value = "select primeiro_acesso from cliente WHERE email = ?1", nativeQuery = true)
	String primeiroAcesso(String email);

	Cliente findByIdCliente(Long idCliente);

	@Modifying
    @Query(value = "UPDATE Cliente SET primeiro_acesso = 'N' where email = :usuario", nativeQuery = true)
    void updateAcesso(@Param("usuario") String user);



	Cliente findByEnderecos(Endereco enderecos);
    
    
    @Query("select u from Cliente u where u.idCliente = ?1")
    Cliente obterLogado(Long idCliente);
    
    @Query(value = 
    	    "SELECT 'Compra' AS tipo, v.car_nome, v.car_url, c.valor, CONCAT('Comprado em ', c.data_compra) AS observacao " +
    	    "FROM compra c " +
    	    "INNER JOIN veiculo v ON c.id_veiculo = v.id_carro " +
    	    "WHERE c.status = 'F' AND c.id_cliente = ?1 " +
    	    "UNION ALL " +
    	    "SELECT 'Aluguel' AS tipo, v.car_nome, v.car_url, SUM(DATEDIFF(a.data_fim, a.data_inicio) * t.valor_aluguel) AS valor, " +
    	    "CONCAT('finalizado em ', a.data_fim) AS observacao " +
    	    "FROM aluguel a " +
    	    "INNER JOIN veiculo v ON a.id_carro = v.id_carro " +
    	    "INNER JOIN tipo t ON v.id_tipo = t.id_tipo " +
    	    "WHERE a.status_aluguel = 'F' AND a.id_cliente = ?1 " +
    	    "GROUP BY a.id_aluguel",
    	    nativeQuery = true)
    	List<Object[]> obterShopp(Long clienteId);
    
		@Query(value = "SELECT DISTINCT c.id_cliente, c.pes_nome, end.logradouro, end.numero,  COALESCE(end.complemento, 'Não cadastrado') complemento,end.bairro, end.cep CEP, c.telefone, c.cpf_cnpj, c.email " +
		"FROM cliente c " +
		"INNER JOIN endereco end ON c.id_cliente = end.id_cliente " +
		"WHERE c.id_cliente = :id_cliente", nativeQuery = true)
List<Object[]> obterEnderecoClienteCompra(@Param("id_cliente") Long id_cliente);
    
    boolean existsByCpfCnpj(String cpfCnpj);
	boolean existsByNomeUsuario(String nomeUsuario);
	boolean existsByEmail(String email);
	boolean existsByTelefone(String telefone);
	boolean existsByPesNome(String pesNome);
}
