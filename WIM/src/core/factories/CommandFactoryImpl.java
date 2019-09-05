package core.factories;

import commands.*;
import commands.contracts.Command;
import commands.enums.CommandType;
import core.contracts.CommandFactory;

public class CommandFactoryImpl implements CommandFactory {
    private static final String INVALID_COMMAND = "Invalid command name: %s!";

    @Override
    public Command createCommand(String commandTypeAsString, Facility facility) {
        CommandType commandType = CommandType.valueOf(commandTypeAsString.toUpperCase());

        switch (commandType) {
            case CREATEMEMBER:
                return new CreateMember(facility.getMemberBoardFactory(),
                                        facility.getMemberBoardRepository());

            case CREATENEWTEAM:
                return new CreateNewTeam(facility.getTeamFactory(),
                                         facility.getTeamRepository());

            case SHOWALLPEOPLE:
                return new ShowAllMembers(facility.getMemberBoardRepository());

            case SHOWALLTEAMS:
                return new ShowAllTeams(facility.getTeamRepository());

            case ADDPERSONTOTEAM:
                return new AddPersonToTeam(facility.getTeamRepository(),
                                           facility.getMemberBoardRepository());

            case CREATEANEWBOARD:
                return new CreateNewBoard(facility.getMemberBoardFactory(),
                                          facility.getMemberBoardRepository(),
                                          facility.getTeamRepository());

            case SHOWPERSONSACTIVITY:
                return new ShowPersonsActivity(facility.getMemberBoardRepository());

            case SHOWALLTEAMMEMBERS:
                return new ShowAllTeamMembers(facility.getTeamRepository());

            case SHOWBOARDACTIVITY:
                return new ShowBoardActivity(facility.getTeamRepository());

            case SHOWALLBOARDS:
                return new ShowAllBoards(facility.getTeamRepository());

            case CREATEBUG:
                return new CreateBug(facility.getTeamRepository(),
                                                facility.getMemberBoardRepository(),
                                                facility.getWorkitemRepository(),
                                                facility.getWorkItemFactory());

            case CREATESTORY:
                return new CreateStory(facility.getTeamRepository(),
                                                  facility.getMemberBoardRepository(),
                                                  facility.getWorkitemRepository(),
                                                  facility.getWorkItemFactory());

            case CREATEFEEDBACK:
                return new CreateFeedback(facility.getTeamRepository(),
                                                     facility.getMemberBoardRepository(),
                                                     facility.getWorkitemRepository(),
                                                     facility.getWorkItemFactory());

            case CHANGEBUGSEVERITY:
                return new ChangeBugSeverity(facility.getWorkitemRepository(),
                        facility.getTeamRepository());

            case CHANGEPRIORITY:
                return new ChangePriority(facility.getTeamRepository(),
                        facility.getWorkitemRepository());

            case CHANGESTORYSIZE:
                return new ChangeStorySize(facility.getWorkitemRepository(),
                        facility.getTeamRepository());

            case CHANGESTATUS:
                return new ChangeStatus(facility.getWorkitemRepository(),
                        facility.getTeamRepository());

            case ASSIGNWORKITEM:
                return new AssignWorkitem(facility.getWorkitemRepository(),
                        facility.getMemberBoardRepository(),
                        facility.getTeamRepository());

            case UNASSIGNWORKITEM:
                return new UnassignWorkitem(facility.getWorkitemRepository(),
                        facility.getMemberBoardRepository());

            case CHANGEFEEDBACKRATING:
                return new ChangeFeedbackRating(facility.getWorkitemRepository(),
                        facility.getTeamRepository());

            case ADDCOMMENTTOWORKITEM:
                return new AddCommentToWorkitem(facility.getWorkitemRepository(),
                        facility.getMemberBoardRepository(),
                        facility.getTeamRepository(),
                        facility.getCommentsFactory());

            case LISTALL:
                return new ListAllWorkitems(facility.getWorkitemRepository());

            case FILTERBUGS:
                return new FilterBugs(facility.getWorkitemRepository());

            case FILTERSTORIES:
                return new FilterStories(facility.getWorkitemRepository());

            case FILTERFEEDBACK:
                return new FilterFeedbacks(facility.getWorkitemRepository());

            case FILTERBYSTATUS:
                return new FilterByStatus(facility.getWorkitemRepository());

            case FILTERBYASSIGNEE:
                return new FilterByAssignee(facility.getWorkitemRepository(),
                        facility.getMemberBoardRepository());

            case FILTERBYSTATUSANDASSUGNEE:
                return new FilterByStatusAndAssignee(facility.getWorkitemRepository(),
                        facility.getMemberBoardRepository());

            case SORTBYTITLE:
                return new SortByTitle(facility.getWorkitemRepository(),
                        facility.getTeamRepository());

            case SORTBYPRIORITY:
                return new SortByPriority(facility.getWorkitemRepository(),
                        facility.getTeamRepository());

            case SORTBYSEVERITY:
                return new SortBySeverity(facility.getWorkitemRepository(),
                        facility.getTeamRepository());

            case SORTBYSIZE:
                return new SortBySize(facility.getWorkitemRepository(),
                        facility.getTeamRepository());

            case SORTBYRATING:
                return new SortByRating(facility.getWorkitemRepository(),
                        facility.getTeamRepository());

                default:
                throw new IllegalArgumentException(String.format(INVALID_COMMAND,commandType));
        }
    }
}
