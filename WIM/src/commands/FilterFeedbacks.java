package commands;

import commands.contracts.Command;
import core.contracts.WorkitemRepository;
import models.common.Validator;

import java.util.List;
import java.util.stream.Collectors;

import static commands.CommandConstants.THERE_ARE_NO_FEEDBACKS_ERROR_MESSAGE;

public class FilterFeedbacks implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 0;

    private WorkitemRepository workitemRepository;

    public FilterFeedbacks(WorkitemRepository workitemRepository){
        this.workitemRepository = workitemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(
                parameters.size(),
                CORRECT_NUMBER_OF_ARGUMENTS);

        return filterFeedback();
    }

    private String filterFeedback(){
        if(workitemRepository.getFeedbacks().isEmpty()){
            return THERE_ARE_NO_FEEDBACKS_ERROR_MESSAGE;
        }

       return workitemRepository.getFeedbacks()
                .values()
                .stream()
                .map(f -> f.toString(workitemRepository.getWorkitemID(f)))
                .collect(Collectors.joining("\n"));
    }
}
