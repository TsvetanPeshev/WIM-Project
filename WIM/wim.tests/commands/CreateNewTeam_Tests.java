package commands;

import commands.contracts.Command;
import core.TeamRepositoryImpl;
import core.contracts.TeamFactory;
import core.contracts.TeamRepository;
import core.factories.TeamFactoryImpl;
import models.contracts.Team;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CreateNewTeam_Tests {

    private TeamRepository teamRepository;
    private TeamFactory teamFactory;
    private Command testCommand;

    @Before
    public void before() {
        teamRepository = new TeamRepositoryImpl();
        teamFactory = new TeamFactoryImpl();
        testCommand = new CreateNewTeam(teamFactory, teamRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throwException_when_passedLessArguments() {
        List<String> parameters = new ArrayList<>();
        parameters.add("createnewteam");
        parameters.add("TeamOne");
        testCommand.execute(parameters);

        Team team = teamRepository.getTeams().get("TeamOne");

        Assert.assertEquals("TeamOne", team.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throwException_when_passedMoreArguments() {
        List<String> parameters = new ArrayList<>();
        parameters.add("createnewteam");
        parameters.add("TeamOne");
        parameters.add("Too much");
        testCommand.execute(parameters);

        Team team = teamRepository.getTeams().get("TeamOne");

        Assert.assertEquals("TeamOne", team.getName());
    }

}
