package br.com.turbomotors.turbomotors.DTO;


public class MarcasDTO {
	   private String nomeMarca;
	   private Long qtdCompra;

	   public MarcasDTO(String nomeMarca, Long qtdCompra) {
	       this.nomeMarca = nomeMarca;
	       this.qtdCompra = qtdCompra;
	   }

	public String getNomeMarca() {
		return nomeMarca;
	}

	public void setNomeMarca(String nomeMarca) {
		this.nomeMarca = nomeMarca;
	}
	public Long getQtdCompra() {
		return qtdCompra;
	}

	public void setQtdCompra(Long qtdCompra) {
		this.qtdCompra = qtdCompra;
	}

	@Override
	public String toString() {
		return "MarcasDTO [nomeMarca=" + nomeMarca + ", qtdCompra=" + qtdCompra + "]";
	}

	  
	}