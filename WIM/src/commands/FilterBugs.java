package commands;

import commands.contracts.Command;
import core.contracts.WorkitemRepository;
import models.common.Validator;

import java.util.List;
import java.util.stream.Collectors;

import static commands.CommandConstants.THERE_ARE_NO_BUGS_ERROR_MESSAGE;

public class FilterBugs implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 0;

    private WorkitemRepository workitemRepository;

    public FilterBugs(WorkitemRepository workitemRepository) {
        this.workitemRepository = workitemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(
                parameters.size(),
                CORRECT_NUMBER_OF_ARGUMENTS);

        return filterBugs();
    }

    private String filterBugs() {

        if (workitemRepository.getBugs().isEmpty()) {
            return THERE_ARE_NO_BUGS_ERROR_MESSAGE;
        }

        return workitemRepository.getBugs()
                .values()
                .stream()
                .map(bug -> bug.toString(workitemRepository.getWorkitemID(bug)))
                .collect(Collectors.joining("\n"));
    }
}
