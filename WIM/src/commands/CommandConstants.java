package commands;

public class CommandConstants {

    // Success messages
    static final String MEMBER_CREATED_SUCCESS_MESSAGE = "Member %s was created";
    static final String CREATE_BOARD_IN_A_TEAM_MESSAGE = "Board %s in a team %s was created";
    static final String TEAM_CREATED_SUCCESS_MESSAGE = "Team %s was created";
    static final String MEMBER_ADDED_TO_TEAM_SUCCESS_MESSAGE = "Member %s was added to team %s";
    static final String BUG_SUCCESSFULLY_CREATED_MESSAGE = "Bug %s with ID %d successfully created";
    static final String STORY_SUCCESSFULLY_CREATED_MESSAGE = "Story %s with ID %d successfully created";
    static final String FEEDBACK_SUCCESSFULLY_CREATED_MESSAGE = "Feedback %s with ID %d successfully created";
    static final String PRIORITY_SUCCESSFULLY_CHANGED_MESSAGE = "Workitem with ID %d successfully changed its priority to %s";
    static final String SEVERITY_SUCCESSFULLY_CHANGED_MESSAGE = "Severity of workitem with ID %d was successfully changed to: %s";
    static final String STATUS_SUCCESSFULLY_CHANGED_MESSAGE = "Status of workitem with ID %d was successfully changed to: %s";
    static final String SUCCESSFULLY_ASSIGNED_MEMBER_TO_WORKITEM_MESSAGE = "Member %s successfully assigned to workitem with ID %d";
    static final String WORKITEM_SUCCESSFULLY_UNASSIGNED_MESSAGE = "Workitem with ID %d was successfully unassigned";
    static final String COMMENT_SUCCESSFULLY_ADDED_TO_WORKITEM_MESSAGE = "Comment successfully added to workitem with ID %d";
    static final String FEEDBACK_RATING_VALUE_CHANGED_MESSAGE = "Feedback with ID %d successfully changed its rating to %d";
    static final String STORY_SIZE_SUCCESSFULLY_CHANGED_MESSAGE ="Story with ID %d successfully changed its size to %s";

    // Error messages
    public static final String INVALID_NUMBER_OF_ARGUMENTS = "Invalid number of arguments. Expected: %d, Received: %d";
    public static final String MEMBER_EXISTS_ERROR_MESSAGE = "Member %s already exists";
    public static final String BOARD_EXISTS_ERROR_MESSAGE = "Board %s already exists";
    static final String TEAM_EXISTS_ERROR_MESSAGE = "Team %s already exists";
    static final String NO_MEMBERS_ERROR_MESSAGE = "There are no members";
    static final String NO_TEAMS_ERROR_MESSAGE = "There are no teams";
    static final String TEAM_DOES_NOT_EXIST_ERROR_MESSAGE = "Team %s does not exist";
    static final String BOARDS_NOT_EXIST_ERROR_MESSAGE = "There are no existing boards for team %s";
    static final String BOARD_DOES_NOT_EXIST_ERROR_MESSAGE = "Board %s does not exist";
    static final String WORKITEM_DOES_NOT_EXIST_ERROR_MESSAGE = "Workitem with id %d does not exist";
    static final String NO_BUG_OR_STORY_WITH_SUCH_ID_MESSAGE = "Workitem with ID %d is not a bug or story";
    static final String WORKITEM_IS_NOT_A_BUG_ERROR_MESSAGE = "Workitem with ID %d is not a bug";
    static final String WORKITEM_IS_NOT_A_STORY_ERROR_MESSAGE = "Workitem with ID %d is not a story";
    static final String MEMBER_DOES_NOT_EXIST_ERROR_MESSAGE = "Member %s does not exist";
    static final String WORKITEM_DOES_NOT_ASSIGNED_MESSAGE = "Work Item with id %d must be assigned in order to be unassigned ";
    static final String WORKITEM_IS_NOT_A_FEEDBACK_ERROR_MESSAGE = "Workitem with ID %d is not a feedback";
    static final String THERE_ARE_NO_WORKITEMS_ERROR_MESSAGE = "There are not existing workitems";
    static final String THERE_ARE_NO_BUGS_ERROR_MESSAGE = "There are not existing bugs";
    static final String THERE_ARE_NO_STORIES_ERROR_MESSAGE = "There are not existing stories";
    static final String THERE_ARE_NO_FEEDBACKS_ERROR_MESSAGE = "There are not existing feedbags";
    static final String CANNOT_ASSIGN_MEMBER_TO_FEEDBACK_MESSAGE = "Cannot assign person to feedback. Only to bug or story";
    public static final String INCORRECT_SIZE_TYPE_ERROR_MESSAGE = "Incorrect type of size.Size can be large, medium or small";
}


