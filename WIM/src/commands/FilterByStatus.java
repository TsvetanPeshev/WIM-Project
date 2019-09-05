package commands;

import commands.contracts.Command;
import core.contracts.WorkitemRepository;
import models.common.Validator;
import models.enums.StatusType;

import java.util.List;
import java.util.stream.Collectors;

import static commands.CommandConstants.THERE_ARE_NO_WORKITEMS_ERROR_MESSAGE;

public class FilterByStatus implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 1;

    private WorkitemRepository workitemRepository;

    public FilterByStatus(WorkitemRepository workitemRepository) {
        this.workitemRepository = workitemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(
                parameters.size(),
                CORRECT_NUMBER_OF_ARGUMENTS);

        StatusType statusType = StatusType.valueOf(parameters.get(0).toUpperCase());

        return filterByStatus(statusType);
    }

    private String filterByStatus(StatusType statusType) {
        if (workitemRepository.getWorkitems().isEmpty()) {
            return THERE_ARE_NO_WORKITEMS_ERROR_MESSAGE;
        }

        return workitemRepository.getWorkitems()
                .values()
                .stream()
                .filter(workitem -> workitem.getStatus().toString().toUpperCase().compareTo(statusType.toString().toUpperCase()) == 0)
                .map(w -> w.toString(workitemRepository.getWorkitemID(w)))
                .collect(Collectors.joining("\n"));
    }
}
