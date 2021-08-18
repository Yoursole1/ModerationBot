package main.repository;

import net.dv8tion.jda.api.entities.Member;

import java.util.ArrayList;
import java.util.List;

public class ModeratorRepositoryImpl implements ModeratorRepository {

    private List<Member> database;

    public ModeratorRepositoryImpl(List<Member> database) {
        this.database = new ArrayList<>(database);
    }

    public ModeratorRepositoryImpl() {
        new ModeratorRepositoryImpl(new ArrayList<>());
    }

    @Override
    public void save(Member member) {
        this.database.add(member);
    }

    @Override
    public void save(List<Member> members) {
        this.database.addAll(members);
    }

    @Override
    public void delete(Member member) {
        this.database.remove(member);
    }

    @Override
    public void delete(int id) {
        this.database.remove(id);
    }

    @Override
    public void deleteAll() {
        this.database.clear();
    }

    @Override
    public Member get(int id) {
        return this.database.get(id);
    }

    @Override
    public List<Member> getAll() {
        return new ArrayList<>(this.database);
    }
}
