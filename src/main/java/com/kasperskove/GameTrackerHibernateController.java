package com.kasperskove;

import com.kasperskove.entities.Game;
import com.kasperskove.interfaces.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GameTrackerHibernateController {

    @Autowired
    GameRepository games;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model) {
        Iterable<Game> gameList = games.findAll();
        model.addAttribute("games", gameList);
        return "home";
    }

    @RequestMapping(path = "/add-game", method = RequestMethod.POST)
    public String addGame(String gameName, String gamePlatform, String gameGenre, int gameYear) {
        Game game = new Game(gameName, gamePlatform, gameGenre, gameYear);
        games.save(game);
        return "redirect:/";
    }
}
