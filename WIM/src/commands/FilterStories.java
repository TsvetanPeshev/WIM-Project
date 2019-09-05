package commands;

import commands.contracts.Command;
import core.contracts.WorkitemRepository;
import models.common.Validator;

import java.util.List;
import java.util.stream.Collectors;

import static commands.CommandConstants.THERE_ARE_NO_STORIES_ERROR_MESSAGE;

public class FilterStories implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 0;

    private WorkitemRepository workitemRepository;

    public FilterStories(WorkitemRepository workitemRepository){
        this.workitemRepository = workitemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(
                parameters.size(),
                CORRECT_NUMBER_OF_ARGUMENTS);

        return filterStories();
    }

    private String filterStories(){

        if(workitemRepository.getStories().isEmpty()){
            return THERE_ARE_NO_STORIES_ERROR_MESSAGE;
        }


        return workitemRepository.getStories()
                .values()
                .stream()
                .map(story -> story.toString(workitemRepository.getWorkitemID(story)))
                .collect(Collectors.joining("\n"));
    }
}
