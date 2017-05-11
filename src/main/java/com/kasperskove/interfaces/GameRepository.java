package com.kasperskove.interfaces;

import com.kasperskove.entities.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Integer> {
}
