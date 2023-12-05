package br.com.at.service;

import br.com.at.exception.ResorceNotFoundException;
import br.com.at.model.Pokemon;
import br.com.at.util.PokemonUtil;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log
@Service
public class PokemonService {
    private Map<Long, Pokemon> pokemons = iniciarPokes();
    private Long lastId = (long)getAll().size();

    private Map<Long, Pokemon> iniciarPokes() {
        Map<Long, Pokemon> pokemons = new HashMap<>();
        PokemonUtil pokemonUtil = new PokemonUtil();
        for (int i = 1; i<=151;i++){
            Pokemon poke = pokemonUtil.getPokemon(i);
            Pokemon p1 = new Pokemon(poke.getID(),poke.getNome(),poke.getAltura(),poke.getPeso(),poke.getTipos(),poke.getHabilidades());
            pokemons.put(p1.getID(),p1);
        }
        return pokemons;
    }

    public List<Pokemon> getAll() {
        return pokemons.values().stream().toList();
    }

    public Pokemon getPokeById(long id) {
        Pokemon pokemon = pokemons.get(id);
        if (pokemon == null){
            throw new ResorceNotFoundException("Pokemon não encontrado");
        }
        return pokemon;
    }

    public void create(Pokemon pokemon) {
        Long id = ++this.lastId;
        pokemon.setID(id);
        pokemons.put(id,pokemon);
    }

    public void deleteById(long id) {
        Pokemon pokemon = pokemons.remove(id);
        if (pokemon == null){
            throw new ResorceNotFoundException("Pokemon não existe");
        }
    }

    public void update(long id, Pokemon atualizado) {
        atualizado.setID(id);
        pokemons.replace(id,atualizado);
    }

    public List<Pokemon> filterByName(String nome) {
        List<Pokemon> all = getAll();
        return all.stream().filter(cliente -> cliente.getNome().startsWith(nome)).toList();
    }
}
