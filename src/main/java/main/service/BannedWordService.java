package main.service;

import lombok.Getter;
import lombok.Setter;
import main.repository.BannedWordRepository;
import main.repository.BannedWordRepositoryImpl;
import main.repository.ModeratorRepository;
import main.repository.ModeratorRepositoryImpl;
import net.dv8tion.jda.api.JDA;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Getter
@Setter
public class BannedWordService extends AbstractConfigurableService<String, Integer> {
    public BannedWordService(BannedWordRepository repository, JDA jda, Path configPath) {
        super(repository, jda, configPath);
    }

    public BannedWordService(BannedWordRepository repository, JDA jda) {
        this(repository, jda, Paths.get("", "bannedwords.txt").toAbsolutePath().normalize());
    }

    public BannedWordService(JDA jda) {
        this(new BannedWordRepositoryImpl(), jda);
    }

    @Override
    public void init() {
        getRepository().deleteAll();
        try (Stream<String> input = Files.lines(getConfigPath())) {
            input.forEach(word -> getRepository().save(word));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
