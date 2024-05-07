package br.com.turbomotors.turbomotors.Tabelas;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the nota_fiscal database table.
 * 
 */
@Entity
@Table(name="nota_fiscal")
@NamedQuery(name="NotaFiscal.findAll", query="SELECT n FROM NotaFiscal n")
public class NotaFiscal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_nota_fiscal")
	private int idNotaFiscal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_emissao")
	private Date dataEmissao;

	@Column(name="valor_total")
	private BigDecimal valorTotal;

	//bi-directional many-to-one association to Aluguel
	@ManyToOne
	@JoinColumn(name="id_aluguel")
	private Aluguel aluguel;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	//bi-directional many-to-one association to Compra
	@ManyToOne
	@JoinColumn(name="id_compra")
	private Compra compra;

	public NotaFiscal() {
	}

	public int getIdNotaFiscal() {
		return this.idNotaFiscal;
	}

	public void setIdNotaFiscal(int idNotaFiscal) {
		this.idNotaFiscal = idNotaFiscal;
	}

	public Date getDataEmissao() {
		return this.dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public BigDecimal getValorTotal() {
		return this.valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Aluguel getAluguel() {
		return this.aluguel;
	}

	public void setAluguel(Aluguel aluguel) {
		this.aluguel = aluguel;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Compra getCompra() {
		return this.compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

}