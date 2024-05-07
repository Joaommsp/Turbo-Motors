package br.com.turbomotors.turbomotors.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.turbomotors.turbomotors.DTO.MarcasDTO;
import br.com.turbomotors.turbomotors.Tabelas.Compra;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;
@Repository
@Transactional
public interface CompraRepositorio extends JpaRepository<Compra, Long>{
    @Query("SELECT SUM(c.valor) FROM Compra c WHERE FUNCTION('MONTH', c.dataCompra) = :month and c.status = 'F'")
    Double obterVendasMensal(int month);
    @Query("SELECT SUM(c.valor) FROM Compra c WHERE FUNCTION('YEAR', c.dataCompra) = :year and c.status = 'F'")
    Double obterVendasAnual(int year);
    
    @Query("SELECT count(1) FROM Compra c where c.status = 'F'")
    long quantidadeDeCarrosVendidos();
    
    @Query("SELECT count(1) FROM Compra c where c.status = 'C'")
    long quantidadeDeCarrosCancelados();
    
//    select m.id_marca, m.mar_nome, count(c.id_compra) as QtdCompra from compra c 
//    inner join veiculo v on c.id_veiculo = v.id_carro
//    inner join marca m on m.id_marca = v.id_marca
//    group by m.id_marca
    
    @Query("SELECT new br.com.turbomotors.turbomotors.DTO.MarcasDTO(m.marNome, COUNT(c.idCompra)) " +
    	      "FROM Compra c " +
    	      "JOIN c.veiculo v JOIN v.marca m " +
    	      "GROUP BY m.idMarca")
    	List<MarcasDTO> marcasCompradas(); 

        @Query("SELECT new br.com.turbomotors.turbomotors.DTO.MarcasDTO(t.tipoNome, COUNT(c.idCompra)) " +
        "FROM Compra c " +
        "JOIN c.veiculo v " +
        "JOIN v.tipo t " +
        "WHERE c.status = 'F' " +
        "GROUP BY t.tipoNome")
        List<MarcasDTO> tiposComprados();
   
    
    @Query(value = "SELECT c.pes_nome, c.email, c.cpf_cnpj, v.car_nome, ca.valor, ca.data_compra " +
            "FROM compra ca " +
            "INNER JOIN cliente c ON ca.id_cliente = c.id_cliente " +
            "INNER JOIN veiculo v ON ca.id_veiculo = v.id_carro " +
            "WHERE ca.status = 'F' " +
            "ORDER BY ca.id_compra DESC", nativeQuery = true)
    	List<Object[]> comprasFinalizadas();
    
    	
   @Query(value = "SELECT SUM(v.car_valor) " +
                "FROM compra c " +
                "INNER JOIN veiculo v ON c.id_veiculo = v.id_carro " +
                "WHERE c.status = 'A' AND c.id_cliente = :id_cliente", nativeQuery = true)
   	Double calcularTotalCompra(@Param("id_cliente") Long id_cliente);
   
   
   @Query("select c from Compra c where c.idCompra = ?1")
   Compra minhaCompra(Long idCompra);   
   
   @Query(value = "SELECT * FROM Compra c WHERE c.id_cliente = :idCliente AND c.status = 'A'", nativeQuery = true)
   List<Object[]> comprasDoCliente(@Param("idCliente") Long idCliente);

    
}
