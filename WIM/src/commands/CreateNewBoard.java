package commands;

import commands.contracts.Command;
import core.contracts.MemberBoardFactory;
import core.contracts.MemberBoardRepository;
import core.contracts.TeamRepository;
import models.common.Validator;
import models.contracts.Board;
import models.contracts.Team;

import java.util.List;

import static commands.CommandConstants.*;

public class CreateNewBoard implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 2;

    private final MemberBoardRepository memberBoardRepository;
    private final MemberBoardFactory memberBoardFactory;
    private final TeamRepository teamRepository;

        public  CreateNewBoard(MemberBoardFactory memberBoardFactory,
                               MemberBoardRepository memberBoardRepository,
                               TeamRepository teamRepository) {
        this.memberBoardRepository = memberBoardRepository;
        this.memberBoardFactory = memberBoardFactory;
        this.teamRepository = teamRepository;
        }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(
                parameters.size(),
                CORRECT_NUMBER_OF_ARGUMENTS);

        String boardName = parameters.get(0);
        String teamName = parameters.get(1);
        return createBoard(boardName, teamName);
    }
    private String createBoard(String boardName, String teamName) {
        if(!teamRepository.getTeams().containsKey(teamName)){
            return String.format(TEAM_DOES_NOT_EXIST_ERROR_MESSAGE, teamName);
        }

        if (memberBoardRepository.getBoards().containsKey(boardName)) {
            return String.format(BOARD_EXISTS_ERROR_MESSAGE, boardName);
        }

        Board board = memberBoardFactory.createBoard(boardName);
        memberBoardRepository.addBoard(boardName, board);
        board.addActivity(String.format(CREATE_BOARD_IN_A_TEAM_MESSAGE, boardName, teamName));
        Team team = teamRepository.getTeams().get(teamName);
        team.addBoard(board);
        team.addActivity(String.format(CREATE_BOARD_IN_A_TEAM_MESSAGE, boardName, teamName));

        return String.format(CREATE_BOARD_IN_A_TEAM_MESSAGE, boardName, teamName);
    }
}
