package commands;

import commands.contracts.Command;
import core.contracts.TeamRepository;
import core.contracts.WorkitemRepository;
import models.common.Validator;
import models.contracts.BugStoryBase;
import models.enums.StatusType;

import java.util.List;
import java.util.Objects;

import static commands.CommandConstants.*;

public class ChangeStatus implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 2;

    private final WorkitemRepository workitemRepository;
    private final TeamRepository teamRepository;

    public ChangeStatus(WorkitemRepository workitemRepository, TeamRepository teamRepository) {
        this.workitemRepository = workitemRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(
                parameters.size(),
                CORRECT_NUMBER_OF_ARGUMENTS);

        long workitemId = Long.parseLong(parameters.get(0));
        StatusType status = StatusType.valueOf(parameters.get(1).toUpperCase());

        return changeStatus(workitemId, status);
    }

    private String changeStatus(long workitemId, StatusType status) {

        if (!(workitemRepository.getWorkitems().containsKey(workitemId))) {
            return String.format(WORKITEM_DOES_NOT_EXIST_ERROR_MESSAGE, workitemId);
        }

        workitemRepository.getWorkitems().get(workitemId).setStatus(status);

        workitemRepository.getWorkitems().get(workitemId).addActivity(
                String.format(STATUS_SUCCESSFULLY_CHANGED_MESSAGE, workitemId, status));

        if (!Objects.isNull((((BugStoryBase) workitemRepository.getWorkitems().get(workitemId)).getAssignee()))) {
            ((BugStoryBase) workitemRepository
                    .getWorkitems()
                    .get(workitemId))
                    .getAssignee()
                    .addActivity(String.format(STATUS_SUCCESSFULLY_CHANGED_MESSAGE, workitemId, status));
        }

        teamRepository.getTeams()
                .values()
                .forEach(team -> team.getBoards()
                        .values()
                        .forEach(board -> workitemRepository
                                .getWorkitems()
                                .get(workitemId)
                                .addActivity(String.format(
                                        STATUS_SUCCESSFULLY_CHANGED_MESSAGE,
                                        workitemId,
                                        status))));

        return String.format(STATUS_SUCCESSFULLY_CHANGED_MESSAGE, workitemId, status);
    }
}
