package main.service;

import lombok.Getter;
import lombok.Setter;
import main.repository.Repository;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public abstract class AbstractConfigurableMemberService<ID extends Serializable>
        extends AbstractConfigurableService<Member, ID> {

    AbstractConfigurableMemberService(Repository<Member, ID> repository, JDA jda, Path configPath) {
        super(repository, jda, configPath);
    }

    @Override
    public void init() {
        getRepository().deleteAll();
        Guild guild = getJda().getGuilds().get(0);
        try (Stream<String> input = Files.lines(getConfigPath())) {
            input
                    .map(guild::getMemberByTag)
                    .forEach(member -> getRepository().save(member));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
