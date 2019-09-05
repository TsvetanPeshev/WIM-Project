package commands;

import commands.contracts.Command;
import core.contracts.MemberBoardRepository;
import core.contracts.WorkitemRepository;
import models.common.Validator;
import models.contracts.Board;
import models.contracts.Member;

import java.util.List;
import java.util.Objects;

import static commands.CommandConstants.*;

public class UnassignWorkitem implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 1;

    private WorkitemRepository workitemRepository;
    private MemberBoardRepository memberBoardRepository;

    public UnassignWorkitem(WorkitemRepository workitemRepository, MemberBoardRepository memberBoardRepository) {
        this.workitemRepository = workitemRepository;
        this.memberBoardRepository = memberBoardRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(
                parameters.size(),
                CORRECT_NUMBER_OF_ARGUMENTS);

        long workitemId = Long.parseLong(parameters.get(0));

        return unassignWorkitem(workitemId);
    }

    private String unassignWorkitem(long workitemId) {
        Member assignee;
        String typeOfWorkitem;

        if (!(workitemRepository.getWorkitems().containsKey(workitemId))) {
            return String.format(WORKITEM_DOES_NOT_EXIST_ERROR_MESSAGE, workitemId);
        }

        if (workitemRepository.getBugs().containsKey(workitemId) &&
                !Objects.isNull(workitemRepository
                        .getBugs()
                        .get(workitemId)
                        .getAssignee())) {
            assignee = workitemRepository.getBugs().get(workitemId).getAssignee();
            typeOfWorkitem = "Bug";
        } else if (workitemRepository.getStories().containsKey(workitemId) &&
                !Objects.isNull(workitemRepository
                        .getStories()
                        .get(workitemId)
                        .getAssignee())) {
            assignee = workitemRepository.getStories().get(workitemId).getAssignee();
            typeOfWorkitem = " Story";
        } else {
            return String.format(
                    WORKITEM_DOES_NOT_ASSIGNED_MESSAGE,
                    workitemId);
        }

        if (typeOfWorkitem.equals("Bug")) {
            workitemRepository.getBugs().get(workitemId).setAssignee(null);
        } else {
            workitemRepository.getStories().get(workitemId).setAssignee(null);
        }

        assignee.addActivity(String.format(WORKITEM_SUCCESSFULLY_UNASSIGNED_MESSAGE, workitemId));

        workitemRepository.getWorkitems().get(workitemId).addActivity(
                String.format(WORKITEM_SUCCESSFULLY_UNASSIGNED_MESSAGE, workitemId));

        for (Board board : memberBoardRepository.getBoards().values()) {
            if (board.getName().contains(workitemRepository
                    .getWorkitems()
                    .get(workitemId)
                    .toString())) {
                board.addActivity(
                        String.format(
                                WORKITEM_SUCCESSFULLY_UNASSIGNED_MESSAGE,
                                workitemId));
                break;
            }
        }
        return String.format(WORKITEM_SUCCESSFULLY_UNASSIGNED_MESSAGE, workitemId);
    }
}
