package main.service;

import lombok.Getter;
import lombok.Setter;
import main.repository.Repository;
import net.dv8tion.jda.api.JDA;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public abstract class AbstractService<T, ID extends Serializable> implements Service<T, ID> {
    private final Repository<T, ID> repository;
    private JDA jda;

    AbstractService(Repository<T, ID> repository, JDA jda) {
        this.repository = repository;
        this.jda = jda;
    }
}
