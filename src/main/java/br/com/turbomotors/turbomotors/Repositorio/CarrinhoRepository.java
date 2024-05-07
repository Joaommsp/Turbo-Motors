package br.com.turbomotors.turbomotors.Repositorio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.turbomotors.turbomotors.Tabelas.Carrinho;
import br.com.turbomotors.turbomotors.Tabelas.Cliente;
import br.com.turbomotors.turbomotors.Tabelas.Compra;

@Repository
@Transactional
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    

    @Query(value = "SELECT v.car_valor, v.car_nome, v.car_url, CASE WHEN c.tipo_carrinho = 'A' THEN 'Aluguel' ELSE 'Compra' END AS Carrinho " +
    "FROM carrinho c " +
    "INNER JOIN veiculo v ON c.id_veiculo = v.id_carro " +
    "WHERE c.id_usuario = :id_cliente", nativeQuery = true)
    List<Object[]> carrinhoCliente(@Param("id_cliente") Long id_cliente);

    @Query("SELECT c FROM Carrinho c WHERE c.cliente = ?1")
    List<Carrinho> obterCarrinho(Cliente cliente);


}
