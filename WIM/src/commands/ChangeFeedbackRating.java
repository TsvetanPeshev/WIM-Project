package commands;

import commands.contracts.Command;
import core.contracts.TeamRepository;
import core.contracts.WorkitemRepository;
import models.common.Validator;
import models.contracts.Feedback;

import java.util.List;
import java.util.Objects;

import static commands.CommandConstants.*;

public class ChangeFeedbackRating implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 2;

    private TeamRepository teamRepository;
    private WorkitemRepository workitemRepository;

    public ChangeFeedbackRating(WorkitemRepository workitemRepository, TeamRepository teamRepository) {
        this.workitemRepository = workitemRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(parameters.size(), CORRECT_NUMBER_OF_ARGUMENTS);

        long workitemId = Integer.parseInt(parameters.get(0));
        int newInputRating = Integer.parseInt(parameters.get(1));

        return changeFeedbackRating(workitemId, newInputRating);
    }

    public String changeFeedbackRating(long workitemID, int newInputRating) {

        if (!(workitemRepository.getWorkitems().containsKey(workitemID))) {
            return String.format(WORKITEM_DOES_NOT_EXIST_ERROR_MESSAGE, workitemID);
        }

        if (workitemRepository.getFeedbacks().containsKey(workitemID)) {
            ((Feedback) workitemRepository.getWorkitems().get(workitemID)).setRating(newInputRating);
        } else {
            return String.format(WORKITEM_IS_NOT_A_FEEDBACK_ERROR_MESSAGE, workitemID);
        }

        workitemRepository.getWorkitems().get(workitemID).addActivity(
                String.format(FEEDBACK_RATING_VALUE_CHANGED_MESSAGE, workitemID, newInputRating));

        if (!Objects.isNull((( workitemRepository.getWorkitems().get(workitemID))))) {
            ( workitemRepository.getWorkitems().get(workitemID))
                    .addActivity(String.format(
                            FEEDBACK_RATING_VALUE_CHANGED_MESSAGE, workitemID, newInputRating)
                    );
        }

        teamRepository.getTeams()
                .values()
                .forEach(team -> team.getBoards()
                        .values()
                        .forEach(board -> workitemRepository
                                .getWorkitems()
                                .get(workitemID)
                                .addActivity(String.format(
                                        FEEDBACK_RATING_VALUE_CHANGED_MESSAGE, workitemID, newInputRating))));


        return String.format(FEEDBACK_RATING_VALUE_CHANGED_MESSAGE, workitemID, newInputRating);
    }


}
