package commands;

import commands.contracts.Command;
import core.contracts.CommentsFactory;
import core.contracts.MemberBoardRepository;
import core.contracts.TeamRepository;
import core.contracts.WorkitemRepository;
import models.common.Validator;
import models.contracts.Comment;
import models.contracts.Team;

import java.util.List;

import static commands.CommandConstants.COMMENT_SUCCESSFULLY_ADDED_TO_WORKITEM_MESSAGE;
import static commands.CommandConstants.WORKITEM_DOES_NOT_EXIST_ERROR_MESSAGE;

public class AddCommentToWorkitem implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 3;

    private WorkitemRepository workitemRepository;
    private MemberBoardRepository memberBoardRepository;
    private TeamRepository teamRepository;
    private CommentsFactory commentsFactory;

    public AddCommentToWorkitem(WorkitemRepository workitemRepository,
                                MemberBoardRepository memberBoardRepository,
                                TeamRepository teamRepository,
                                CommentsFactory commentsFactory) {
        this.workitemRepository = workitemRepository;
        this.memberBoardRepository = memberBoardRepository;
        this.teamRepository = teamRepository;
        this.commentsFactory = commentsFactory;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(
                parameters.size(),
                CORRECT_NUMBER_OF_ARGUMENTS);

        long workitemId = Long.parseLong(parameters.get(0));
        String comment = parameters.get(1);
        String author = parameters.get(2);


        return addCommentToWorkitem(workitemId, comment, author);
    }

    private String addCommentToWorkitem(long workitemId, String comment, String author) {
        Team team;
        if (!workitemRepository.getWorkitems().containsKey(workitemId)) {
            return String.format(
                    WORKITEM_DOES_NOT_EXIST_ERROR_MESSAGE,
                    workitemId);
        }

        String board = memberBoardRepository
                .getBoards()
                .values()
                .stream()
                .filter(board1 -> board1.getName()
                        .contains(workitemRepository
                                .getWorkitems()
                                .get(workitemId).toString()))
                .toString();

        for (Team t : teamRepository.getTeams().values()) {
            if (t.getBoards().containsKey(board) && t.getMembers().containsKey(author)) {
                team = t;
                break;
            }
        }

        Comment comment1 = commentsFactory.createComment(comment, memberBoardRepository.getMembers().get(author));

        workitemRepository.getWorkitems().get(workitemId).addComment(comment1);

        workitemRepository.getWorkitems().get(workitemId).addActivity(String.format(
                COMMENT_SUCCESSFULLY_ADDED_TO_WORKITEM_MESSAGE,
                workitemId));

        memberBoardRepository.getMembers().get(author).addActivity(
                String.format(
                        COMMENT_SUCCESSFULLY_ADDED_TO_WORKITEM_MESSAGE,
                        workitemId)
        );

        memberBoardRepository.getBoards().get(board)
                .addActivity(String.format(
                        COMMENT_SUCCESSFULLY_ADDED_TO_WORKITEM_MESSAGE,
                        workitemId));

        return String.format(COMMENT_SUCCESSFULLY_ADDED_TO_WORKITEM_MESSAGE, workitemId);
    }
}
