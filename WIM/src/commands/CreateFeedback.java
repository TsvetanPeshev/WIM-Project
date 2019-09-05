package commands;

import commands.contracts.Command;
import core.contracts.MemberBoardRepository;
import core.contracts.TeamRepository;
import core.contracts.WorkitemFactory;
import core.contracts.WorkitemRepository;
import models.common.Validator;
import models.common.WorkitemInfoRepository;
import models.contracts.Board;
import models.contracts.Feedback;

import java.util.List;
import java.util.stream.Collectors;

import static commands.CommandConstants.*;

public class CreateFeedback implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 6;

    private final TeamRepository teamRepository;
    private final MemberBoardRepository memberBoardRepository;
    private final WorkitemRepository workitemRepository;
    private final WorkitemFactory workitemFactory;
    private long feedbackId;

    public CreateFeedback(TeamRepository teamRepository,
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
        String feedbackTitle = parameters.get(2);
        String feedbackDescription = parameters.get(3);
        int feedbackRating = Integer.parseInt(parameters.get(4));
        String feedbackStatus = parameters.get(5);

        return CreateFeedbackInABoard(teamName, boardName,
                feedbackTitle, feedbackDescription,
                feedbackRating, feedbackStatus);
    }

    private String CreateFeedbackInABoard(String teamName, String boardName,
                                          String feedbackTitle, String feedbackDescription,
                                          int feedbackRating, String feedbackStatus) {

        if (!teamRepository.getTeams().containsKey(teamName)) {
            return String.format(TEAM_DOES_NOT_EXIST_ERROR_MESSAGE, teamName);
        }

        if (!memberBoardRepository.getBoards().containsKey(boardName)) {
            return String.format(BOARD_DOES_NOT_EXIST_ERROR_MESSAGE, boardName);
        }

        Board board = memberBoardRepository.getBoards().get(boardName);

        feedbackId = workitemRepository.getIdManager().generateId(feedbackTitle);

        return teamRepository.getTeams().get(teamName).getBoards().entrySet()
                .stream()
                .filter(b -> b.getKey().equals(boardName))
                .map(b -> {
                    Feedback feedback = workitemFactory.createFeedback(
                            feedbackTitle, feedbackDescription, feedbackRating, feedbackStatus);
                    board.addWorkitem(feedbackId, feedback);
                    feedback.addActivity(
                            String.format(FEEDBACK_SUCCESSFULLY_CREATED_MESSAGE,
                                    feedbackTitle,
                                    feedbackId));
                    board.addActivity(
                            String.format(FEEDBACK_SUCCESSFULLY_CREATED_MESSAGE,
                                    feedbackTitle,
                                    feedbackId));
                    teamRepository.getTeams().get(teamName).addActivity(
                            String.format(FEEDBACK_SUCCESSFULLY_CREATED_MESSAGE,
                                    feedbackTitle,
                                    feedbackId));
                    WorkitemInfoRepository.addInfoOnIDItem(feedbackId, "Feedback");
                    WorkitemInfoRepository.addIDTitleInfo(feedbackTitle.toString(), feedbackId);
                    workitemRepository.addFeedback(feedback, feedbackId);
                    return String.format(FEEDBACK_SUCCESSFULLY_CREATED_MESSAGE,
                            feedbackTitle,
                            feedbackId);
                })
                .collect(Collectors.joining());
    }
}
