package br.com.at.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;


import java.util.List;

@Data@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {
    @JsonProperty("id")
    private Long ID;
    @JsonProperty("name")
    private String Nome;
    @JsonProperty("height")
    private int Altura;
    @JsonProperty("weight")
    private int Peso;
    @JsonProperty("types")
    private List Tipos;
    @JsonProperty("abilities")
    private List Habilidades;

    public Pokemon(Long ID, String nome, int altura, int peso, List tipos, List habilidades) {
        this.ID = ID;
        Nome = nome;
        Altura = altura;
        Peso = peso;
        Tipos = tipos;
        Habilidades = habilidades;
    }
    public Pokemon(){

    }

}
