package core.contracts;

import models.contracts.Bug;
import models.contracts.Feedback;
import models.contracts.Member;
import models.contracts.Story;

import java.util.HashMap;
import java.util.List;

public interface WorkitemFactory{

    Bug createBug( String title, String description, String priority, String severity, String status);

    Story createStory(String title, String description, String priority, String size, String status);

    Feedback createFeedback( String title, String description, int rating, String status);
}
