package commands;

import commands.contracts.Command;
import core.contracts.TeamRepository;
import core.contracts.WorkitemRepository;
import models.common.Validator;
import models.contracts.*;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static commands.CommandConstants.*;

public class SortByPriority implements Command {

    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 0;
    private final WorkitemRepository workitemRepository;
    private final TeamRepository teamRepository;

    public SortByPriority(WorkitemRepository workitemRepository, TeamRepository teamRepository) {
        this.workitemRepository = workitemRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(parameters.size(), CORRECT_NUMBER_OF_ARGUMENTS);

        if (teamRepository.getTeams().isEmpty()) {
            return NO_TEAMS_ERROR_MESSAGE;
        }

        if(workitemRepository.getFeedbacks().isEmpty()){
            return THERE_ARE_NO_FEEDBACKS_ERROR_MESSAGE;
        }

        StringBuilder sb = new StringBuilder(String.format("Sort criteria are:%nby priority:%n"));

        workitemRepository.getFeedbacks()
                .values()
                .stream()
                .sorted(Comparator.comparing(Feedback::getRating))
                .forEach(feedback -> sb.append(feedback.getRating()).append(System.lineSeparator()));




//        teamRepository
//                .getTeams()
//                .values()
//                .stream()
//                .sorted();
//
//        Map<Long, Workitem> mapBug;
//
//        Comparator<? super Bug> comparator = new Comparator<Bug>() {
//            @Override
//            public int compare(Bug o1, Bug o2) {
//                if (o1.getPriorityType().equals(o2.getPriorityType())) {
//                    return 0;
//                } else if ((o1.getPriorityType().equals("HIGH")
//                        && (o2.getPriorityType().equals("MEDIUM")
//                        || o2.getPriorityType().equals("LOW")))
//                        || (o1.getPriorityType().equals("MEDIUM")
//                        && o2.getPriorityType().equals("LOW"))) {
//                    return 1;
//                } else
//                    return -1;
//            }
//        };
//
//        Comparator<? super Story> comparator2 = new Comparator<Story>() {
//            @Override
//            public int compare(Story o1, Story o2) {
//                if (o1.getPriorityType().equals(o2.getPriorityType())) {
//                    return 0;
//                } else if ((o1.getPriorityType().equals("HIGH")
//                        && (o2.getPriorityType().equals("MEDIUM")
//                        || o2.getPriorityType().equals("LOW")))
//                        || (o1.getPriorityType().equals("MEDIUM")
//                        && o2.getPriorityType().equals("LOW"))) {
//                    return 1;
//                } else
//                    return -1;
//            }
//        };
//        mapBug = workitemRepository
//                .getBugs()
//                .entrySet()
//                .stream()
//                .sorted(Map.Entry.comparingByValue(comparator))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
//                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
//
//
//        Map<Long, Workitem> mapStory = new LinkedHashMap<>();
//
//        mapStory = workitemRepository
//                .getStories()
//                .entrySet()
//                .stream()
//                .sorted(Map.Entry.comparingByValue(comparator2))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
//                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
//
//        mapBug
//                .values()
//                .stream()
//                .forEach(workitem -> sb.append(workitem.getTitle())
//                        .append(System.lineSeparator()));
//
//        mapStory
//                .values()
//                .stream()
//                .forEach(workitem -> sb.append(workitem.getTitle())
//                        .append(System.lineSeparator()));

        return sb.toString();
    }
}
