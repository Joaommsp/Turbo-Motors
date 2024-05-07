package br.com.turbomotors.turbomotors.Tabelas;


import java.io.Serializable;
import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;



/**
 * The persistent class for the cliente database table.
 * 
 */
@Entity
@NamedQuery(name="Cliente.findAll", query="SELECT c FROM Cliente c")
public class Cliente implements UserDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cliente")
	private Long idCliente;

	@Column(name="cpf_cnpj")
	private String cpfCnpj;

	private String email;

	private String fisica;

	@Lob
	@Column(name="foto_cliente")
	private byte[] fotoCliente;

	@Column(name="nome_usuario", unique = true)
	private String nomeUsuario;

	@Column(name="pes_nome")
	private String pesNome;

	private String senha;

	private String sexo;

	@Column(unique = true)
	private String telefone;
	
	private String url_anexo;

	//bi-directional many-to-one association to Aluguel
	@OneToMany(mappedBy="cliente")
	private List<Aluguel> aluguels;

	//bi-directional many-to-one association to Compra
	@OneToMany(mappedBy="cliente")
	private List<Compra> compras;

	//bi-directional many-to-one association to Endereco
	@OneToMany(mappedBy="cliente")
	private List<Endereco> enderecos;

	public Cliente(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}

	
	
	public String getUrl_anexo() {
		return url_anexo;
	}



	public void setUrl_anexo(String url_anexo) {
		this.url_anexo = url_anexo;
	}



	public Cliente() {
	}

	public Long getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getCpfCnpj() {
		return this.cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFisica() {
		return this.fisica;
	}

	public void setFisica(String fisica) {
		this.fisica = fisica;
	}

	public byte[] getFotoCliente() {
		return this.fotoCliente;
	}

	public void setFotoCliente(byte[] fotoCliente) {
		this.fotoCliente = fotoCliente;
	}

	public String getNomeUsuario() {
		return this.nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getPesNome() {
		return this.pesNome;
	}

	public void setPesNome(String pesNome) {
		this.pesNome = pesNome;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public List<Aluguel> getAluguels() {
		return this.aluguels;
	}

	public void setAluguels(List<Aluguel> aluguels) {
		this.aluguels = aluguels;
	}

	public Aluguel addAluguel(Aluguel aluguel) {
		getAluguels().add(aluguel);
		aluguel.setCliente(this);

		return aluguel;
	}

	public Aluguel removeAluguel(Aluguel aluguel) {
		getAluguels().remove(aluguel);
		aluguel.setCliente(null);

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
		compra.setCliente(this);

		return compra;
	}

	public Compra removeCompra(Compra compra) {
		getCompras().remove(compra);
		compra.setCliente(null);

		return compra;
	}

	public List<Endereco> getEnderecos() {
		return this.enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Endereco addEndereco(Endereco endereco) {
		getEnderecos().add(endereco);
		endereco.setCliente(this);

		return endereco;
	}

	public Endereco removeEndereco(Endereco endereco) {
		getEnderecos().remove(endereco);
		endereco.setCliente(null);

		return endereco;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return senha;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
				return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
				return true;
	}

	@Override
	public boolean isEnabled() {
				return true;
	}



	@Override
	public String toString() {
		return "Cliente [idCliente=" + idCliente + ", cpfCnpj=" + cpfCnpj + ", email=" + email + ", fisica=" + fisica
				+ ", fotoCliente=" + Arrays.toString(fotoCliente) + ", nomeUsuario=" + nomeUsuario + ", pesNome="
				+ pesNome + ", senha=" + senha + ", sexo=" + sexo + ", telefone=" + telefone + "]";
	}

	

	

}