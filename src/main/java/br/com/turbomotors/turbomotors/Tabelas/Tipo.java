package br.com.turbomotors.turbomotors.Tabelas;
import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the tipo database table.
 * 
 */
@Entity
@NamedQuery(name="Tipo.findAll", query="SELECT t FROM Tipo t")
public class Tipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo")
	private Long idTipo;

	@Column(name="tipo_nome", unique = true)
	private String tipoNome;

	@Column(name="valor_aluguel")
	private BigDecimal valorAluguel;

	//bi-directional many-to-one association to Veiculo
	@OneToMany(mappedBy="tipo", fetch = FetchType.LAZY)
	private List<Veiculo> veiculos;

	public Tipo() {
	}

	public Long getIdTipo() {
		return this.idTipo;
	}

	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}

	public String getTipoNome() {
		return this.tipoNome;
	}

	public void setTipoNome(String tipoNome) {
		this.tipoNome = tipoNome;
	}

	public BigDecimal getValorAluguel() {
		return this.valorAluguel;
	}

	public void setValorAluguel(BigDecimal valorAluguel) {
		this.valorAluguel = valorAluguel;
	}

	public List<Veiculo> getVeiculos() {
		return this.veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	public Veiculo addVeiculo(Veiculo veiculo) {
		getVeiculos().add(veiculo);
		veiculo.setTipo(this);

		return veiculo;
	}

	public Veiculo removeVeiculo(Veiculo veiculo) {
		getVeiculos().remove(veiculo);
		veiculo.setTipo(null);

		return veiculo;
	}

    public void setIdMarca(long l) {
    }

	@Override
	public String toString() {
		return "Tipo [idTipo=" + idTipo + ", tipoNome=" + tipoNome + ", valorAluguel=" + valorAluguel + "]";
	}


	

}