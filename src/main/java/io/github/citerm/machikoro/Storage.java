package io.github.citerm.machikoro;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import ru.ewc.decita.ComputationContext;
import ru.ewc.decita.DecitaException;
import ru.ewc.decita.Locator;

public class Storage implements Locator {
    private final String name;
    private final Map<String, String> valuesByFragment = new HashMap<>();

    public Storage(String name) {
        this.name = name;
    }

    @Override
    public String fragmentBy(String fragment, ComputationContext context) throws DecitaException {
        return valuesByFragment.get(fragment);
    }

    public void putFragment(String fragment, String value) {
        valuesByFragment.put(fragment, value);
    }

    public String log() {
        return valuesByFragment.entrySet().stream()
            .map(e -> "  %s: %s".formatted(e.getKey(), e.getValue()))
            .collect(Collectors.joining("\n"));
    }
}
