package main.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.repository.ModeratorRepository;
import main.repository.ModeratorRepositoryImpl;
import main.repository.Repository;
import main.utils.Utils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Getter
@Setter
public class ModeratorService extends AbstractConfigurableMemberService<Integer> {

    public ModeratorService(ModeratorRepository repository, JDA jda, Path configPath) {
        super(repository, jda, configPath);
    }

    public ModeratorService(ModeratorRepository repository, JDA jda) {
        this(repository, jda, Paths.get("", "moderators.txt").toAbsolutePath().normalize());
    }

    public ModeratorService(JDA jda) {
        this(new ModeratorRepositoryImpl(), jda);
    }
}
