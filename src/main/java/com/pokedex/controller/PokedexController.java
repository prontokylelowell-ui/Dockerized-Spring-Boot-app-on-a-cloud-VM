package com.pokedex.controller;

import com.pokedex.model.Pokemon;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pokemon")
@CrossOrigin(origins = "*")
public class PokedexController {

    private final List<Pokemon> pokedex = new ArrayList<>();

    public PokedexController() {
        initPokedex();
    }

    private void initPokedex() {
        pokedex.add(new Pokemon(1, "Bulbasaur",
                Arrays.asList("Grass", "Poison"),
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
                "A strange seed was planted on its back at birth. The plant sprouts and grows with this Pokémon.",
                7, 69,
                Arrays.asList("Overgrow", "Chlorophyll"),
                new Pokemon.Stats(45, 49, 49, 65, 65, 45)));

        pokedex.add(new Pokemon(2, "Ivysaur",
                Arrays.asList("Grass", "Poison"),
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png",
                "When the bulb on its back grows large, it appears to lose the ability to stand on its hind legs.",
                10, 130,
                Arrays.asList("Overgrow", "Chlorophyll"),
                new Pokemon.Stats(60, 62, 63, 80, 80, 60)));

        pokedex.add(new Pokemon(3, "Venusaur",
                Arrays.asList("Grass", "Poison"),
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png",
                "The plant blooms when it is absorbing solar energy. It stays on the move to seek sunlight.",
                20, 1000,
                Arrays.asList("Overgrow", "Chlorophyll"),
                new Pokemon.Stats(80, 82, 83, 100, 100, 80)));

        pokedex.add(new Pokemon(4, "Charmander",
                Arrays.asList("Fire"),
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png",
                "Obviously prefers hot places. When it rains, steam is said to spout from the tip of its tail.",
                6, 85,
                Arrays.asList("Blaze", "Solar Power"),
                new Pokemon.Stats(39, 52, 43, 60, 50, 65)));

        pokedex.add(new Pokemon(5, "Charmeleon",
                Arrays.asList("Fire"),
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/5.png",
                "When it swings its burning tail, it elevates the temperature to unbearably high levels.",
                11, 190,
                Arrays.asList("Blaze", "Solar Power"),
                new Pokemon.Stats(58, 64, 58, 80, 65, 80)));

        pokedex.add(new Pokemon(6, "Charizard",
                Arrays.asList("Fire", "Flying"),
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/6.png",
                "Spits fire that is hot enough to melt boulders. Known to cause forest fires unintentionally.",
                17, 905,
                Arrays.asList("Blaze", "Solar Power"),
                new Pokemon.Stats(78, 84, 78, 109, 85, 100)));

        pokedex.add(new Pokemon(7, "Squirtle",
                Arrays.asList("Water"),
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/7.png",
                "After birth, its back swells and hardens into a shell. Powerfully sprays foam from its mouth.",
                5, 90,
                Arrays.asList("Torrent", "Rain Dish"),
                new Pokemon.Stats(44, 48, 65, 50, 64, 43)));

        pokedex.add(new Pokemon(8, "Wartortle",
                Arrays.asList("Water"),
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/8.png",
                "Often hides in water to stalk unwary prey. For swimming fast, it moves its ears to maintain balance.",
                10, 225,
                Arrays.asList("Torrent", "Rain Dish"),
                new Pokemon.Stats(59, 63, 80, 65, 80, 58)));

        pokedex.add(new Pokemon(9, "Blastoise",
                Arrays.asList("Water"),
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/9.png",
                "A brutal Pokémon with pressurized water jets on its shell. They are used for high speed tackles.",
                16, 855,
                Arrays.asList("Torrent", "Rain Dish"),
                new Pokemon.Stats(79, 83, 100, 85, 105, 78)));

        pokedex.add(new Pokemon(25, "Pikachu",
                Arrays.asList("Electric"),
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png",
                "When several of these Pokémon gather, their electricity could build and cause lightning storms.",
                4, 60,
                Arrays.asList("Static", "Lightning Rod"),
                new Pokemon.Stats(35, 55, 40, 50, 50, 90)));

        pokedex.add(new Pokemon(26, "Raichu",
                Arrays.asList("Electric"),
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/26.png",
                "Its long tail serves as a ground to protect itself from its own high voltage power.",
                8, 300,
                Arrays.asList("Static", "Lightning Rod"),
                new Pokemon.Stats(60, 90, 55, 90, 80, 110)));

        pokedex.add(new Pokemon(94, "Gengar",
                Arrays.asList("Ghost", "Poison"),
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/94.png",
                "Under a full moon, this Pokémon likes to mimic the shadows of people and laugh at their fright.",
                15, 405,
                Arrays.asList("Cursed Body"),
                new Pokemon.Stats(60, 65, 60, 130, 75, 110)));

        pokedex.add(new Pokemon(131, "Lapras",
                Arrays.asList("Water", "Ice"),
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/131.png",
                "A Pokémon that has been overhunted almost to extinction. It can ferry people across the water.",
                25, 2200,
                Arrays.asList("Water Absorb", "Shell Armor"),
                new Pokemon.Stats(130, 85, 80, 85, 95, 60)));

        pokedex.add(new Pokemon(143, "Snorlax",
                Arrays.asList("Normal"),
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/143.png",
                "Very lazy. Just eats and sleeps. As its rotund bulk builds, it becomes steadily more slothful.",
                21, 4600,
                Arrays.asList("Thick Fat", "Immunity"),
                new Pokemon.Stats(160, 110, 65, 65, 110, 30)));

        pokedex.add(new Pokemon(150, "Mewtwo",
                Arrays.asList("Psychic"),
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/150.png",
                "Created by a scientist after years of horrific gene splicing and DNA engineering experiments.",
                20, 1220,
                Arrays.asList("Pressure", "Unnerve"),
                new Pokemon.Stats(106, 110, 90, 154, 90, 130)));
    }

    @GetMapping
    public ResponseEntity<List<Pokemon>> getAllPokemon(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type) {
        List<Pokemon> result = pokedex.stream()
                .filter(p -> name == null || p.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(p -> type == null || p.getTypes().stream()
                        .anyMatch(t -> t.equalsIgnoreCase(type)))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pokemon> getPokemonById(@PathVariable int id) {
        Optional<Pokemon> pokemon = pokedex.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        return pokemon.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/types")
    public ResponseEntity<List<String>> getAllTypes() {
        List<String> types = pokedex.stream()
                .flatMap(p -> p.getTypes().stream())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        return ResponseEntity.ok(types);
    }
}
