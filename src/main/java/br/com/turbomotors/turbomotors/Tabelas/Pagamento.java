package br.com.turbomotors.turbomotors.Tabelas;
import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * The persistent class for the pagamento database table.
 * 
 */
@Entity
@NamedQuery(name="Pagamento.findAll", query="SELECT p FROM Pagamento p")
public class Pagamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pagamento")
	private Long idPagamento;

	@Column(name="card_expiracao")
	private String cardExpiracao;

	@Column(name="data_pagamento")
	private LocalDateTime dataPagamento;

	private String descricao;

	@Column(name="nome_pagador")
	private String nomePagador;

	@Column(name="numero_cartao")
	private Long numeroCartao;

	@Column(name="valor_total")
	private BigDecimal valorTotal;

	//bi-directional many-to-one association to Aluguel
	@ManyToOne
	@JoinColumn(name="id_aluguel")
	private Aluguel aluguel;

	//bi-directional many-to-one association to Compra
	@ManyToOne
	@JoinColumn(name="id_compra")
	private Compra compra;
	
	
	private Long id_cliente;

	public Pagamento() {
	}

	
	
	public Long getId_cliente() {
		return id_cliente;
	}



	public void setId_cliente(Long id_cliente) {
		this.id_cliente = id_cliente;
	}



	public  Long getIdPagamento() {
		return this.idPagamento;
	}

	public void setIdPagamento(Long idPagamento) {
		this.idPagamento = idPagamento;
	}

	public String getCardExpiracao() {
		return this.cardExpiracao;
	}

	public void setCardExpiracao(String cardExpiracao) {
		this.cardExpiracao = cardExpiracao;
	}

	public LocalDateTime getDataPagamento() {
		return this.dataPagamento;
	}

	public void setDataPagamento(LocalDateTime dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNomePagador() {
		return this.nomePagador;
	}

	public void setNomePagador(String nomePagador) {
		this.nomePagador = nomePagador;
	}

	public Long getNumeroCartao() {
		return this.numeroCartao;
	}

	public void setNumeroCartao(Long numeroCartao) {
		this.numeroCartao = numeroCartao;
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

	public Compra getCompra() {
		return this.compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

}