package br.com.at.controller;

import br.com.at.exception.ResorceNotFoundException;
import br.com.at.model.Pokemon;
import br.com.at.model.ResponsePayload;
import br.com.at.service.PokemonService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log
@RestController
@RequestMapping("/pokemon")
public class PokemonController {
    @Autowired
    PokemonService pokemonService;

    @GetMapping
    public List<Pokemon> getAll(@RequestParam(required = false, defaultValue = "10") int size,
                                @RequestParam(required = false) Optional<String> nome){
        List<Pokemon> pokemons = pokemonService.getAll();
        if(nome.isPresent()){
            return pokemons = pokemonService.filterByName(nome.get());
        }else{
            return pokemons = pokemons.subList(0,size);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getPokeById(@PathVariable long id){
        try {
            Pokemon pokemon = pokemonService.getPokeById(id);
            return ResponseEntity.ok(pokemon);
        }catch (ResorceNotFoundException ex){
            ResponsePayload responsePayload = new ResponsePayload(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsePayload);
        }
    }

    @PostMapping
    public void create(@RequestBody Pokemon pokemon){
        pokemonService.create(pokemon);
        log.info(String.valueOf(HttpStatus.CREATED));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponsePayload> delete(@PathVariable Long id){
        try {
            pokemonService.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body((new ResponsePayload("Pokemon deletado com sucesso!")));
        }catch (ResorceNotFoundException ex){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponsePayload(ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload> update(@PathVariable Long id, @RequestBody Pokemon atualizado){
        try {
            pokemonService.update(id,atualizado);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ResponsePayload("Pokemon alterado com sucesso!"));
        }catch (ResorceNotFoundException ex){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponsePayload(ex.getMessage()));
        }
    }
}
