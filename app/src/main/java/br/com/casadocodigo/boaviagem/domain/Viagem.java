package br.com.casadocodigo.boaviagem.domain;

import java.util.Date;

public class Viagem {
    private Long id;
    private String destino;
    private Integer tipoViagem;
    private Date dataChegada;
    private Date dataSaida;
    private Double orcamento;
    private Integer quantidadePessoas;

    public Viagem(){}

    public Viagem(Long id, String destino, Integer tipoViagem, Date dataChegada, Date dataSaida, Integer quantidadePessoas, Double orcamenteo) {
        this.id = id;
        this.destino = destino;
        this.tipoViagem = tipoViagem;
        this.dataChegada = dataChegada;
        this.dataSaida = dataSaida;
        this.quantidadePessoas = quantidadePessoas;
        this.orcamento = orcamento;
    }

    public Long getId() {
        return id;
    }
    public String getDestino() {
        return destino;
    }
    public Integer getTipoViagem() {
        return tipoViagem;
    }
    public Date getDataChegada() {
        return dataChegada;
    }
    public Date getDataSaida() {
        return dataSaida;
    }
    public Double getOrcamento() {
        return orcamento;
    }
    public Integer getQuantidadePessoas() {
        return quantidadePessoas;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setDestino(String dstino) {
        this.destino = dstino;
    }
    public void setTipoViagem(Integer tipoViagem) {
        this.tipoViagem = tipoViagem;
    }
    public void setDataChegada(Date dataChegada) {
        this.dataChegada = dataChegada;
    }
    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }
    public void setOrcamento(Double orcamento) {
        this.orcamento = orcamento;
    }
    public void setQuantidadePessoas(Integer quantidadePessoas) {
        this.quantidadePessoas = quantidadePessoas;
    }
}
