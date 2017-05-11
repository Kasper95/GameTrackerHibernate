package com.kasperskove.interfaces;

import com.kasperskove.entities.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Integer> {
    List<Game> findByGenre(String genre);
}
