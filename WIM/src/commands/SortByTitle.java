package commands;


import commands.contracts.Command;
import core.contracts.TeamRepository;
import core.contracts.WorkitemRepository;
import models.common.Validator;
import models.contracts.Workitem;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import static commands.CommandConstants.NO_TEAMS_ERROR_MESSAGE;

public class SortByTitle implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 0;
    private final WorkitemRepository workitemRepository;
    private final TeamRepository teamRepository;

    public SortByTitle(WorkitemRepository workitemRepository, TeamRepository teamRepository) {
        this.workitemRepository = workitemRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(parameters.size(), CORRECT_NUMBER_OF_ARGUMENTS);

        if (teamRepository.getTeams().isEmpty()) {
            return NO_TEAMS_ERROR_MESSAGE;
        }

       return workitemRepository.getWorkitems()
                .values()
                .stream()
                .sorted(Comparator.comparing(Workitem::getTitle))
                .map(w -> w.toString(workitemRepository.getWorkitemID(w)))
                .collect(Collectors.joining("\n"));
    }
}
