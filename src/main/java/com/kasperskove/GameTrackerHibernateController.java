package com.kasperskove;

import com.kasperskove.entities.Game;
import com.kasperskove.entities.User;
import com.kasperskove.interfaces.GameRepository;
import com.kasperskove.interfaces.UserRepository;
import com.kasperskove.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class GameTrackerHibernateController {

    @Autowired
    private UserRepository users;

    @Autowired
    private GameRepository games;

    // retrieves list of games
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model, String genre, Integer releaseYear, String search, String searchYear) {
        String userName = (String) session.getAttribute("userName");
        User user = users.findFirstByName(userName);
        if (user != null) {
            model.addAttribute("user", user);
        }
        List<Game> gameList;
        if (genre != null) {
            gameList = games.findByGenre(genre);
        } else if (releaseYear != null) {
            gameList = games.findByReleaseYear(releaseYear);
        } else if (search != null) {
            gameList = games.findByNameStartsWith(search);
        } else if (searchYear != null){
            gameList = games.findByReleaseYear(Integer.parseInt(searchYear));
        } else {
            gameList = (List<Game>) games.findAll();
        }
        model.addAttribute("games", gameList);
        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password) throws Exception {
        User user = users.findFirstByName(userName);
        if (user == null) {
            user = new User(userName, PasswordStorage.createHash(password));
            users.save(user);
        }
        else if (!PasswordStorage.verifyPassword(password, user.getPassword())) {
            throw new Exception("Incorrect password");
        }
        session.setAttribute("userName", userName);
        return "redirect:/";
    }


    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // adds game entry to list
    @RequestMapping(path = "/add-game", method = RequestMethod.POST)
    public String addGame(HttpSession session, String gameName, String gamePlatform, String gameGenre, int gameYear) {
        String userName = (String) session.getAttribute("userName");
        User user = users.findFirstByName(userName);
        Game game = new Game(gameName, gamePlatform, gameGenre, gameYear, user);
        games.save(game);

        return "redirect:/";
    }
}
