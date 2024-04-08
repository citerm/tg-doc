package io.github.citerm.machikoro;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.ewc.decita.InMemoryStorage;
import ru.ewc.decita.Locators;

@RestController
@RequiredArgsConstructor
public class TestRestController {
    public static final String ACTION = "action";
    private final Storages storages;
    private final Commands commands;


    @PostMapping("/move")
    public void makeAMove(@RequestBody Map<String, Object> move) {
        Locators locators = storages.getLocators().mergedWith(new Locators(Map.of("request", new InMemoryStorage(move))));
        commands.runCommand(extractActionNameFrom(move), locators);
    }

    private static String extractActionNameFrom(Map<String, Object> move) {
        if (!move.containsKey(ACTION)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Action is required");
        }
        return move.get(ACTION).toString();
    }
}
