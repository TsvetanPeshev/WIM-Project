package commands;

import commands.contracts.Command;
import core.contracts.MemberBoardRepository;
import core.contracts.TeamRepository;
import core.contracts.WorkitemFactory;
import core.contracts.WorkitemRepository;
import models.common.Validator;
import models.common.WorkitemInfoRepository;
import models.contracts.Board;
import models.contracts.Story;

import java.util.List;
import java.util.stream.Collectors;

import static commands.CommandConstants.*;

public class CreateStory implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 7;

    private final TeamRepository teamRepository;
    private final MemberBoardRepository memberBoardRepository;
    private final WorkitemRepository workitemRepository;
    private final WorkitemFactory workitemFactory;
    private long storyId;

    public CreateStory(TeamRepository teamRepository,
                       MemberBoardRepository memberBoardRepository,
                       WorkitemRepository workitemRepository,
                       WorkitemFactory workitemFactory) {
        this.teamRepository = teamRepository;
        this.memberBoardRepository = memberBoardRepository;
        this.workitemRepository = workitemRepository;
        this.workitemFactory = workitemFactory;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(parameters.size(), CORRECT_NUMBER_OF_ARGUMENTS);

        String teamName = parameters.get(0);
        String boardName = parameters.get(1);
        String storyTitle = parameters.get(2);
        String storyDescription = parameters.get(3);
        String storyPriority = parameters.get(4);
        String storySize = parameters.get(5);
        String storyStatus = parameters.get(6);

        return createStoryInABoardCommand(teamName, boardName, storyTitle,
                storyDescription, storyPriority, storySize, storyStatus);
    }

    private String createStoryInABoardCommand(
            String teamName, String boardName,
            String storyTitle, String storyDescription,
            String storyPriority, String storySize,
            String storyStatus
    ) {

        if (!teamRepository.getTeams().containsKey(teamName)) {
            return String.format(TEAM_DOES_NOT_EXIST_ERROR_MESSAGE, teamName);
        }

        if (!memberBoardRepository.getBoards().containsKey(boardName)) {
            return String.format(BOARD_DOES_NOT_EXIST_ERROR_MESSAGE, boardName);
        }

        Board board = memberBoardRepository.getBoards().get(boardName);

        storyId = workitemRepository.getIdManager().generateId(storyTitle);

        return teamRepository.getTeams().get(teamName).getBoards().entrySet()
                .stream()
                .filter(b -> b.getKey().equals(boardName))
                .map(b -> {
                    Story story = workitemFactory.createStory(
                            storyTitle, storyDescription, storyPriority, storySize, storyStatus);
                    board.addWorkitem(storyId, story);
                    story.addActivity(
                            String.format(STORY_SUCCESSFULLY_CREATED_MESSAGE, storyTitle, storyId));
                    board.addActivity(
                            String.format(STORY_SUCCESSFULLY_CREATED_MESSAGE, storyTitle, storyId));
                    teamRepository.getTeams().get(teamName).addActivity(
                            String.format(STORY_SUCCESSFULLY_CREATED_MESSAGE, storyTitle, storyId));
                    WorkitemInfoRepository.addInfoOnIDItem(storyId, "Story");
                    WorkitemInfoRepository.addIDTitleInfo(storyTitle.toString(), storyId);
                    workitemRepository.addStory(story, storyId);

                    return String.format(STORY_SUCCESSFULLY_CREATED_MESSAGE, storyTitle, storyId);
                })
                .collect(Collectors.joining());
    }
}
