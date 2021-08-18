package main.service;

import lombok.Getter;
import lombok.Setter;
import main.repository.AdminRepository;
import main.repository.AdminRepositoryImpl;
import main.repository.ModeratorRepository;
import main.repository.ModeratorRepositoryImpl;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Getter
@Setter
public class AdminService extends AbstractConfigurableMemberService<Integer> {

    public AdminService(AdminRepository repository, JDA jda, Path configPath) {
        super(repository, jda, configPath);
    }

    public AdminService(AdminRepository repository, JDA jda) {
        this(repository, jda, Paths.get("", "admins.txt").toAbsolutePath().normalize());
    }

    public AdminService(JDA jda) {
        this(new AdminRepositoryImpl(), jda);
    }
}
