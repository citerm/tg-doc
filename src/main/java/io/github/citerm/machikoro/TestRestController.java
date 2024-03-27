package io.github.citerm.machikoro;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ewc.decita.DecitaFacade;
import ru.ewc.decita.InMemoryStorage;
import ru.ewc.decita.Locators;

@RestController
@RequiredArgsConstructor
public class TestRestController {
    private final DecitaFacade decita;

    @GetMapping("/")
    public Map<String, String> index(@RequestParam(required = false, defaultValue = "undefined") String player) {

        return decita.decisionFor("create_table", new Locators(Map.of(
            "request", new InMemoryStorage(Map.of("player", player)
        ))));
    }

}
