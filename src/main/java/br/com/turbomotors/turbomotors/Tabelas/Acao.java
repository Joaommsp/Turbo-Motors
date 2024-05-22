package br.com.turbomotors.turbomotors.Tabelas;

import java.time.LocalDateTime;

public class Acao {
    private String guid;
    private String data;
    private LocalDateTime dataHoraFormatada;
    private int qtdCliques;
    private int qtdTempo;
    private String acao;

    // Construtor da classe Acao
    public Acao(String guid, String data, LocalDateTime dataHoraFormatada, int qtdCliques, int qtdTempo, String acao) {
        this.guid = guid;
        this.data = data;
        this.dataHoraFormatada = dataHoraFormatada;
        this.qtdCliques = qtdCliques;
        this.qtdTempo = qtdTempo;
        this.acao = acao;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public LocalDateTime getDataHoraFormatada() {
        return dataHoraFormatada;
    }

    public void setDataHoraFormatada(LocalDateTime dataHoraFormatada) {
        this.dataHoraFormatada = dataHoraFormatada;
    }

    public int getQtdCliques() {
        return qtdCliques;
    }

    public void setQtdCliques(int qtdCliques) {
        this.qtdCliques = qtdCliques;
    }

    public int getQtdTempo() {
        return qtdTempo;
    }

    public void setQtdTempo(int qtdTempo) {
        this.qtdTempo = qtdTempo;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    
}
