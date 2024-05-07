package br.com.turbomotors.turbomotors.Tabelas;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name = "carrinho")
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrinho")
    private Long idCarrinho;

    @ManyToOne
    @JoinColumn(name = "id_usuario") 
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_veiculo") 
    private Veiculo veiculo;

    @Column(name = "data_insercao")
    private LocalDateTime dataInsercao = LocalDateTime.now();

    @Column(name = "tipo_carrinho")
    private String tipoCarrinho;

    
    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    
    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    public Long getIdCarrinho() {
        return idCarrinho;
    }

    public void setIdCarrinho(Long idCarrinho) {
        this.idCarrinho = idCarrinho;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public LocalDateTime getData_insercao() {
        return dataInsercao;
    }

    public void setData_insercao(LocalDateTime dataInsercao) {
        this.dataInsercao = dataInsercao;
    }

    public String getTipoCarrinho() {
        return tipoCarrinho;
    }

    public void setTipoCarrinho(String tipoCarrinho) {
        this.tipoCarrinho = tipoCarrinho;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    
    
    
}
