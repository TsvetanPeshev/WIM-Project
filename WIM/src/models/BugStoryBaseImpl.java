package models;

import models.common.Validator;
import models.contracts.BugStoryBase;
import models.contracts.Member;
import models.enums.PriorityType;
import models.enums.StatusType;

import java.util.Objects;

public class BugStoryBaseImpl extends WorkitemImpl implements BugStoryBase {
    private static final String PRIORITY_HEADER = "Priority";
    private static final String ASSIGNEE_HEADER = "Assignee";

    private PriorityType priority;
    private Member assignee;

    public BugStoryBaseImpl(String title, String description, StatusType status, PriorityType priority){
        super(title, description, status);
        this.priority = priority;
    }

    @Override
    public PriorityType getPriorityType() {
        return priority;
    }

    @Override
    public void changePriority(PriorityType priority) {
        Validator.checkForEmptyValue(priority, PRIORITY_HEADER);
        this.priority = priority;
    }

    @Override
    public Member getAssignee() {
        return assignee;
    }

    @Override
    public void setAssignee(Member assignee) {
        Validator.checkForEmptyValue(assignee, ASSIGNEE_HEADER);
        this.assignee = assignee;
    }

    public String toString(int id, String typeOfWorkitem){
        StringBuilder result = new StringBuilder(super.toString(id));

        result.append(String.format("Priority: %S%n\t", this.getPriorityType().toString()));

        if(!Objects.isNull(this.getAssignee())){
            result.append(String.format("Assignee: %s%n\t", this.getAssignee().getName()));
        }
        return result.toString();
    }
}
