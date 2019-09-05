package core.contracts;

import models.contracts.Board;
import models.contracts.Member;
import models.contracts.Team;

import java.util.LinkedHashMap;
import java.util.Map;

public interface TeamRepository {
    Map<String, Team> getTeams();

    void addTeam(String name, Team team);
}
