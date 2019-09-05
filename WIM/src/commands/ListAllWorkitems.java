package commands;

import commands.contracts.Command;
import core.contracts.WorkitemRepository;
import models.common.Validator;

import java.util.List;
import java.util.stream.Collectors;

import static commands.CommandConstants.THERE_ARE_NO_WORKITEMS_ERROR_MESSAGE;

public class ListAllWorkitems implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 0;

    private WorkitemRepository workitemRepository;

    public ListAllWorkitems(WorkitemRepository workitemRepository){
        this.workitemRepository = workitemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(
                parameters.size(),
                CORRECT_NUMBER_OF_ARGUMENTS);

        return listAllWorkitems();
    }

    private String listAllWorkitems(){

        if(workitemRepository.getWorkitems().isEmpty()){
            return THERE_ARE_NO_WORKITEMS_ERROR_MESSAGE;
        }

      return   workitemRepository.getWorkitems()
                .values()
                .stream()
                .map(w -> w.toString(workitemRepository.getWorkitemID(w)))
                .collect(Collectors.joining("\n"));
    }
}
