package core.factories;

import core.contracts.WorkitemFactory;
import models.BugImpl;
import models.FeedbackImpl;
import models.StoryImpl;
import models.contracts.Bug;
import models.contracts.Feedback;
import models.contracts.Story;
import models.enums.PriorityType;
import models.enums.SeverityType;
import models.enums.Size;
import models.enums.StatusType;


public class WorkitemFactoryImpl implements WorkitemFactory {
    @Override
    public Bug createBug(String title, String description, String priority, String severity, String status){
        return new BugImpl(title, description, getPriorityType(priority), getSeverityType(severity), getStatusType(status));
    }

    @Override
    public Story createStory( String title, String description, String priority, String size, String status){
        return new StoryImpl( title, description, getPriorityType(priority), getSize(size), getStatusType(status));
    }

    @Override
    public Feedback createFeedback(String title, String description, int rating, String status){
        return new FeedbackImpl(title, description, rating, getStatusType(status));
    }

    private StatusType getStatusType(String status){
        return StatusType.valueOf(status.toUpperCase());
    }

    private SeverityType getSeverityType(String severity){
        return SeverityType.valueOf(severity.toUpperCase());
    }

    private Size getSize( String size){
        return Size.valueOf(size.toUpperCase());
    }

    private PriorityType getPriorityType(String priority){
        return PriorityType.valueOf(priority.toUpperCase());
    }
}
