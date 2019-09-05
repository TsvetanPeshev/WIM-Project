package commands;

import commands.contracts.Command;
import core.contracts.MemberBoardRepository;
import core.contracts.WorkitemRepository;
import models.common.Validator;
import models.contracts.BugStoryBase;
import models.enums.StatusType;

import java.util.List;
import java.util.stream.Collectors;

import static commands.CommandConstants.MEMBER_DOES_NOT_EXIST_ERROR_MESSAGE;
import static commands.CommandConstants.THERE_ARE_NO_WORKITEMS_ERROR_MESSAGE;

public class FilterByStatusAndAssignee implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 2;

    private WorkitemRepository workitemRepository;
    private MemberBoardRepository memberBoardRepository;

    public FilterByStatusAndAssignee(WorkitemRepository workitemRepository,
                                     MemberBoardRepository memberBoardRepository) {
        this.workitemRepository = workitemRepository;
        this.memberBoardRepository = memberBoardRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(
                parameters.size(),
                CORRECT_NUMBER_OF_ARGUMENTS);

        StatusType statusType = StatusType.valueOf(parameters.get(0).toUpperCase());
        String assignee = parameters.get(1);

        return filterByStatusAndAssignee(statusType, assignee);
    }

    private String filterByStatusAndAssignee(StatusType statusType, String assignee) {
        if (workitemRepository.getWorkitems().isEmpty()) {
            return THERE_ARE_NO_WORKITEMS_ERROR_MESSAGE;
        }

        if (!memberBoardRepository.getMembers().containsKey(assignee)) {
            return String.format(MEMBER_DOES_NOT_EXIST_ERROR_MESSAGE, assignee);
        }

        return workitemRepository.getWorkitems()
                .values()
                .stream()
                .filter(w -> workitemRepository.getBugs().containsValue(w)
                        || workitemRepository.getStories().containsValue(w))
                .map(w -> (BugStoryBase)w)
                .filter(w -> w.getAssignee() != null)
                .filter(w -> w.getAssignee().getName().equals(assignee))
                .filter(w -> w.getStatus().equals(statusType))
                .map(w -> w.toString(workitemRepository.getWorkitemID(w)))
                .collect(Collectors.joining("\n"));
    }
}
