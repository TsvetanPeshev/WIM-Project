package commands;

import commands.contracts.Command;
import core.contracts.TeamFactory;
import core.contracts.TeamRepository;
import models.common.Validator;
import models.contracts.Team;

import java.util.List;

import static commands.CommandConstants.*;

public class CreateNewTeam implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 1;

    private final TeamRepository teamRepository;
    private final TeamFactory teamFactory;

    public CreateNewTeam (TeamFactory teamFactory, TeamRepository teamRepository){
        this.teamRepository = teamRepository;
        this.teamFactory = teamFactory;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(
                parameters.size(),
                CORRECT_NUMBER_OF_ARGUMENTS);


        String teamName = parameters.get(0);
        return createTeam(teamName);
    }

    private String createTeam(String name){
        if(teamRepository.getTeams().containsKey(name)){
            return String.format(TEAM_EXISTS_ERROR_MESSAGE, name);
        }

        Team team = teamFactory.createTeam(name);
                teamRepository.addTeam(name, team);
                team.addActivity(String.format(TEAM_CREATED_SUCCESS_MESSAGE, name));
        return String.format(TEAM_CREATED_SUCCESS_MESSAGE, name);
    }
}
