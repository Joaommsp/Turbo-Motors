package br.com.turbomotors.turbomotors.DTO;

public class UsuarioLogadoDTO {
    private String user;
    private String urlAnexo;
    private String nomeCargo;
	private String primeiroAcesso;
	public UsuarioLogadoDTO(String user, String urlAnexo, String nomeCargo, String primeiroAcesso) {
		super();
		this.user = user;
		this.urlAnexo = urlAnexo;
		this.nomeCargo = nomeCargo;
		this.primeiroAcesso = primeiroAcesso;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getUrlAnexo() {
		return urlAnexo;
	}
	public void setUrlAnexo(String urlAnexo) {
		this.urlAnexo = urlAnexo;
	}
	public String getNomeCargo() {
		return nomeCargo;
	}
	public void setNomeCargo(String nomeCargo) {
		this.nomeCargo = nomeCargo;
	}

	public String getPrimeiroAcesso() {
		return primeiroAcesso;
	}
	public void setPrimeiroAcesso(String primeiroAcesso) {
		this.primeiroAcesso = primeiroAcesso;
	}
    
	

	@Override
	public String toString() {
		return "UsuarioLogadoDTO [user=" + user + ", urlAnexo=" + urlAnexo + ", nomeCargo=" + nomeCargo + "]";
	}

    
}