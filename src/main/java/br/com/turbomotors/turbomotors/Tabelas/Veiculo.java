package br.com.turbomotors.turbomotors.Tabelas;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the veiculo database table.
 * 
 */
@Entity
@NamedQuery(name="Veiculo.findAll", query="SELECT v FROM Veiculo v")
public class Veiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id     
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_carro")
	private Long idCarro;

	@Column(name="car_ano")
	private int carAno;

	@Column(name="car_descricao")
	private String carDescricao;

	@Lob
	@Column(name="car_imagem")
	private byte[] carImagem;

	@Column(name="car_nome")
	private String carNome;

	@Column(name="car_placa", unique =  true)
	private String carPlaca;

	@Column(name="car_url")
	private String carUrl;

	@Column(name="car_valor")
	private BigDecimal carValor;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_ultima")
	private Date dataUltima;

	@Column(name="Disponivel")
	private String disponivel;

	//bi-directional many-to-one association to Aluguel
	@OneToMany(mappedBy="veiculo")
	private List<Aluguel> aluguels;

	//bi-directional many-to-one association to Compra
	@OneToMany(mappedBy="veiculo")
	private List<Compra> compras;

	//bi-directional many-to-one association to Marca
	@ManyToOne
	@JoinColumn(name="id_marca")
	private Marca marca;

	//bi-directional many-to-one association to Tipo
	@ManyToOne
	@JoinColumn(name="id_tipo")
	private Tipo tipo;


	@Column(name="car_nome_arquivo")
	private String car_nome_arquivo;

	public Veiculo() {
	}

	public String getCar_nome_arquivo() {
		return car_nome_arquivo;
	}

	public void setCar_nome_arquivo(String car_nome_arquivo) {
		this.car_nome_arquivo = car_nome_arquivo;
	}

	public Long getIdCarro() {
		return this.idCarro;
	}

	public void setIdCarro(Long idCarro) {
		this.idCarro = idCarro;
	}

	public int getCarAno() {
		return this.carAno;
	}

	public void setCarAno(int carAno) {
		this.carAno = carAno;
	}

	public String getCarDescricao() {
		return this.carDescricao;
	}

	public void setCarDescricao(String carDescricao) {
		this.carDescricao = carDescricao;
	}

	public byte[] getCarImagem() {
		return this.carImagem;
	}

	public void setCarImagem(byte[] carImagem) {
		this.carImagem = carImagem;
	}

	public String getCarNome() {
		return this.carNome;
	}

	public void setCarNome(String carNome) {
		this.carNome = carNome;
	}


	public String getCarPlaca() {
		return this.carPlaca;
	}

	public void setCarPlaca(String carPlaca) {
		this.carPlaca = carPlaca;
	}

	public String getCarUrl() {
		return this.carUrl;
	}

	public void setCarUrl(String carUrl) {
		this.carUrl = carUrl;
	}

	public BigDecimal getCarValor() {
		return this.carValor;
	}

	public void setCarValor(BigDecimal carValor) {
		this.carValor = carValor;
	}

	public Date getDataUltima() {
		return this.dataUltima;
	}

	public void setDataUltima(Date dataUltima) {
		this.dataUltima = dataUltima;
	}

	public String getDisponivel() {
		return this.disponivel;
	}

	public void setDisponivel(String disponivel) {
		this.disponivel = disponivel;
	}

	public List<Aluguel> getAluguels() {
		return this.aluguels;
	}

	public void setAluguels(List<Aluguel> aluguels) {
		this.aluguels = aluguels;
	}

	public Aluguel addAluguel(Aluguel aluguel) {
		getAluguels().add(aluguel);
		aluguel.setVeiculo(this);

		return aluguel;
	}

	public Aluguel removeAluguel(Aluguel aluguel) {
		getAluguels().remove(aluguel);
		aluguel.setVeiculo(null);

		return aluguel;
	}

	public List<Compra> getCompras() {
		return this.compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	public Compra addCompra(Compra compra) {
		getCompras().add(compra);
		compra.setVeiculo(this);

		return compra;
	}

	public Compra removeCompra(Compra compra) {
		getCompras().remove(compra);
		compra.setVeiculo(null);

		return compra;
	}

	public Marca getMarca() {
		return this.marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Tipo getTipo() {
		return this.tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Veiculo [carDescricao=" + carDescricao + ", carNome=" + carNome + ", carPlaca=" + carPlaca + ", carUrl="
				+ carUrl + ", carValor=" + carValor + ", disponivel=" + disponivel + ", marca=" + marca + ", tipo="
				+ tipo + "]";
	}



}