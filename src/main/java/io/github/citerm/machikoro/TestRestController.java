package io.github.citerm.machikoro;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ewc.decita.DecitaFacade;
import ru.ewc.decita.InMemoryStorage;
import ru.ewc.decita.Locators;

@RestController
@RequiredArgsConstructor
public class TestRestController {
    public static final String TABLE = "table";
    private final DecitaFacade decita;
    private final Storages storages;
    private final Map<String, String> playerNumbers = Map.of(
        "1", "playerOne",
        "2", "playerTwo",
        "3", "playerThree",
        "4", "playerFour",
        "5", "playerFive"
    );

    @PostMapping("/table")
    public void createTable(@RequestParam(required = false, defaultValue = "undefined") String player) {
        final Locators locators = storages.getLocators().mergedWith(
            new Locators(Map.of("request", new InMemoryStorage(Map.of("player", player))))
        );

        storages.log("before table creation");
        boolean isAvailable = Boolean.parseBoolean(decita.decisionFor("create_table", locators).get("enabled"));
        if (isAvailable) {
            storages.putToStorage(TABLE, "id", "0000");
            storages.putToStorage(TABLE, "isStarted", "false");
            storages.putToStorage(TABLE, "minPlayers", "1");
            storages.putToStorage(TABLE, "maxPlayers", "5");
            storages.putToStorage(TABLE, "players", "0");
            storages.putToStorage("playerOne", "name", "undefined");
            storages.putToStorage("playerTwo", "name", "undefined");
            storages.putToStorage("playerThree", "name", "undefined");
            storages.putToStorage("playerFour", "name", "undefined");
            storages.putToStorage("playerFive", "name", "undefined");
        }
        storages.log("after table creation");
    }

    @PostMapping("/seat")
    public void takeASeat(@RequestParam String player, @RequestParam String tableId) {
        final Locators locators = storages.getLocators().mergedWith(
            new Locators(Map.of("request", new InMemoryStorage(Map.of(
                "player", player,
                "tableId", tableId))))
        );

        storages.log("before taking a seat");
        boolean isAvailable = Boolean.parseBoolean(decita.decisionFor("take_a_seat", locators).get("enabled"));
        if (isAvailable) {
            final String newPlayersNumber = String.valueOf(Integer.parseInt(storages.getStorage(TABLE).fragmentBy("players", null)) + 1);
            storages.putToStorage(TABLE, "players", newPlayersNumber);
            storages.putToStorage(playerNumbers.get(newPlayersNumber), "name", player);
        }
        storages.log("after taking a seat");
    }
}
