package br.com.turbomotors.turbomotors.Tabelas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the marca database table.
 * 
 */
@Entity
@NamedQuery(name="Marca.findAll", query="SELECT m FROM Marca m")
public class Marca implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_marca")
	private Long idMarca;

	@Column(name="mar_nome")
	private String marNome;

	//bi-directional many-to-one association to Veiculo
	@OneToMany(mappedBy="marca")
	private List<Veiculo> veiculos;

	public Marca() {
	}

	public Long getIdMarca() {
		return this.idMarca;
	}

	public void setIdMarca(Long idMarca) {
		this.idMarca = idMarca;
	}

	public String getMarNome() {
		return this.marNome;
	}

	public void setMarNome(String marNome) {
		this.marNome = marNome;
	}

	public List<Veiculo> getVeiculos() {
		return this.veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	public Veiculo addVeiculo(Veiculo veiculo) {
		getVeiculos().add(veiculo);
		veiculo.setMarca(this);

		return veiculo;
	}

	public Veiculo removeVeiculo(Veiculo veiculo) {
		getVeiculos().remove(veiculo);
		veiculo.setMarca(null);

		return veiculo;
	}

	@Override
	public String toString() {
		return "Marca [idMarca=" + idMarca + ", marNome=" + marNome + "]";
	}

	

}