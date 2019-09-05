package commands;

import commands.contracts.Command;
import core.contracts.MemberBoardRepository;
import core.contracts.TeamRepository;
import core.contracts.WorkitemRepository;
import models.common.Validator;
import models.contracts.Team;
import models.contracts.Workitem;

import java.util.List;

import static commands.CommandConstants.*;

public class AssignWorkitem implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 2;

    private WorkitemRepository workitemRepository;
    private MemberBoardRepository memberBoardRepository;
    private TeamRepository teamRepository;
    private static Workitem workitem;

    public AssignWorkitem(WorkitemRepository workitemRepository,
                          MemberBoardRepository memberBoardRepository,
                          TeamRepository teamRepository) {
        this.workitemRepository = workitemRepository;
        this.memberBoardRepository = memberBoardRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(
                parameters.size(),
                CORRECT_NUMBER_OF_ARGUMENTS);

        long workitemId = Long.parseLong(parameters.get(0));
        String memberName = parameters.get(1);

        return assignWorkitem(workitemId, memberName);
    }

    private String assignWorkitem(long workitemId, String memberName) {

        if (!(workitemRepository.getWorkitems().containsKey(workitemId))) {
            return String.format(WORKITEM_DOES_NOT_EXIST_ERROR_MESSAGE, workitemId);
        }

        if (!memberBoardRepository.getMembers().containsKey(memberName)) {
            return String.format(MEMBER_DOES_NOT_EXIST_ERROR_MESSAGE, memberName);
        }

        if (workitemRepository.getBugs().containsKey(workitemId)) {
            workitem =  workitemRepository.getBugs().get(workitemId);
            workitemRepository.getBugs().get(workitemId).setAssignee(
                    memberBoardRepository.getMembers().get(memberName));

        } else if (workitemRepository.getStories().containsKey(workitemId)) {
            workitemRepository.getStories().get(workitemId).setAssignee(
                    memberBoardRepository.getMembers().get(memberName));
        } else {
            return CANNOT_ASSIGN_MEMBER_TO_FEEDBACK_MESSAGE;
        }

        teamRepository.getTeams()
                .values()
                .stream()
                .filter(team -> team.getMembers().containsKey(memberName))
                .map(Team::getBoards)
                .filter(board -> board.containsValue(workitem))
                .forEach(board -> workitem.addActivity(String.format(
                        SUCCESSFULLY_ASSIGNED_MEMBER_TO_WORKITEM_MESSAGE,
                        memberName,
                        workitemId)));

        memberBoardRepository.getMembers().get(memberName).addActivity(
                String.format(
                        SUCCESSFULLY_ASSIGNED_MEMBER_TO_WORKITEM_MESSAGE,
                        memberName,
                        workitemId
                ));

        workitemRepository.getWorkitems().get(workitemId).addActivity(
                String.format(
                        SUCCESSFULLY_ASSIGNED_MEMBER_TO_WORKITEM_MESSAGE,
                        memberName,
                        workitemId
                ));

        return String.format(SUCCESSFULLY_ASSIGNED_MEMBER_TO_WORKITEM_MESSAGE,
                memberName,
                workitemId);
    }
}
