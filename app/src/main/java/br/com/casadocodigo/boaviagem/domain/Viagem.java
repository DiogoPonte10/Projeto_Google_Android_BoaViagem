package br.com.casadocodigo.boaviagem.domain;

import java.util.Date;

public class Viagem {
    private Long id;
    private String dstino;
    private Integer tipoViagem;
    private Date dataChegada;
    private Date dataSaida;
    private Double orcamenteo;
    private Integer quantidadePessoas;

    public Viagem(){}

    public Viagem(Long id, String dstino, Integer tipoViagem, Date dataChegada, Date dataSaida, Integer quantidadePessoas, Double orcamenteo) {
        this.id = id;
        this.dstino = dstino;
        this.tipoViagem = tipoViagem;
        this.dataChegada = dataChegada;
        this.dataSaida = dataSaida;
        this.quantidadePessoas = quantidadePessoas;
        this.orcamenteo = orcamenteo;
    }

    public Long getId() {
        return id;
    }
    public String getDstino() {
        return dstino;
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
    public Double getOrcamenteo() {
        return orcamenteo;
    }
    public Integer getQuantidadePessoas() {
        return quantidadePessoas;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setDstino(String dstino) {
        this.dstino = dstino;
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
    public void setOrcamenteo(Double orcamenteo) {
        this.orcamenteo = orcamenteo;
    }
    public void setQuantidadePessoas(Integer quantidadePessoas) {
        this.quantidadePessoas = quantidadePessoas;
    }
}
