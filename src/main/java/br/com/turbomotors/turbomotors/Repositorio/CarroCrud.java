package br.com.turbomotors.turbomotors.Repositorio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.com.turbomotors.turbomotors.Tabelas.Veiculo;



@Repository
@Transactional
public interface CarroCrud extends JpaRepository<Veiculo, Long> {
 
	@Query("SELECT c FROM Veiculo c WHERE c.idCarro = :idCarro")
	Veiculo findByIdOrdernado(@Param("idCarro") Long idCarro);
    
    @Query("SELECT v FROM Veiculo v INNER JOIN v.compras c")
    List<Veiculo> findByCompras();    

    @Query(value="SELECT max(id_carro) FROM Veiculo", nativeQuery=true)
    Long ultimoRegistro();

   


    @Query("SELECT v FROM Veiculo v WHERE v.carNome = ?1 AND v.disponivel = 'S'")
    List<Veiculo> obterCarrosDisponiveis(String carNome);
    
    
    @Query(value = "SELECT v.car_nome, m.mar_nome, v.car_url, t.valor_aluguel, v.car_valor, t.tipo_nome, v.id_carro " +
            "FROM veiculo v " +
            "INNER JOIN tipo t ON t.id_tipo = v.id_tipo " +
            "INNER JOIN marca m ON v.id_marca = m.id_marca " +
            "WHERE LOWER(t.tipo_nome) = :tipoNome and v.disponivel = 'S'", nativeQuery = true)
    List<Object[]> obterCarrosTipo(@Param("tipoNome") String tipoNome);
    
    @Query(value = "SELECT v.car_valor, v.car_nome, v.car_url, 'Compra' " +
            "FROM compra c " +
            "INNER JOIN veiculo v ON c.id_veiculo = v.id_carro " +
            "WHERE c.status = 'A' AND c.id_cliente = :id_cliente " +
            "UNION ALL " +
            "SELECT v.car_valor, v.car_nome, v.car_url, 'Aluguel' " +
            "FROM aluguel c " +
            "INNER JOIN veiculo v ON c.id_carro = v.id_carro " +
            "WHERE c.status_aluguel = 'A' AND c.id_cliente = :id_cliente", nativeQuery = true)
    List<Object[]> obterCarrinhoCliente(@Param("id_cliente") Long id_cliente);

    	
    
    	@Query(value = "SELECT v.car_nome, v.car_ano, FORMAT(v.car_valor, 2, 'de_DE')  AS car_valor, v.car_descricao, v.car_url, v.disponivel, m.mar_nome, t.tipo_nome, v.id_carro " +
                "FROM veiculo v " +
                "INNER JOIN marca m ON v.id_marca = m.id_marca " +
                "INNER JOIN tipo t ON t.id_tipo = v.id_tipo " +
                "WHERE v.id_carro = :id_carro", nativeQuery = true)
	List<Object[]> obterVeiculos(@Param("id_carro") Long id_carro);

	@Query("select v from Veiculo v where v.idCarro = :idCarro")
	Veiculo carroPorID(Long idCarro);

}
