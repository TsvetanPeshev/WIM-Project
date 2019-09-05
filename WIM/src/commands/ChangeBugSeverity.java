package commands;

import commands.contracts.Command;
import core.contracts.TeamRepository;
import core.contracts.WorkitemRepository;
import models.common.Validator;
import models.contracts.Bug;
import models.enums.SeverityType;

import java.util.List;
import java.util.Objects;

import static commands.CommandConstants.*;

public class ChangeBugSeverity implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 2;

    private final WorkitemRepository workitemRepository;
    private final TeamRepository teamRepository;

    public ChangeBugSeverity(WorkitemRepository workitemRepository, TeamRepository teamRepository) {
        this.workitemRepository = workitemRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(
                parameters.size(),
                CORRECT_NUMBER_OF_ARGUMENTS);

        long workitemId = Long.parseLong(parameters.get(0));
        SeverityType bugSeverity = SeverityType.valueOf(parameters.get(1).toUpperCase());

        return changeSeverity(workitemId, bugSeverity);
    }

    private String changeSeverity(long workitemId, SeverityType bugSeverity) {

        if (!(workitemRepository.getWorkitems().containsKey(workitemId))) {
            return String.format(WORKITEM_DOES_NOT_EXIST_ERROR_MESSAGE, workitemId);
        }

        if (workitemRepository.getBugs().containsKey(workitemId)) {
            ((Bug) workitemRepository.getWorkitems().get(workitemId)).changeSeverity(bugSeverity);
        } else {
            return String.format(WORKITEM_IS_NOT_A_BUG_ERROR_MESSAGE, workitemId);
        }

        workitemRepository.getWorkitems().get(workitemId).addActivity(
                String.format(SEVERITY_SUCCESSFULLY_CHANGED_MESSAGE, workitemId, bugSeverity));

        if (!Objects.isNull((((Bug) workitemRepository.getWorkitems().get(workitemId)).getAssignee()))) {
            ((Bug) workitemRepository
                    .getWorkitems()
                    .get(workitemId))
                    .getAssignee()
                    .addActivity(String.format(
                            SEVERITY_SUCCESSFULLY_CHANGED_MESSAGE, workitemId, bugSeverity)
                    );
        }

        teamRepository.getTeams()
                .values()
                .forEach(team -> team.getBoards()
                        .values()
                        .forEach(board -> workitemRepository
                                .getWorkitems()
                                .get(workitemId)
                                .addActivity(String.format(
                                        SEVERITY_SUCCESSFULLY_CHANGED_MESSAGE, workitemId, bugSeverity))));


        return String.format(SEVERITY_SUCCESSFULLY_CHANGED_MESSAGE, workitemId, bugSeverity);
    }
}
