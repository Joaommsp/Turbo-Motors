package br.com.turbomotors.turbomotors.Tabelas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the aluguel database table.
 * 
 */
@Entity
@NamedQuery(name="Aluguel.findAll", query="SELECT a FROM Aluguel a")
public class Aluguel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_aluguel")
	private Long idAluguel;

	@Column(name="data_fim")
	private LocalDateTime dataFim;

	@Column(name="data_inicio")
	private LocalDateTime dataInicio;

	@Column(name="valor_hora")
	private BigDecimal valorHora;
	
	@Column(name="status_aluguel")
	private String status;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	//bi-directional many-to-one association to Veiculo
	@ManyToOne
	@JoinColumn(name="id_carro")
	private Veiculo veiculo;

	//bi-directional many-to-one association to Pagamento
	@OneToMany(mappedBy="aluguel")
	private List<Pagamento> pagamentos;

	public Aluguel() {
	}

	public Long getIdAluguel() {
		return this.idAluguel;
	}
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setIdAluguel(Long idAluguel) {
		this.idAluguel = idAluguel;
	}

	public LocalDateTime getDataFim() {
		return this.dataFim;
	}

	public void setDataFim(LocalDateTime dataFim) {
		this.dataFim = dataFim;
	}

	public LocalDateTime getDataInicio() {
		return this.dataInicio;
	}

	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}

	public BigDecimal getValorHora() {
		return this.valorHora;
	}

	public void setValorHora(BigDecimal valorHora) {
		this.valorHora = valorHora;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Veiculo getVeiculo() {
		return this.veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public List<Pagamento> getPagamentos() {
		return this.pagamentos;
	}

	public void setPagamentos(List<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	public Pagamento addPagamento(Pagamento pagamento) {
		getPagamentos().add(pagamento);
		pagamento.setAluguel(this);

		return pagamento;
	}

	public Pagamento removePagamento(Pagamento pagamento) {
		getPagamentos().remove(pagamento);
		pagamento.setAluguel(null);

		return pagamento;
	}

}