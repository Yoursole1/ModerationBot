package main.service;

import lombok.Getter;
import lombok.Setter;
import main.repository.Repository;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Getter
@Setter
public abstract class AbstractConfigurableService<T, ID extends Serializable> extends AbstractService<T, ID> {
    private Path configPath;

    AbstractConfigurableService(Repository<T, ID> repository, JDA jda, Path configPath) {
        super(repository, jda);
        this.configPath = configPath;
    }

    public abstract void init();
}
