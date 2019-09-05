package commands;
import commands.contracts.Command;
import core.contracts.TeamRepository;


import java.util.List;

import static commands.CommandConstants.INVALID_NUMBER_OF_ARGUMENTS;
import static commands.CommandConstants.NO_TEAMS_ERROR_MESSAGE;

public class ShowAllTeams implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 0;

    private TeamRepository teamRepository;

    public ShowAllTeams(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        if(parameters.size() != CORRECT_NUMBER_OF_ARGUMENTS){
            throw new IllegalArgumentException(INVALID_NUMBER_OF_ARGUMENTS);
        }

        if(teamRepository.getTeams().isEmpty()){
            return NO_TEAMS_ERROR_MESSAGE;
        }

        StringBuilder sb = new StringBuilder(String.format("Teams are:%n"));
        teamRepository.getTeams()
                .values()
                .forEach(team -> sb.append(team.getName()).append(System.lineSeparator()));

        return sb.toString();
    }
}
