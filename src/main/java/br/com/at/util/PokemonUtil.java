package br.com.at.util;

import br.com.at.model.Pokemon;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.java.Log;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

@Log
public class PokemonUtil {
    private final String URL = "https://pokeapi.co/api/v2/pokemon/";

    public Pokemon getPokemon(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .version(HttpClient.Version.HTTP_2)
                    .uri(new URI(URL + id))
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
            Pokemon pokeDetails = mapper.readValue(response.body(), Pokemon.class);

            String nome = pokeDetails.getNome();
            //id
            int altura = pokeDetails.getAltura();
            int peso = pokeDetails.getPeso();
            List<String> habilidades = getPokeHabilidades(pokeDetails);
            List<String> tipos = getPokeTipos(pokeDetails);

            Pokemon pokemon = new Pokemon((long) id, nome, altura, peso,tipos,habilidades);

            return pokemon;

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getPokeHabilidades(Pokemon poke) {
        List<String> habilidades = new LinkedList<>();
        for (int i = 0; i < poke.getHabilidades().size(); i++) {
            Object tipos = poke.getHabilidades().get(i);

            if (tipos instanceof Map) {
                Map<String, Object> tiposList = (Map<String, Object>) tipos;
                Object tipoList = tiposList.get("ability");
                if (tipoList instanceof Map) {
                    Map<String, Object> habilidadeList = (Map<String, Object>) tipoList;
                    Object nomeHabilidade = habilidadeList.get("name");
                    habilidades.add(nomeHabilidade.toString());
                }
            }
        }
        return habilidades;
    }

    private List<String> getPokeTipos(Pokemon poke) {
        List<String> tiposList = new LinkedList<>();
        for (int i = 0; i < poke.getTipos().size(); i++) {
            Object tipos1 = poke.getTipos().get(i);
            if (tipos1 instanceof Map) {
                Map<String, Object> tipo = (Map<String, Object>) tipos1;
                Object habilNome = tipo.get("type");
                if (habilNome instanceof Map) {
                    Map<String, Object> nome = (Map<String, Object>) habilNome;
                    Object pokeTipo = nome.get("name");
                    tiposList.add(pokeTipo.toString());
                }
            }
        }
        return tiposList;
    }
}
