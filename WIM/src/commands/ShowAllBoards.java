package commands;

import commands.contracts.Command;
import core.contracts.TeamRepository;
import models.common.Validator;

import java.util.List;
import java.util.stream.Collectors;

import static commands.CommandConstants.*;

public class ShowAllBoards implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 1;

    private final TeamRepository teamRepository;

    public ShowAllBoards(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(
                parameters.size(),
                CORRECT_NUMBER_OF_ARGUMENTS);

        if (teamRepository.getTeams().isEmpty()) {
            return NO_TEAMS_ERROR_MESSAGE;
        }

        StringBuilder sb = new StringBuilder(String.format("All boards are:%n"));


        String teamName = parameters.get(0);
        sb.append(
                teamRepository.getTeams().get(teamName).getBoards()
                        .keySet()
                        .stream()
                        .collect(Collectors.joining(
                                String.format("%n"))));

        return sb.toString();
    }
}
