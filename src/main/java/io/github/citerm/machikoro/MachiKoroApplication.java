package io.github.citerm.machikoro;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.ewc.decita.DecitaFacade;
import ru.ewc.decita.input.PlainTextContentReader;

@SpringBootApplication
public class MachiKoroApplication {

    public static void main(String[] args) {
        SpringApplication.run(MachiKoroApplication.class, args);
    }

    @Bean
    public DecitaFacade decitaFacade() {
        return new DecitaFacade(new PlainTextContentReader(tablesDirectory(), ".csv", ";")::allTables);
    }

    private URI tablesDirectory() {
        return Path.of(
            Paths.get("").toAbsolutePath().toString(),
            "Sources/tables"
        ).toUri();
    }

}
