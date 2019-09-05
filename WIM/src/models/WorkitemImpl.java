package models;

import core.WorkitemRepositoryImpl;
import core.contracts.WorkitemRepository;
import models.common.Validator;
import models.common.WorkiitemStatusValidator;
import models.contracts.*;
import models.enums.StatusType;

import models.contracts.Comment;
import java.util.*;

public class WorkitemImpl extends ActivityBase implements Workitem {
    private static final int TITLE_MIN_LENGTH = 10;
    private static final int TITLE_MAX_LENGTH = 50;
    private static final String TITLE_LENGTH_ERROR_MESSAGE = "Title can not be less than %d and more than %d.";
    private static final int DESCRIPTION_MIN_LENGTH = 10;
    private static final int DESCRIPTION_MAX_LENGTH = 500;
    private static final String DESCRIPTION_LENGTH_ERROR_MESSAGE = "Description can not be less than %d and more than %d";
    private static final String STATUS_HEADER = "Status";
    private static final String DESCRIPTION_HEADER = "Description";
    private static final String TITLE_HEADER = "Title";
    private static final String HISTORY_HEADER = "History";
    private static final String BUG = "#Bug\n\t";
    private static final String STORY = "#Story\n\t";
    private static final String FEEDBACK = "#Feedback\n\t";

    private String title;
    private String description;
    private StatusType status;
    private List<String> history;
    private List<Comment> comments;

    public WorkitemImpl(String title, String description, StatusType status) {
        setTitle(title);
        setDescription(description);
        setStatus(status);
        comments = new ArrayList<>();

    }
    @Override
    public void addComment(Comment comment) {
        Validator.checkForEmptyValue(comment, "Comment");
        comments.add(comment);
    }

    @Override
    public ArrayList<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public StatusType getStatus() {
        return status;
    }

    @Override
    public List<String> getHistory() {
        return new ArrayList<>(history);
    }

    public void setStatus(StatusType status) {
        Validator.checkForEmptyValue(status, STATUS_HEADER);
        WorkiitemStatusValidator.workitemStatusValidation(status, getTitle());

        this.status = status;
    }

    @Override
    public void addHistory(String history) {
        Validator.checkForEmptyValue(history, HISTORY_HEADER);
        this.history.add(history);
    }

    private void setTitle(String title) {
        Validator.checkForEmptyValue(title, TITLE_HEADER);
        Validator.validateLength(title.length(), TITLE_MIN_LENGTH, TITLE_MAX_LENGTH,
                TITLE_LENGTH_ERROR_MESSAGE);
        this.title = title;
    }

    private void setDescription(String description) {
        Validator.checkForEmptyValue(description, DESCRIPTION_HEADER);
        Validator.validateLength(description.length(), DESCRIPTION_MIN_LENGTH,
                DESCRIPTION_MAX_LENGTH, DESCRIPTION_LENGTH_ERROR_MESSAGE);
        this.description = description;
    }

    @Override
    public String toString(long id) {
        StringBuilder result = new StringBuilder();
        WorkitemRepository workitemRepository = new WorkitemRepositoryImpl();

        if(workitemRepository.getBugs().containsValue(this)){
            result.append(BUG);
        }else if(workitemRepository.getStories().containsValue(this)){
            result.append(STORY);
        }else{
            result.append(FEEDBACK);
        }

        result.append(String.format("ID: %d%n\t", id));
        result.append(String.format("Title: %s%n\t", this.getTitle()));
        result.append(String.format("Description: %s%n\t", this.getDescription()));
        result.append(String.format("Status: %S%n\t", this.getStatus()));
        result.append("Comments: \n\t");

        for (Comment comment : this.getComments()) {
            result.append(String.format("%s (%s%n\t)", comment.getComment(), comment.getAuthor().getName()));
        }

        result.append("Activity history:\n");

        this.getActivityHistory()
                .forEach(activity -> result.append(String.format("\t %s%n", activity)));

        return result.append("\t").toString();
    }

}
