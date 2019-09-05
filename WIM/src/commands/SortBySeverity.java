package commands;

import commands.contracts.Command;
import core.contracts.TeamRepository;
import core.contracts.WorkitemRepository;
import models.common.Validator;
import models.contracts.Bug;
import models.contracts.Workitem;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static commands.CommandConstants.NO_TEAMS_ERROR_MESSAGE;

public class SortBySeverity implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 0;
    private final WorkitemRepository workitemRepository;
    private final TeamRepository teamRepository;

    public SortBySeverity(WorkitemRepository workitemRepository, TeamRepository teamRepository) {
        this.workitemRepository = workitemRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(parameters.size(), CORRECT_NUMBER_OF_ARGUMENTS);

        if (teamRepository.getTeams().isEmpty()) {
            return NO_TEAMS_ERROR_MESSAGE;
        }

        StringBuilder sb = new StringBuilder(String.format("Sort criteria are:severity%n"));

        teamRepository
                .getTeams()
                .values()
                .stream()
                .sorted();

        Map<Long, Bug> mapBug = new LinkedHashMap<>();

        Comparator<? super Bug> comparator = new Comparator<Bug>() {
            @Override
            public int compare(Bug o1, Bug o2) {
                if (o1.getSeverity().equals(o2.getSeverity())) {
                    return 0;
                } else if ((o1.getSeverity().equals("CRITICAL")
                        && (o2.getSeverity().equals("MAJOR")
                        || o2.getSeverity().equals("MINOR")))
                        || (o1.getSeverity().equals("MAJOR")
                        && o2.getSeverity().equals("MINOR"))) {
                    return 1;
                } else
                    return -1;
            }
        };


        mapBug = workitemRepository
                .getBugs()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(comparator))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        mapBug
                .values()
                .stream()
                .forEach(workitem -> sb.append(workitem.getTitle())
                        .append(System.lineSeparator()));

        return sb.toString();
    }
}
