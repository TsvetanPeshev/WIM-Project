package core;

import core.contracts.TeamRepository;
import models.contracts.Team;

import java.util.LinkedHashMap;
import java.util.Map;

public class TeamRepositoryImpl implements TeamRepository {

    private Map<String, Team> teams;

    public TeamRepositoryImpl() {
        teams = new LinkedHashMap<>();
    }

    @Override
    public Map<String, Team> getTeams() {
        return new LinkedHashMap<>(teams);
    }

    @Override
    public void addTeam(String name, Team team) {
        teams.put(name, team);
    }
}
