package br.com.at;

import br.com.at.model.Pokemon;
import br.com.at.util.PokemonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.java.Log;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log
public class PokemonUtilTest {
    @Test
    @DisplayName("TestNaApi: Busca um pokemon pelo ID")
    public void buscaPokePeloId() {
        PokemonUtil pokeUtil = new PokemonUtil();
        Pokemon poke = pokeUtil.getPokemon(1);
        assertEquals("bulbasaur", poke.getNome());
        log.info(poke.getNome());
    }
}
