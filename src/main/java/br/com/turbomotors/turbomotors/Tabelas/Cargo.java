package br.com.turbomotors.turbomotors.Tabelas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cargos database table.
 * 
 */

/**
 * The persistent class for the cargos database table.
 * 
 */
@Entity
@Table(name="cargos")
@NamedQuery(name="Cargo.findAll", query="SELECT c FROM Cargo c")
public class Cargo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cargos")
	private Long idCargos;

	@Column(name="nome_cargo")
	private String nomeCargo;

	//bi-directional many-to-one association to Funcionario
	@OneToMany(mappedBy="cargos")
	private List<Funcionarios> funcionarios;

	public Cargo() {
	}

	public Long getIdCargos() {
		return this.idCargos;
	}

	public void setIdCargos(Long idCargos) {
		this.idCargos = idCargos;
	}

	public String getNomeCargo() {
		return this.nomeCargo;
	}

	public void setNomeCargo(String nomeCargo) {
		this.nomeCargo = nomeCargo;
	}

	public List<Funcionarios> getFuncionarios() {
		return this.funcionarios;
	}

	public void setFuncionarios(List<Funcionarios> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public Funcionarios addFuncionario(Funcionarios funcionario) {
		getFuncionarios().add(funcionario);
		funcionario.setCargoBean(this);

		return funcionario;
	}

	public Funcionarios removeFuncionario(Funcionarios funcionario) {
		getFuncionarios().remove(funcionario);
		funcionario.setCargoBean(null);

		return funcionario;
	}

}