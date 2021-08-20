package main.service;

import main.repository.BannedWordRepositoryImpl;

public final class BannedWordService extends AbstractStaticFileService<String, Integer> {
    private static BannedWordService instance;

    private BannedWordService() {
        super(BannedWordRepositoryImpl.getInstance());
    }

    public static synchronized BannedWordService getInstance() {
        if (instance == null) {
            instance = new BannedWordService();
        }
        return instance;
    }
}
