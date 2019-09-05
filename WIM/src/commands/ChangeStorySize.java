package commands;

import commands.contracts.Command;
import core.contracts.TeamRepository;
import core.contracts.WorkitemRepository;
import models.common.Validator;
import models.contracts.Story;
import models.enums.Size;

import java.util.List;

import static commands.CommandConstants.*;

public class ChangeStorySize implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 2;

    private TeamRepository teamRepository;
    private WorkitemRepository workitemRepository;

    public ChangeStorySize(WorkitemRepository workitemRepository, TeamRepository teamRepository) {
        this.workitemRepository = workitemRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(parameters.size(), CORRECT_NUMBER_OF_ARGUMENTS);
        int workitemId = Integer.parseInt(parameters.get(0));
        Size storySize = Size.valueOf(parameters.get(1).toUpperCase());

        return changeStorySize(workitemId, storySize);
    }


    public String changeStorySize(long workitemId, Size storySize) {

        if (!(workitemRepository.getWorkitems().containsKey(workitemId))) {
            return String.format(WORKITEM_DOES_NOT_EXIST_ERROR_MESSAGE, workitemId);
        }

        if (workitemRepository.getStories().containsKey(workitemId)) {

            ((Story) (workitemRepository.getWorkitems().get(workitemId))).setStorySize(storySize);

        } else {
            return String.format(WORKITEM_IS_NOT_A_STORY_ERROR_MESSAGE, workitemId);
        }

        workitemRepository.getWorkitems().get(workitemId).addActivity(
                String.format(STORY_SIZE_SUCCESSFULLY_CHANGED_MESSAGE, workitemId, storySize));


        teamRepository.getTeams()
                .values()
                .forEach(team -> team.getBoards()
                        .values()
                        .forEach(board -> workitemRepository
                                .getWorkitems()
                                .get(workitemId)
                                .addActivity(String.format(
                                        STORY_SIZE_SUCCESSFULLY_CHANGED_MESSAGE, workitemId, storySize))));


        return String.format(STORY_SIZE_SUCCESSFULLY_CHANGED_MESSAGE, workitemId, storySize);
    }


}
