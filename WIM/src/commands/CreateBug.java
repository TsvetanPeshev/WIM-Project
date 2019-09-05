package commands;

import commands.contracts.Command;
import core.contracts.MemberBoardRepository;
import core.contracts.TeamRepository;
import core.contracts.WorkitemFactory;
import core.contracts.WorkitemRepository;
import models.common.Validator;
import models.common.WorkitemInfoRepository;
import models.contracts.Board;
import models.contracts.Bug;

import java.util.List;
import java.util.stream.Collectors;

import static commands.CommandConstants.*;

public class CreateBug implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 7;

    private final TeamRepository teamRepository;
    private final MemberBoardRepository memberBoardRepository;
    private final WorkitemRepository workitemRepository;
    private final WorkitemFactory workitemFactory;
    private long bugId;

    public CreateBug(TeamRepository teamRepository,
                     MemberBoardRepository memberBoardRepository,
                     WorkitemRepository workitemRepository,
                     WorkitemFactory workitemFactory) {
        this.teamRepository = teamRepository;
        this.memberBoardRepository = memberBoardRepository;
        this.workitemRepository = workitemRepository;
        this.workitemFactory = workitemFactory;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(parameters.size(), CORRECT_NUMBER_OF_ARGUMENTS);

        String teamName = parameters.get(0);
        String boardName = parameters.get(1);
        String bugTitle = parameters.get(2);
        String bugDescription = parameters.get(3);
        String bugPriority = parameters.get(4);
        String bugSeverity = parameters.get(5);
        String bugStatus = parameters.get(6);

        return createBugInABoard(teamName, boardName, bugTitle,
                bugDescription, bugPriority, bugSeverity, bugStatus);
    }

    private String createBugInABoard(String teamName, String boardName, String bugTitle,
                                     String bugDescription, String bugPriority,
                                     String bugSeverity, String bugStatus
    ) {

        if (!teamRepository.getTeams().containsKey(teamName)) {
            return String.format(TEAM_DOES_NOT_EXIST_ERROR_MESSAGE, teamName);
        }

        if (!memberBoardRepository.getBoards().containsKey(boardName)) {
            return String.format(BOARD_DOES_NOT_EXIST_ERROR_MESSAGE, boardName);
        }

        Board board = memberBoardRepository.getBoards().get(boardName);

        bugId = workitemRepository.getIdManager().generateId(bugTitle);

        return teamRepository.getTeams().get(teamName).getBoards().entrySet()
                .stream()
                .filter(b -> b.getKey().equals(boardName))
                .map(b -> {
                    Bug bug = workitemFactory.createBug(
                            bugTitle, bugDescription, bugPriority, bugSeverity, bugStatus);
                    board.addWorkitem(bugId, bug);
                    bug.addActivity(String.format(
                            BUG_SUCCESSFULLY_CREATED_MESSAGE, bugTitle, bugId));
                    board.addActivity(String.format(
                            BUG_SUCCESSFULLY_CREATED_MESSAGE, bugTitle, bugId));
                    teamRepository.getTeams().get(teamName).addActivity(String.format(
                            BUG_SUCCESSFULLY_CREATED_MESSAGE, bugTitle, bugId));
                    WorkitemInfoRepository.addInfoOnIDItem(bugId, "Bug");
                    WorkitemInfoRepository.addIDTitleInfo(bugTitle, bugId);
                    workitemRepository.addBug(bug, bugId);

                   return String.format(BUG_SUCCESSFULLY_CREATED_MESSAGE, bugTitle, bugId);
                })
                .collect(Collectors.joining());
    }
}
