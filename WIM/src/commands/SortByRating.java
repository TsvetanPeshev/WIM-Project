package commands;

import commands.contracts.Command;
import core.contracts.TeamRepository;
import core.contracts.WorkitemRepository;
import models.common.Validator;
import models.contracts.Feedback;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static commands.CommandConstants.NO_TEAMS_ERROR_MESSAGE;

public class SortByRating implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 0;
    private final WorkitemRepository workitemRepository;
    private final TeamRepository teamRepository;

    public SortByRating(WorkitemRepository workitemRepository, TeamRepository teamRepository) {
        this.workitemRepository = workitemRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(parameters.size(), CORRECT_NUMBER_OF_ARGUMENTS);

        if (teamRepository.getTeams().isEmpty()) {
            return NO_TEAMS_ERROR_MESSAGE;
        }

        StringBuilder sb = new StringBuilder(String.format("Sort criteria are:Rating%n"));

        teamRepository
                .getTeams()
                .values()
                .stream()
                .sorted();

        Map<Long, Feedback> mapFeedback = new LinkedHashMap<>();

        Comparator<? super Feedback> comparator = new Comparator<Feedback>() {
            @Override
            public int compare(Feedback o1, Feedback o2) {
                if ((o1.getRating()==o2.getRating()) || (o1.getRating()>o2.getRating())){
                    return 1;
                } else
                    return -1;
            }
        };


        mapFeedback = workitemRepository
                .getFeedbacks()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(comparator))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        mapFeedback
                .values()
                .stream()
                .forEach(workitem -> sb.append(workitem.getTitle())
                        .append(System.lineSeparator()));

        return sb.toString();
    }
}
