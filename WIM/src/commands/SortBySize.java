package commands;

import commands.contracts.Command;
import core.contracts.TeamRepository;
import core.contracts.WorkitemRepository;
import models.common.Validator;
import models.contracts.Story;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static commands.CommandConstants.NO_TEAMS_ERROR_MESSAGE;

public class SortBySize implements Command {

    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 0;
    private final WorkitemRepository workitemRepository;
    private final TeamRepository teamRepository;

    public SortBySize(WorkitemRepository workitemRepository, TeamRepository teamRepository) {
        this.workitemRepository = workitemRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(parameters.size(), CORRECT_NUMBER_OF_ARGUMENTS);

        if (teamRepository.getTeams().isEmpty()) {
            return NO_TEAMS_ERROR_MESSAGE;
        }

        StringBuilder sb = new StringBuilder(String.format("Sort criteria are:Size%n"));

        teamRepository
                .getTeams()
                .values()
                .stream()
                .sorted();

        Map<Long, Story> mapStory = new LinkedHashMap<>();

        Comparator<? super Story> comparator = new Comparator<Story>() {
            @Override
            public int compare(Story o1, Story o2) {
                if (o1.getStorySize().equals(o2.getStorySize()) || ((o1.getStorySize().equals("LARGE") && (o2.getStorySize().equals("MEDIUM")
                        || o2.getStorySize().equals("SMALL")))
                        || (o1.getStorySize().equals("MEDIUM")
                        && o2.getStorySize().equals("SMALL")))){
                    return 1;
                } else
                    return -1;
            }
        };


        mapStory = workitemRepository
                .getStories()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(comparator))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        mapStory
                .values()
                .stream()
                .forEach(workitem -> sb.append(workitem.getTitle())
                        .append(System.lineSeparator()));

        return sb.toString();
    }
}
