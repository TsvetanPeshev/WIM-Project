package commands;

import commands.contracts.Command;
import core.contracts.TeamRepository;
import core.contracts.WorkitemRepository;
import models.BugImpl;
import models.BugStoryBaseImpl;
import models.common.Validator;
import models.contracts.*;
import models.enums.PriorityType;
import models.enums.SeverityType;
import models.enums.StatusType;

import java.util.List;
import java.util.Objects;


import static commands.CommandConstants.*;

public class ChangePriority implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 2;

    private final TeamRepository teamRepository;
    private final WorkitemRepository workitemRepository;

    public ChangePriority(TeamRepository teamRepository, WorkitemRepository workitemRepository) {
        this.teamRepository = teamRepository;
        this.workitemRepository = workitemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(parameters.size(), CORRECT_NUMBER_OF_ARGUMENTS);

        long workitemId = Long.parseLong(parameters.get(0));
        PriorityType priority = PriorityType.valueOf(parameters.get(1).toUpperCase());
        return changePriority(workitemId, priority);
    }

    private String changePriority(long workitemId, PriorityType priority) {

        if (!(workitemRepository.getWorkitems().containsKey(workitemId))) {
            return String.format(WORKITEM_DOES_NOT_EXIST_ERROR_MESSAGE, workitemId);
        }

        if (workitemRepository.getBugs().containsKey(workitemId)) {
            ((Bug) workitemRepository.getWorkitems().get(workitemId)).changePriority(priority);
        } else if (workitemRepository.getStories().containsKey(workitemId)) {
            ((Story) workitemRepository.getWorkitems().get(workitemId)).changePriority(priority);
        } else {
            return String.format(NO_BUG_OR_STORY_WITH_SUCH_ID_MESSAGE, workitemId);
        }

        workitemRepository.getWorkitems().get(workitemId).addActivity(
                String.format(PRIORITY_SUCCESSFULLY_CHANGED_MESSAGE, workitemId, priority));

        if (!Objects.isNull((((BugStoryBase) workitemRepository.getWorkitems().get(workitemId)).getAssignee()))) {
            ((BugStoryBase) workitemRepository
                    .getWorkitems()
                    .get(workitemId))
                    .getAssignee()
                    .addActivity(String.format(
                            PRIORITY_SUCCESSFULLY_CHANGED_MESSAGE, workitemId, priority));
        };

        teamRepository.getTeams()
                .values()
                .forEach(team -> team.getBoards()
                        .values()
                        .forEach(board -> workitemRepository
                                .getWorkitems()
                                .get(workitemId)
                                .addActivity(String.format(
                                        PRIORITY_SUCCESSFULLY_CHANGED_MESSAGE, workitemId, priority))));


        return String.format(PRIORITY_SUCCESSFULLY_CHANGED_MESSAGE, workitemId, priority);
    }


}
