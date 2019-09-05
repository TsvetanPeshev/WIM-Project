package core;

import core.contracts.WorkitemRepository;
import models.common.EditorOfID;
import models.contracts.Bug;
import models.contracts.Feedback;
import models.contracts.Story;
import models.contracts.Workitem;

import java.util.*;
import java.util.stream.Collectors;

public class WorkitemRepositoryImpl implements WorkitemRepository {
    private Map<Long, Bug> bugs;
    private Map<Long, Story> stories;
    private Map<Long, Feedback> feedbacks;
    private Map<Long, Workitem> workitems;
    private EditorOfID idManager;

    public WorkitemRepositoryImpl() {
        this.bugs = new LinkedHashMap<>();
        this.stories = new LinkedHashMap<>();
        this.feedbacks = new LinkedHashMap<>();
        workitems = new LinkedHashMap<>();
        idManager = new EditorOfID();
    }

    @Override
    public Map<Long, Workitem> getWorkitems() {
        return new LinkedHashMap<>(workitems);
    }

    @Override
    public Map<Long, Bug> getBugs() {
        return new LinkedHashMap<>(bugs);

    }

    @Override
    public void addBug(Bug bug, long id) {

        bugs.put(id, bug);
        workitems.put(id, bug);
    }

    @Override
    public Map<Long, Story> getStories() {
        return new LinkedHashMap<>(stories);
    }

    @Override
    public void addStory(Story story, long id) {

        stories.put(id, story);
        workitems.put(id, story);
    }

    public long getWorkitemID(Workitem workitem) {

        List<Long> s = getWorkitems()
                .entrySet()
                .stream()
                .filter(w -> w.getValue().equals(workitem))
                .map(w -> w.getKey())
                .collect(Collectors.toList());

        return s.get(0);
    }

    @Override
    public Map<Long, Feedback> getFeedbacks() {
        return new LinkedHashMap<>(feedbacks);
    }

    @Override
    public void addFeedback(Feedback feedback, long id) {
        feedbacks.put(id, feedback);
        workitems.put(id, feedback);
    }

    @Override
    public EditorOfID getIdManager() {
        return idManager;
    }
}
