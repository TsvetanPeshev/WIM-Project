package models.contracts;

import models.enums.PriorityType;

public interface BugStoryBase extends Workitem{
    PriorityType getPriorityType();

    void changePriority(PriorityType priority);

    Member getAssignee();

    void setAssignee(Member assignee);
}
