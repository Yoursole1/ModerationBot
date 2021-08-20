package main.repository;

import lombok.Getter;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public class AdminRepositoryImpl implements AdminRepository {
    private final Path databasePath;
    private static AdminRepositoryImpl instance;

    public AdminRepositoryImpl(Path databasePath) {
        this.databasePath = databasePath;
    }

    public AdminRepositoryImpl() {
        this(Paths.get("", "admins.txt").toAbsolutePath().normalize());
    }

    public static synchronized AdminRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new AdminRepositoryImpl();
        }
        return instance;
    }
}
