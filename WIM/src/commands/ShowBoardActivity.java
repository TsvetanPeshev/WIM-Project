package commands;

import commands.contracts.Command;
import core.contracts.TeamRepository;
import models.common.Validator;

import java.util.List;

import static commands.CommandConstants.*;

public class ShowBoardActivity implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 2;

    private final TeamRepository teamRepository;

    public ShowBoardActivity(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }


    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments
                (parameters.size(),
                CORRECT_NUMBER_OF_ARGUMENTS);

        String teamName = parameters.get(0);
        String boardName = parameters.get(1);

        if (teamRepository.getTeams().isEmpty()) {
            throw new IllegalArgumentException(NO_TEAMS_ERROR_MESSAGE);
        }

        if (!teamRepository.getTeams().containsKey(teamName)) {
            throw new IllegalArgumentException(String.format(TEAM_DOES_NOT_EXIST_ERROR_MESSAGE, teamName));
        }

        if(!teamRepository.getTeams().get(teamName).getBoards().containsKey(boardName)){
            throw new IllegalArgumentException(String.format(
                    BOARDS_NOT_EXIST_ERROR_MESSAGE,
                    boardName));
        }

        StringBuilder sb = new StringBuilder(String.format("Board %s activity:%n", boardName));
        teamRepository
                .getTeams()
                .get(teamName)
                .getBoards()
                .values()
                .stream()
                .filter(b -> b.getName().compareTo(boardName) == 0)
                .forEach(board -> sb
                        .append(board.showActivity(board.getName())));


        return sb.toString();
    }
}
