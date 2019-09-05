package models.contracts;

import models.enums.SeverityType;

import java.util.List;

public interface Bug extends BugStoryBase{
    List<String> getStepsToReproduce();
    SeverityType getSeverity();
    void changeSeverity(SeverityType severity);
}
