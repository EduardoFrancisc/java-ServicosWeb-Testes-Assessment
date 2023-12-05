package br.com.at;

import br.com.at.exception.ResorceNotFoundException;
import br.com.at.model.Pokemon;
import br.com.at.service.PokemonService;
import lombok.extern.java.Log;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Log
@SpringBootTest
public class PokemonControllerTest {
    @Autowired
    PokemonService pokemonService;

    @Test
    @DisplayName("Deve retornar o valor correto de pokemons")
    public void deveRetornarTodosPokemons(){
        List<Pokemon> pokemons = pokemonService.getAll();
        assertEquals(151,pokemons.size());
    }

    @Test
    @DisplayName("Deve retornar um pokemon pelo id")
    public void deveRetornarUmPokePeloId(){
        Pokemon pokemon = pokemonService.getPokeById(2);
        assertEquals("ivysaur",pokemon.getNome());
        assertEquals(2,pokemon.getID());
        assertThrows(ResorceNotFoundException.class,()->{
           pokemonService.getPokeById(-1);
        });
    }

    @Test
    @DisplayName("Deve criar um novo pokemon")
    public void deveCriarUmPokemon(){
        Pokemon charmeleon = Pokemon.builder()
                .Nome("charmeleon")
                .Altura(11)
                .Peso(190)
                .Tipos(List.of("fire"))
                .Habilidades(List.of("blaze","solar-power"))
                .build();
        pokemonService.create(charmeleon);
        Pokemon pikachu = Pokemon.builder()
                .Nome("pikachu")
                .Altura(15)
                .Peso(198)
                .Tipos(List.of("eletrick"))
                .Habilidades(List.of("blaze","raio"))
                .build();
        pokemonService.create(pikachu);
        assertEquals(152,pokemonService.getAll().size());
    }

    @Test
    @DisplayName("Deve deletar um pokemon")
    public void deveDeletarUmPokemon(){
        pokemonService.deleteById(1);
        assertEquals(150,pokemonService.getAll().size());
    }

    @Test
    @DisplayName("Deve atualizar um pokemon")
    public void deveAtualizarUmPokemom(){
        Pokemon charmeleon = Pokemon.builder()
                .Nome("charmeleon")
                .Altura(11)
                .Peso(190)
                .Tipos(List.of("fire"))
                .Habilidades(List.of("blaze","solar-power"))
                .build();
        pokemonService.update(3,charmeleon);
        Pokemon poke = pokemonService.getPokeById(3);
        assertEquals("charmeleon",poke.getNome());
        assertEquals(190,poke.getPeso());
        assertEquals(11,poke.getAltura());
    }


}
