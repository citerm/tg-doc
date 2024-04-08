package io.github.citerm.machikoro;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import ru.ewc.decita.DecitaException;
import ru.ewc.decita.DecitaFacade;
import ru.ewc.decita.Locators;

public class Commands {
    public static final String TABLE = "table";
    private static final Map<String, String> playerNumbers = Map.of(
        "1", "playerOne",
        "2", "playerTwo",
        "3", "playerThree",
        "4", "playerFour",
        "5", "playerFive"
    );
    private final Storages storages;
    private final DecitaFacade decita;

    public Commands(Storages storages, DecitaFacade decita) {
        this.storages = storages;
        this.decita = decita;
    }

    void createTable(Locators context) {
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

    void takeASeat(Locators context) {
        final String newPlayersNumber =
            String.valueOf(Integer.parseInt(context.locatorFor(TABLE).fragmentBy("players", null)) + 1);
        final String player = context.locatorFor("request").fragmentBy("player", null);

        storages.putToStorage(TABLE, "players", newPlayersNumber);
        storages.putToStorage(playerNumbers.get(newPlayersNumber), "name", player);
    }

    void runCommand(String commandName, Locators context) {
        logBefore(commandName);
        checkAvailability(commandName, context);
        performCommand(commandName, context);
        logAfter(commandName);
    }

    void performCommand(String commandName, Locators context) {
        switch (commandName) {
            case "create_table" -> createTable(context);
            case "take_a_seat" -> takeASeat(context);
            default -> throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Unknown action");
        }
    }

    void checkAvailability(String commandName, Locators locators) {
        final String enabled;
        try {
            enabled = decita.decisionFor(commandName, locators).get("enabled");
            if (!Boolean.parseBoolean(enabled)) {
                throw new HttpClientErrorException(
                    HttpStatus.FORBIDDEN,
                    ("Command %s is not available").formatted(commandName)
                );
            }
        } catch (DecitaException e) {
            throw new HttpClientErrorException(
                HttpStatus.BAD_REQUEST,
                ("Command %s not found at all").formatted(commandName)
            );
        }
    }

    void logAfter(String commandName) {
        storages.log("after %s".formatted(commandName));
    }

    void logBefore(String commandName) {
        storages.log("before %s".formatted(commandName));
    }
}
