package core.factories;

import core.contracts.TeamFactory;
import models.TeamImpl;
import models.contracts.Team;

public class TeamFactoryImpl implements TeamFactory {

    @Override
    public Team createTeam(String name){
        return new TeamImpl(name);
    }
}
