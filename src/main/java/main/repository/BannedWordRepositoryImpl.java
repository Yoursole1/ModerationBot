package main.repository;

import lombok.Getter;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public class BannedWordRepositoryImpl implements BannedWordRepository {
    private final Path databasePath;
    private static BannedWordRepositoryImpl instance;

    public BannedWordRepositoryImpl(Path databasePath) {
        this.databasePath = databasePath;
    }

    public BannedWordRepositoryImpl() {
        this(Paths.get("", "bannedwords.txt").toAbsolutePath().normalize());
    }

    public static synchronized BannedWordRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new BannedWordRepositoryImpl();
        }
        return instance;
    }
}
