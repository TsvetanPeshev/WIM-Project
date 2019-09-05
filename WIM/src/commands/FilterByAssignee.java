package commands;

import commands.contracts.Command;
import core.contracts.MemberBoardRepository;
import core.contracts.WorkitemRepository;
import models.common.Validator;
import models.contracts.BugStoryBase;

import java.util.List;
import java.util.stream.Collectors;

import static commands.CommandConstants.MEMBER_DOES_NOT_EXIST_ERROR_MESSAGE;

public class
FilterByAssignee implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 1;

    private WorkitemRepository workitemRepository;
    private MemberBoardRepository memberBoardRepository;

    public FilterByAssignee(WorkitemRepository workitemRepository,
                            MemberBoardRepository memberBoardRepository) {
        this.workitemRepository = workitemRepository;
        this.memberBoardRepository = memberBoardRepository;
    }

    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(
                parameters.size(),
                CORRECT_NUMBER_OF_ARGUMENTS);

        String assignee = parameters.get(0);

        return filterByAssignee(assignee);
    }

    private String filterByAssignee(String assignee) {
        if (!memberBoardRepository.getMembers().containsKey(assignee)) {
            return String.format(MEMBER_DOES_NOT_EXIST_ERROR_MESSAGE, assignee);
        }

        String result = workitemRepository.getWorkitems()
                .values()
                .stream()
                .filter(w -> workitemRepository.getBugs().containsValue(w)
                        || workitemRepository.getStories().containsValue(w))
                .map(w -> (BugStoryBase)w)
                .filter(w -> w.getAssignee() != null)
                .filter(w -> w.getAssignee().getName().compareTo(assignee) == 0)
                .map(w -> w.toString(workitemRepository.getWorkitemID(w)))
                .collect(Collectors.joining("\n"));

        return result;
    }
}
