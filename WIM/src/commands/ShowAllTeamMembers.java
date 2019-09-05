package commands;

import commands.contracts.Command;
import core.contracts.MemberBoardRepository;
import core.contracts.TeamRepository;
import models.common.Validator;
import models.contracts.Team;


import java.util.List;
import java.util.stream.Collectors;

import static commands.CommandConstants.TEAM_DOES_NOT_EXIST_ERROR_MESSAGE;

public class ShowAllTeamMembers implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 1;

    private TeamRepository teamRepository;
    Team team;

    public ShowAllTeamMembers(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(
                parameters.size(),
                CORRECT_NUMBER_OF_ARGUMENTS);

        String teamName = parameters.get(0);

        if (!teamRepository.getTeams().containsKey(teamName)) {
            return String.format(TEAM_DOES_NOT_EXIST_ERROR_MESSAGE, teamName);
        }

        team = teamRepository.getTeams().get(teamName);

        StringBuilder sb = new StringBuilder(String.format("Team %s members:%n", teamName));
        teamRepository
                .getTeams()
                .keySet()
                .stream()
                .filter(team -> team.compareTo(teamName) == 0)
                .forEach(name -> sb.append(team.getMembers()
                        .keySet()
                        .stream()
                        .collect(Collectors.joining(String.format("%n"))))
                        .append(System.lineSeparator()));

        return sb.toString();
    }
}