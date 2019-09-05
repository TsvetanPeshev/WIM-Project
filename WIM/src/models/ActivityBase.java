package models;

import models.common.Validator;
import models.contracts.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityBase implements Activity {
    private static final String ACTIVITY_HEADER = "Activity";
    private static final String ACTIVITIES_ERROR_MESSAGE = "There is not activities";

    private List<String> activityHistory;

    public ActivityBase(){
        activityHistory = new ArrayList<>();
    }

    @Override
    public void addActivity(String activity) {
        Validator.checkForEmptyValue(activity, ACTIVITY_HEADER);
        activityHistory.add(activity);
    }

    @Override
    public List<String> getActivityHistory() {
        return new ArrayList<>(activityHistory);
    }

    @Override
    public String showActivity(String name) {
        if(getActivityHistory().isEmpty()){
           return ACTIVITIES_ERROR_MESSAGE;
        }

        StringBuilder sb = new StringBuilder();
        getActivityHistory()
                .forEach(h -> sb.append(h).append(System.lineSeparator()));
        return sb.toString();
    }
}
