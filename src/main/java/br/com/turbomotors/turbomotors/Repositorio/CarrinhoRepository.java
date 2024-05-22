package br.com.turbomotors.turbomotors.Repositorio;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.turbomotors.turbomotors.Tabelas.Carrinho;
import br.com.turbomotors.turbomotors.Tabelas.Cliente;
import br.com.turbomotors.turbomotors.Tabelas.Compra;
import br.com.turbomotors.turbomotors.Tabelas.Veiculo;

@Repository
@Transactional
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    

    @Query(value = "SELECT v.car_valor, v.car_nome, v.car_url, CASE WHEN c.tipo_carrinho = 'A' THEN 'Aluguel' ELSE 'Compra' END AS Carrinho, v.id_carro " +
    "FROM carrinho c " +
    "INNER JOIN veiculo v ON c.id_veiculo = v.id_carro " +
    "WHERE c.id_usuario = :id_cliente", nativeQuery = true)
    List<Object[]> carrinhoCliente(@Param("id_cliente") Long id_cliente);

    @Query("SELECT c FROM Carrinho c WHERE c.cliente = ?1")
    List<Carrinho> obterCarrinho(Cliente cliente);


    @Modifying
    @Query(value = "insert into log_compra (guid,dataHoraFormatada,acao, lg_tempo ,lg_cliques, id_compra, id_usuario) values (:guid, :dataHoraFormatada,  :acao, :lg_tempo, :lg_cliques, :id_compra, :id_usuario);", nativeQuery = true)
    void inserirLog(@Param("guid") String guid, @Param("dataHoraFormatada") LocalDateTime dataHoraFormatada, @Param("acao") String acao, @Param("lg_tempo") int lg_tempo, @Param("lg_cliques") int cliques, @Param("id_compra") Long id_Compra, @Param("id_usuario") Long id_usuario );


    @Query(value = "select id_veiculo from carrinho where id_usuario = :usuario and id_veiculo = :idCarro limit 1", nativeQuery = true)
    String verificarExistenciaCarrinho(@Param("usuario") Long usuario, @Param("idCarro") Long idCarro);    

    @Modifying
    @Query(value="delete from carrinho where id_usuario = :codigoUsuario and id_veiculo = :codigoCarro", nativeQuery=true)
    void deletarCairro(@Param("codigoUsuario") int codigoUsuario, @Param("codigoCarro") int codigoCarro);
}
