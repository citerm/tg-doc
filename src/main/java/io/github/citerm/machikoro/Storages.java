package io.github.citerm.machikoro;

import java.util.HashMap;
import java.util.Map;
import ru.ewc.decita.Locator;
import ru.ewc.decita.Locators;

public class Storages {
    private final Map<String, Locator> storages = new HashMap<>();

    public void putToStorage(String name, String fragment, String value) {
        ((Storage) storages.computeIfAbsent(name, Storage::new)).putFragment(fragment, value);
    }

    public Locator getStorage(String name) {
        return storages.get(name);
    }

    public Locators getLocators() {
        return new Locators(storages);
    }

    public void log(String description) {
        System.out.println(description);
        storages.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .map(e -> "%s:\n%s".formatted(e.getKey(), ((Storage) e.getValue()).log()))
            .forEach(System.out::println);
        System.out.println("---");
    }
}
