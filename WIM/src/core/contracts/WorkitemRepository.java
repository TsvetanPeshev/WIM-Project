package core.contracts;

import models.common.EditorOfID;
import models.contracts.Bug;
import models.contracts.Feedback;
import models.contracts.Story;
import models.contracts.Workitem;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface WorkitemRepository {
    Map<Long, Bug> getBugs();

    Map<Long, Story> getStories();

    Map<Long, Feedback> getFeedbacks();

    Map<Long, Workitem> getWorkitems();

    void addBug(Bug bug, long id);

    void addStory(Story story, long id);

    void addFeedback(Feedback feedback, long id);

    EditorOfID getIdManager();

    long getWorkitemID(Workitem workitem);
}
