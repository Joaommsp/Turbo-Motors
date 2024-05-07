package br.com.turbomotors.turbomotors.Tabelas;
import java.io.Serializable;
import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * The persistent class for the funcionarios database table.
 * 
 */
@Entity
@Table(name="funcionarios")
@NamedQuery(name="Funcionarios.findAll", query="SELECT f FROM Funcionarios f")
public class Funcionarios implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_funcionario")
	private Long id;

	
	@Column(name="cargo")
	private String cargo;
	
	@Column(name="email", unique = true)
	private String email;

	@Lob
	@Column(name="foto_funcionario")
	private byte[] fotoFuncionario;

	@Column(name="password")
	private String password;

	@Column(name="user", unique = true)
	private String user;
	

	@Column(name="url_anexo")
	private String url_anexo;

	private String primeiroAcesso;
	

	//bi-directional many-to-one association to Cargo
	@ManyToOne
	@JoinColumn(name = "id_cargos")
	private Cargo cargos;

	public Funcionarios() {
	}


	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getFotoFuncionario() {
		return this.fotoFuncionario;
	}

	public void setFotoFuncionario(byte[] fotoFuncionario) {
		this.fotoFuncionario = fotoFuncionario;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Cargo getCargoBean() {
		return this.cargos;
	}

	public void setCargoBean(Cargo cargoBean) {
		this.cargos = cargoBean;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUrl_anexo() {
		return url_anexo;
	}


	public void setUrl_anexo(String url_anexo) {
		this.url_anexo = url_anexo;
	}


	public Cargo getCargos() {
		return cargos;
	}


	public void setCargos(Cargo cargos) {
		this.cargos = cargos;
	}

	

	public String getPrimeiroAcesso() {
		return primeiroAcesso;
	}


	public void setPrimeiroAcesso(String primeiroAcesso) {
		this.primeiroAcesso = primeiroAcesso;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user;
	}


	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public String toString() {
		return "Funcionarios [id=" + id + ", cargo=" + cargo + ", email=" + email + ", fotoFuncionario="
				+ Arrays.toString(fotoFuncionario) + ", password=" + password + ", user=" + user + ", url_anexo="
				+ url_anexo + ", cargos=" + cargos + "]";
	}
	
	
	

}