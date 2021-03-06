package com.kasperskove.interfaces;

import com.kasperskove.entities.Game;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Integer> {

    List<Game> findByGenre(String genre);
    List<Game> findByReleaseYear(int year);

    @Query("SELECT g FROM Game g WHERE g.name LIKE ?1%")
    List<Game> findByNameStartsWith(String name);

    @Query("SELECT g FROM Game g WHERE g.releaseYear LIKE ?1%")
    List<Game> findByReleaseYear(Integer year);
}
